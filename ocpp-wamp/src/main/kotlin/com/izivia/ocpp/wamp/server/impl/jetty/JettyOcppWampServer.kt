package com.izivia.ocpp.wamp.server.impl.jetty

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.wamp.messages.WampMessage
import com.izivia.ocpp.wamp.messages.WampMessageMetaHeaders
import com.izivia.ocpp.wamp.server.OcppWampServer
import com.izivia.ocpp.wamp.server.OcppWampServerHandler
import com.izivia.ocpp.wamp.server.impl.OcppWampServerApp
import com.izivia.ocpp.wamp.server.impl.OcppWsEndpoint
import org.http4k.server.Http4kServer
import org.http4k.server.Jetty
import org.http4k.server.asServer
import org.slf4j.LoggerFactory

class JettyOcppWampServer(
    val port: Int,
    val ocppVersions: Set<OcppVersion>,
    path: String = "ws",
    val timeoutInMs: Long = 30_000,
    private val onWsConnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    private val onWsReconnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    private val onWsCloseHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> }
) : OcppWampServer {
    private val handlers = mutableListOf<OcppWampServerHandler>()
    private var server: Http4kServer? = null
    private var wsApp: OcppWampServerApp? = null
    private var ocppWsEndpoint = OcppWsEndpoint(path)

    override fun start() {
        wsApp = OcppWampServerApp(
            ocppVersions = ocppVersions,
            handlers = { id -> handlers.filter { h -> h.accept(id) } },
            onWsConnectHandler = onWsConnectHandler,
            onWsReconnectHandler = onWsReconnectHandler,
            onWsCloseHandler = onWsCloseHandler,
            ocppWsEndpoint = ocppWsEndpoint,
            timeoutInMs = timeoutInMs
        )
            .also {
                server = it.newRoutingHandler().asServer(
                    Jetty(port = port)
                ).start()
            }
        logger.info(
            "starting jetty ocpp wamp server 1.1.1 B on port $port" +
                " -- ocpp versions=$ocppVersions - timeout=$timeoutInMs ms"
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

    override fun sendBlocking(ocppId: CSOcppId, message: WampMessage): WampMessage =
        getWsApp()
            .sendBlocking(ocppId, message)

    override fun register(handler: OcppWampServerHandler) {
        handlers.add(handler)
    }

    override fun getChargingStationOcppVersion(ocppId: CSOcppId): OcppVersion =
        getWsApp()
            .getChargingStationOcppVersion(ocppId)

    private fun getWsApp() = (wsApp ?: throw IllegalStateException("server not started"))

    companion object {
        private val logger = LoggerFactory.getLogger(JettyOcppWampServer::class.java)
    }
}
