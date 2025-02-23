package com.izivia.ocpp.wamp.server.impl

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.utils.MessageErrorCode
import com.izivia.ocpp.wamp.messages.WampMessage
import com.izivia.ocpp.wamp.messages.WampMessageMeta
import com.izivia.ocpp.wamp.server.OcppWampServer
import com.izivia.ocpp.wamp.server.OcppWampServerHandler
import kotlinx.datetime.Clock
import org.http4k.server.Http4kServer
import org.http4k.server.asServer
import org.slf4j.LoggerFactory
import java.util.concurrent.ConcurrentHashMap

class UndertowOcppWampServer(
    val port: Int,
    val ocppVersions: Set<OcppVersion>,
    path: String = "ws",
    val settings: OcppWampServerSettings = OcppWampServerSettings(),
    private val listeners: EventsListeners = EventsListeners()
) : OcppWampServer {
    private val handlers = mutableListOf<OcppWampServerHandler>()
    private val selectedHandler = ConcurrentHashMap<CSOcppId, List<OcppWampServerHandler>>()
    private var server: Http4kServer? = null
    private var wsApp: OcppWampServerApp? = null
    private var ocppWsEndpoint = OcppWsEndpoint(path)

    override fun start() {
        wsApp = OcppWampServerApp(
            ocppVersions = ocppVersions,
            handlers = { id -> selectedHandler[id] ?: throw IllegalStateException() },
            listeners = listeners,
            ocppWsEndpoint = ocppWsEndpoint,
            settings = settings
        )
            .also {
                server = it.newRoutingHandler().asServer(
                    Undertow(
                        port = port,
                        enableHttp2 = true,
                        acceptWebSocketPredicate = { exch ->
                            // search for an handler accepting this ocpp charging station, and memoize it in selectedHandler
                            ocppWsEndpoint.extractChargingStationOcppId(exch.requestURI)?.let { ocppId ->
                                handlers
                                    .filter { h -> h.accept(ocppId) }
                                    .also { selectedHandler[ocppId] = it }
                            } != null
                        },
                        wsSubprotocols = ocppVersions.map { it.subprotocol }.toSet()
                    )
                ).start()
            }
        logger.info(
            "starting ocpp wamp server 1.0.2 on port $port" +
                " -- ocpp versions=$ocppVersions - timeout=${settings.timeoutInMs} ms"
        )
    }

    override fun shutdown() {
        logger.info("shutting down ocpp wamp server of port $port")
        wsApp?.shutdown()
        stop()
    }

    override fun stop() {
        logger.info("stopping ocpp wamp server of port $port")
        server?.stop()
        server = null
        wsApp = null
    }

    override fun sendBlocking(ocppId: CSOcppId, message: WampMessage, timeoutInMs: Long?): WampMessage =
        getWsApp()
            .sendBlocking(ocppId, message, timeoutInMs = timeoutInMs)

    override fun register(handler: OcppWampServerHandler) {
        handlers.add(handler)
    }

    override fun getChargingStationOcppVersion(ocppId: CSOcppId): OcppVersion =
        getWsApp()
            .getChargingStationOcppVersion(ocppId)

    private fun getWsApp() = (wsApp ?: throw IllegalStateException("server not started"))

    companion object {
        private val logger = LoggerFactory.getLogger(UndertowOcppWampServer::class.java)
    }
}

// example only
fun main() {
    val server = UndertowOcppWampServer(5000, setOf(OcppVersion.OCPP_1_6))
    server.register(object : OcppWampServerHandler {
        override fun accept(ocppId: CSOcppId): Boolean = listOf("TEST1", "TEST2").contains(ocppId)

        override fun onAction(meta: WampMessageMeta, msg: WampMessage): WampMessage? =
            when (msg.action?.lowercase()) {
                "heartbeat" ->
                    WampMessage.CallResult(msg.msgId, """{"currentTime":"${Clock.System.now()}"}""")

                else -> {
                    println("unhandled action for message: ${msg.toJson()}")
                    WampMessage.CallError(
                        msg.msgId,
                        MessageErrorCode.NOT_SUPPORTED,
                        "unhandled action for message",
                        """{"action":" ${msg.action}"}"""
                    )
                }
            }
    })

    server.start()
}
