package com.izivia.ocpp.wamp.server.impl

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.utils.MessageErrorCode
import com.izivia.ocpp.wamp.core.WampCallManager
import com.izivia.ocpp.wamp.messages.WampMessage
import com.izivia.ocpp.wamp.messages.WampMessageMeta
import com.izivia.ocpp.wamp.messages.WampMessageMetaHeaders
import com.izivia.ocpp.wamp.messages.WampMessageType
import com.izivia.ocpp.wamp.server.OcppWampServerHandler
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.http4k.routing.bind
import org.http4k.routing.websockets
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsMessage
import org.slf4j.LoggerFactory
import java.net.SocketException
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Websocket Server
 *
 * @param handlers are called in the list order and return the first not null response
 */
class OcppWampServerApp(
    val ocppVersions: Set<OcppVersion>,
    private val handlers: (CSOcppId) -> List<OcppWampServerHandler>,
    private val onWsConnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    private val onWsReconnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    private val onWsCloseHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    private val ocppWsEndpoint: OcppWsEndpoint,
    val timeoutInMs: Long,
    private val clock: Clock = Clock.System
) {
    companion object {
        private val logger = LoggerFactory.getLogger("com.izivia.ocpp.wamp.server")
    }

    private val connections = Collections.synchronizedMap(
        TreeMap<String, ChargingStationConnection?> { a, b ->
            a.lowercase().compareTo(b.lowercase())
        }
    )
    private val shutdown = AtomicBoolean(false)

    private fun newConnection(ws: Websocket) {
        try {
            logger.debug("websocket connection: upgrade request={}", ws.upgradeRequest)
            if (shutdown.get()) {
                logger.warn("shutting down - rejecting connection")
                ws.close()
                return
            }
            val wsConnectionId = UUID.randomUUID().toString()
            val chargingStationOcppId = ocppWsEndpoint.extractChargingStationOcppId(ws.upgradeRequest.uri.path)
                ?.takeUnless { it.isEmpty() }
                ?: throw IllegalArgumentException("malformed request - no chargingStationOcppId - ${ws.upgradeRequest}")
            val ocppVersion = ws.upgradeRequest.header("Sec-WebSocket-Protocol")
                ?.split(",")?.firstOrNull()?.trim()
                ?.let { ocppVersions.filter { v -> v.subprotocol == it.lowercase() }.firstOrNull() }
                ?: throw IllegalArgumentException(
                    "malformed request - unsupported or invalid ocpp version - ${ws.upgradeRequest}"
                )

            val previousConnection = connections[chargingStationOcppId]
            val chargingStationConnection = ChargingStationConnection(
                clock.now(),
                wsConnectionId,
                chargingStationOcppId,
                ws,
                ocppVersion,
                timeoutInMs,
                shutdown
            ).also {
                connections[chargingStationOcppId] = it
            }

            val reconnect = previousConnection != null

            if (reconnect) {
                logger.warn(
                    """[$chargingStationOcppId] already connected
                        | - the new connection will replace the previous one
                        | - a connection reconnect notification will be sent
                        | - existing connection=$previousConnection
                        | - new connection=$chargingStationConnection
                        | - existing connection will be closed
                    """.trimMargin()
                )
                // this close wont trigger a onWsCloseHandler,
                // because we have already changed the current registered connection
                previousConnection?.close()
                onWsReconnectHandler(chargingStationOcppId, ws.upgradeRequest.headers)
            } else {
                onWsConnectHandler(chargingStationOcppId, ws.upgradeRequest.headers)
            }

            val logContext = "[$chargingStationOcppId] [$wsConnectionId]"
            ws.onClose {
                handleWebSocketClosed(logContext, chargingStationConnection)
            }

            ws.onError { e ->
                // not sure yet if we should consider any error as a close, so we handle only connection resets
                if (e.isConnectionReset()) {
                    handleWebSocketClosed(logContext, chargingStationConnection, e)
                }
            }

            logger.info("$logContext connected - number of connections : ${connections.size}")
            val handler = handlers(chargingStationOcppId)
            ws.onMessage {
                val msgString = it.bodyString()
                if (logger.isDebugEnabled) {
                    logger.debug("$logContext onMessage `$msgString`")
                }
                if (msgString.trim().isEmpty()) {
                    logger.info("$logContext empty message received - ignored")
                    return@onMessage
                }
                val msg = WampMessage.parse(msgString)
                if (msg == null) {
                    logger.error("$logContext parsing Wamp message returns null - `$msgString`")
                    ws.send(
                        WsMessage(
                            WampMessage.CallError(
                                "",
                                MessageErrorCode.INTERNAL_ERROR,
                                "Parse error",
                                "{}"
                            ).toJson()
                        )
                    )
                    return@onMessage
                }
                msg.also { wampMessage ->
                    when (wampMessage.msgType) {
                        WampMessageType.CALL -> {
                            if (shutdown.get()) {
                                logger.info("$logContext - rejected call - shutting down - $msgString")
                                ws.send(
                                    WsMessage(
                                        WampMessage.CallError(
                                            wampMessage.msgId,
                                            MessageErrorCode.INTERNAL_ERROR,
                                            "Rejected call - shutting down",
                                            "{}"
                                        ).toJson()
                                    )
                                )
                                return@onMessage
                            }

                            logger.info("$logContext -> ${it.bodyString()}")
                            val resp = handler.asSequence()
                                // use sequence to avoid greedy mapping, to find the first handler with non null result
                                .map {
                                    it.onAction(
                                        WampMessageMeta(ocppVersion, chargingStationOcppId, ws.upgradeRequest.headers),
                                        wampMessage
                                    )
                                }
                                .filterNotNull()
                                .firstOrNull()
                                ?: WampMessage.CallError(
                                    wampMessage.msgId,
                                    MessageErrorCode.INTERNAL_ERROR,
                                    "No action handler found",
                                    """{"message":"$wampMessage"}"""
                                ).also { logger.warn("$logContext no action handler found for $wampMessage") }

                            logger.info("$logContext <- ${resp.toJson()}")
                            ws.send(WsMessage(resp.toJson()))
                        }

                        WampMessageType.CALL_RESULT, WampMessageType.CALL_ERROR -> {
                            chargingStationConnection.callManager.handleResult(logContext, wampMessage)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            logger.error("Error during new connection with ${ws.upgradeRequest}: $e", e)
            throw e
        }
    }

    private fun handleWebSocketClosed(
        logContext: String,
        chargingStationConnection: ChargingStationConnection,
        t: Throwable? = null
    ) {
        val chargingStationOcppId = chargingStationConnection.ocppId
        val currentConnection = connections[chargingStationOcppId]
        if (currentConnection?.wsConnectionId == chargingStationConnection.wsConnectionId) {
            // todo - use a concurrent map and a remove if to make this atomic
            connections[chargingStationOcppId] = null
        } else {
            logger.info(
                "$logContext warn: do not clear ws on close - not current connection in map" +
                    " - registered connection: $currentConnection" +
                    " - closed connection: $chargingStationConnection"
            )
        }
        val duration = clock.now() - chargingStationConnection.timestamp
        val errorInfo = t?.let { " onError $t - " } ?: ""
        logger.info("$logContext disconnected - $duration -$errorInfo number of connections : ${connections.size}")

        if (connections[chargingStationOcppId] == null) {
            // we notify only if we remove the connection, otherwise we are still connected
            onWsCloseHandler(chargingStationOcppId, chargingStationConnection.ws.upgradeRequest.headers)
        } else {
            logger.debug("$logContext not notifying disconnection - still connected")
        }
    }

    fun shutdown() {
        shutdown.set(true)
        connections.values.toList().forEach { c ->
            c?.let {
                it.callManager.await()
                it.close()
                connections[it.ocppId] = null
            }
        }
    }

    fun sendBlocking(ocppId: CSOcppId, message: WampMessage): WampMessage =
        getChargingStationConnection(ocppId).sendBlocking(message)

    private fun getChargingStationConnection(ocppId: CSOcppId): ChargingStationConnection {
        var backOffRetryMs = 10L
        var backOffRetryAttempts = 5
        var connection = connections[ocppId]
        while (connection == null && backOffRetryAttempts > 0) {
            Thread.sleep(backOffRetryMs)
            backOffRetryAttempts--
            backOffRetryMs *= 2
            connection = connections[ocppId]
        }
        return connection ?: throw IllegalStateException("no connection to $ocppId")
    }

    fun getChargingStationOcppVersion(ocppId: CSOcppId): OcppVersion =
        getChargingStationConnection(ocppId).ocppVersion

    fun newRoutingHandler() = websockets(ocppWsEndpoint.uriTemplate.toString() bind ::newConnection)

    private class ChargingStationConnection(
        val timestamp: Instant,
        val wsConnectionId: String,
        val ocppId: CSOcppId,
        val ws: Websocket,
        val ocppVersion: OcppVersion,
        timeoutInMs: Long,
        shutdown: AtomicBoolean
    ) {
        val callManager: WampCallManager =
            WampCallManager(logger, { m: String -> ws.send(WsMessage(m)) }, timeoutInMs, shutdown)

        fun sendBlocking(message: WampMessage): WampMessage =
            callManager.callBlocking("[$ocppId] [$wsConnectionId]", message)

        fun close() {
            logger.info("[$ocppId] [$wsConnectionId] - closing")
            ws.close()
        }

        override fun toString(): String {
            return "ChargingStationConnection(" +
                "wsConnectionId='$wsConnectionId', ocppId='$ocppId', ocppVersion=$ocppVersion, timestamp='$timestamp')"
        }
    }
}

private fun Throwable.isConnectionReset(): Boolean =
    this is SocketException && this.message.equals("Connection reset")
