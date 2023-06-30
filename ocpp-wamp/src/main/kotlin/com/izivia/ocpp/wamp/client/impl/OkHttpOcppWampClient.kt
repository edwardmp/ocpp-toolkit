package com.izivia.ocpp.wamp.client.impl

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.utils.MessageErrorCode
import com.izivia.ocpp.wamp.client.OcppWampClient
import com.izivia.ocpp.wamp.client.WampOnActionHandler
import com.izivia.ocpp.wamp.core.WampCallManager
import com.izivia.ocpp.wamp.messages.WampMessage
import com.izivia.ocpp.wamp.messages.WampMessageMeta
import com.izivia.ocpp.wamp.messages.WampMessageMetaHeaders
import com.izivia.ocpp.wamp.messages.WampMessageType
import kotlinx.coroutines.*
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import okhttp3.*
import org.http4k.core.Uri
import org.slf4j.LoggerFactory
import java.io.IOException
import java.net.ConnectException
import java.util.concurrent.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

class OkHttpOcppWampClient(
    target: Uri,
    val ocppId: CSOcppId,
    val ocppVersion: OcppVersion,
    val timeoutInMs: Long = 30_000,
    autoReconnect: Boolean = true,
    baseAutoReconnectDelayInMs: Long = 250,
    private val headers: WampMessageMetaHeaders = emptyList(),
    hostnameVerifier: HostnameVerifier = HostnameVerifier { _, _ -> true }
) : OcppWampClient {
    val serverUri = target.path("${target.path.removeSuffix("/")}/$ocppId")

    private val handlers = mutableListOf<WampOnActionHandler>()

    private val socketOkHttpClient = OkHttpClient.Builder()
        .readTimeout(timeoutInMs, TimeUnit.MILLISECONDS)
        .connectTimeout(timeoutInMs, TimeUnit.MILLISECONDS)
        .hostnameVerifier(hostnameVerifier)
        .build()
    private val autoReconnectHandler = AutoReconnectHandler(this, baseAutoReconnectDelayInMs)
        .takeIf { autoReconnect }

    private var wampConnection: WampConnection? = null

    private var desiredConnectionState: ConnectionState = ConnectionState.DISCONNECTED
    private var connectionState: ConnectionState = ConnectionState.DISCONNECTED
        set(value) {
            if (value != field) {
                logger.info("[$ocppId] connection state $field -> $value")
                field = value
            }
        }

    override fun connect() {
        logger.info("connecting to $serverUri with ocpp version $ocppVersion")
        desiredConnectionState = ConnectionState.CONNECTED
        tryToConnect().also { t ->
            if (t == null) {
                logger.info("[$ocppId] connected to $serverUri")
            } else {
                logger.error("[$ocppId] error when connecting to $serverUri - $t")
                desiredConnectionState = ConnectionState.DISCONNECTED
                cleanupStateOnClose()
                throw IOException("[$ocppId] connection to $serverUri failed", t)
            }
        }
    }

    override fun close() {
        logger.info("disconnecting from $serverUri")
        desiredConnectionState = ConnectionState.DISCONNECTED
        connectionState = ConnectionState.DISCONNECTING
        wampConnection?.close(NORMAL_CLOSURE_STATUS, "close")
        cleanupStateOnClose()
    }

    private fun cleanupStateOnClose() {
        wampConnection = null
        autoReconnectHandler?.stopReconnecting()
        connectionState = ConnectionState.DISCONNECTED
    }

    fun tryToConnect(): Throwable? {
        val latch = CountDownLatch(1)
        var error: Throwable? = TimeoutException("connection timed out to $serverUri")
        asyncConnect(object : ConnectListener {
            override fun onConnect() {
                error = null
                latch.countDown()
            }

            override fun onFailure(t: Throwable) {
                error = t
                latch.countDown()
            }
        })
        latch.await(timeoutInMs, TimeUnit.MILLISECONDS)
        return error
    }

    private fun asyncConnect(listener: ConnectListener) {
        logger.debug("[$ocppId] connecting to $serverUri - current connection state = $connectionState")
        connectionState = ConnectionState.CONNECTING
        socketOkHttpClient.newWebSocket(
            Request.Builder().url(serverUri.toString())
                .header("Sec-WebSocket-Protocol", ocppVersion.subprotocol)
                .run {
                    headers
                        .filter { header -> header.second != null }
                        .foldRight(this) { header, acc ->
                            acc.header(header.first, header.second!!)
                        }
                }
                .build(),
            object : WebSocketListener() {
                override fun onOpen(webSocket: WebSocket, response: Response) {
                    onConnectedTo(webSocket)
                    listener.onConnect()
                }

                override fun onMessage(webSocket: WebSocket, text: String) {
                    onMessage(text)
                }

                override fun onClosing(closingWebSocket: WebSocket, code: Int, reason: String) {
                    when {
                        code == CLEANUP_CLOSURE_STATUS ->
                            logger.info("[$ocppId] connection closed to $serverUri due to reconnection")

                        desiredConnectionState == ConnectionState.CONNECTED -> {
                            logger.info("[$ocppId] connection lost to $serverUri - code=$code; reason=$reason")

                            handleUnexpectedDisconnection(closingWebSocket)
                        }

                        else -> {
                            connectionState = ConnectionState.DISCONNECTED
                        }
                    }
                }

                override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                    logger.info("[$ocppId] connection closed to $serverUri - code=$code; reason=$reason")
                }

                override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                    if (connectionState == ConnectionState.CONNECTING) {
                        if (t is ConnectException) {
                            logger.warn("[$ocppId] web socket failed to connect to $serverUri: $t")
                        } else {
                            logger.warn("[$ocppId] web socket connection failure to $serverUri: $t", t)
                        }
                        connectionState = ConnectionState.DISCONNECTED
                        listener.onFailure(t)
                    } else {
                        logger.warn("[$ocppId] web socket failure with connection to $serverUri: $t", t)
                        handleUnexpectedDisconnection(webSocket)
                    }
                }
            }
        )
    }

    private fun onMessage(text: String) {
        CoroutineScope(Dispatchers.Default).launch {
            val msgString = text
            logger.info("OkHttpOcppWampClient onMessage : $msgString")
            val msg = WampMessage.parse(msgString)

            if (msg == null) {
                logger.warn("can't parse wamp message from server: $msgString")
            } else {
                when (msg.msgType) {
                    WampMessageType.CALL_RESULT, WampMessageType.CALL_ERROR -> {
                        // outcoming call result
                        try {
                            withTimeout(timeoutInMs) {
                                logger.info("OkHttpOcppWampClient connect callManager : ${wampConnection?.callManager}")
                                wampConnection?.callManager?.handleResult("[$ocppId]", msg)
                            }
                        } catch (e: TimeoutCancellationException) {
                            logger.error(
                                "[$ocppId] timeout during call result handling of" +
                                    " ${msg.action} - $msgString -- ${e.message}"
                            )
                        }
                    }

                    WampMessageType.CALL -> {
                        try {
                            withTimeout(timeoutInMs) {
                                // incoming call
                                logger.info("[$ocppId] <- ${msg.action} - $msgString")
                                val r = handlers.asSequence()
                                    // use sequence to avoid greedy mapping, to find the first handler with non null result
                                    .map { it(WampMessageMeta(ocppVersion, ocppId), msg) }
                                    .filterNotNull()
                                    .firstOrNull()
                                    ?: WampMessage.CallError(
                                        msg.msgId,
                                        MessageErrorCode.INTERNAL_ERROR,
                                        "No handler found",
                                        "{}"
                                    )
                                logger.info("[$ocppId] -> ${r.toJson()}")
                                wampConnection?.websocket?.send(r.toJson())
                            }
                        } catch (e: TimeoutCancellationException) {
                            logger.error(
                                "[$ocppId] timeout during call handling of" +
                                    " ${msg.action} - $msgString -- ${e.message}"
                            )
                            wampConnection?.websocket?.send(
                                WampMessage.CallError(
                                    msg.msgId,
                                    MessageErrorCode.INTERNAL_ERROR,
                                    "Timeout during call handling",
                                    "{}"
                                ).toJson()
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleUnexpectedDisconnection(closingWebSocket: WebSocket) {
        connectionState = ConnectionState.DISCONNECTED
        // we don't call close on the connection, we are already in the ws closing hook
        wampConnection = null

        autoReconnectHandler?.startReconnecting()
    }

    private fun onConnectedTo(connectedWebsocket: WebSocket) {
        synchronized(this) {
            if (wampConnection?.websocket == connectedWebsocket) {
                logger.info("[$ocppId] already connected to $serverUri - ignored")
                return
            }

            if (wampConnection != null) {
                logger.warn("connected to new websocket while another one was already open - closing previous one")
                // cleanup call manager and websocket
                wampConnection?.close(CLEANUP_CLOSURE_STATUS, "reconnection")
                wampConnection = null
            }

            wampConnection = WampConnection(
                connectedWebsocket,
                WampCallManager(logger, { m: String -> connectedWebsocket.send(m) }, timeoutInMs)
            )
            connectionState = ConnectionState.CONNECTED
        }
    }

    override fun sendBlocking(message: WampMessage): WampMessage {
        return getCallManager().callBlocking("[$ocppId]", message)
    }

    override fun onAction(handler: WampOnActionHandler) {
        handlers.add(handler)
    }

    private fun getCallManager(): WampCallManager {
        if (wampConnection == null && desiredConnectionState == ConnectionState.CONNECTED) {
            waitForReconnectionOrTimeout()
        }
        return wampConnection?.callManager
            ?: throw when (desiredConnectionState) {
                ConnectionState.DISCONNECTED ->
                    IllegalStateException("not connected to $serverUri")

                ConnectionState.CONNECTED ->
                    IOException("currently not connected to $serverUri - retry later")

                else ->
                    IllegalStateException("unsupported desired state $desiredConnectionState")
            }
    }

    private fun waitForReconnectionOrTimeout() {
        if (connectionState == ConnectionState.CONNECTED) return

        autoReconnectHandler
            ?.maybeForceReconnectAttemptAndWaitForReconnection()
            ?.also {
                if (connectionState == ConnectionState.CONNECTED) {
                    logger.debug("[$ocppId] reconnection successful to $serverUri")
                } else {
                    logger.debug("[$ocppId] reconnection failed to $serverUri")
                }
            } ?: logger.debug("[$ocppId] no reconnection configured - disconnected from $serverUri")

    }

    enum class ConnectionState {
        CONNECTING, CONNECTED, DISCONNECTING, DISCONNECTED
    }

    companion object {
        const val NORMAL_CLOSURE_STATUS = 1000
        const val CLEANUP_CLOSURE_STATUS = 1001

        private val logger = LoggerFactory.getLogger(OkHttpOcppWampClient::class.java)
    }

    class WampConnection(val websocket: WebSocket, val callManager: WampCallManager) {
        fun close(code: Int, reason: String) {
            callManager.close()
            websocket.close(code, reason)
        }
    }

    interface ConnectListener {
        fun onConnect()
        fun onFailure(t: Throwable)
    }

    class AutoReconnectHandler(val client: OkHttpOcppWampClient, val baseAutoReconnectDelay: Long) {
        private var reconnecting: Boolean = false
        private var autoReconnectDelay: Long = baseAutoReconnectDelay
        private var autoReconnectAttemptCount = 0
        private var next: ScheduledFuture<*>? = null
        private var connectionLatch: CountDownLatch? = null
        private var lastForcedReconnectionAttempt: Instant? = null

        fun maybeForceReconnectAttemptAndWaitForReconnection() {
            val lastAttempt = lastForcedReconnectionAttempt
            if (lastAttempt == null ||
                // make sure we don't force the immediate reconnect attempt too often
                Clock.System.now().minus(lastAttempt).inWholeMilliseconds > baseAutoReconnectDelay
            ) {
                logger.info("[${client.ocppId}] triggering immediate reconnect attempt to ${client.serverUri}")
                next?.cancel(false)
                autoReconnectDelay = baseAutoReconnectDelay
                lastForcedReconnectionAttempt = Clock.System.now()
                reconnectAttempt()
            }
            logger.info(
                "[${client.ocppId}] waiting for reconnection to ${client.serverUri}" +
                    " with connection latch $connectionLatch"
            )
            connectionLatch?.await(client.timeoutInMs, TimeUnit.MILLISECONDS)
        }

        fun startReconnecting() {
            synchronized(this) {
                if (reconnecting) {
                    logger.debug("[${client.ocppId}] auto reconnect requested, but already going on - ignored")
                    return
                }
                logger.info("[${client.ocppId}] starting auto reconnect process to ${client.serverUri}")
                reconnecting = true
                autoReconnectAttemptCount = 0
                autoReconnectDelay = baseAutoReconnectDelay
                setupConnectionLatch()
                reconnectAttempt()
            }
        }

        private fun setupConnectionLatch() {
            if (connectionLatch == null) {
                connectionLatch = CountDownLatch(1)
                logger.info("[${client.ocppId}] set up connection latch $connectionLatch")
            }
        }

        private fun reconnectAttempt() {
            autoReconnectAttemptCount++
            logger.info(
                "[${client.ocppId}] attempting to reconnect to ${client.serverUri} (attempt $autoReconnectAttemptCount)"
            )
            client.tryToConnect().also { t ->
                if (t == null) {
                    logger.info(
                        "[${client.ocppId}] reconnected to ${client.serverUri}" +
                            " (after $autoReconnectAttemptCount attempts)"
                    )

                    logger.info("[${client.ocppId}] notifying connection on latch $connectionLatch")
                    connectionLatch?.countDown()
                    connectionLatch = null

                    stopReconnecting()
                } else {
                    logger.info(
                        "[${client.ocppId}] failed to reconnect to ${client.serverUri}"
                    )
                    autoReconnectDelay *= 2
                    next = executor.schedule({ reconnectAttempt() }, autoReconnectDelay, TimeUnit.MILLISECONDS)
                }
            }
        }

        fun stopReconnecting() {
            synchronized(this) {
                if (!reconnecting) return
                reconnecting = false
                next?.cancel(false)
            }
        }

        companion object {
            private val executor = Executors.newScheduledThreadPool(1)
        }
    }
}
