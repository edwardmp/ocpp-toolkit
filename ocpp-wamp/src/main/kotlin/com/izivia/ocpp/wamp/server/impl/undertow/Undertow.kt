package com.izivia.ocpp.wamp.server.impl.undertow

import io.undertow.Handlers.*
import io.undertow.Undertow
import io.undertow.UndertowOptions.ENABLE_HTTP2
import io.undertow.connector.ByteBufferPool
import io.undertow.server.HttpServerExchange
import io.undertow.server.handlers.BlockingHandler
import io.undertow.util.Headers
import io.undertow.websockets.WebSocketProtocolHandshakeHandler
import io.undertow.websockets.core.WebSocketChannel
import io.undertow.websockets.core.protocol.version07.Hybi07Handshake
import io.undertow.websockets.core.protocol.version07.WebSocket07Channel
import io.undertow.websockets.core.protocol.version08.Hybi08Handshake
import io.undertow.websockets.core.protocol.version08.WebSocket08Channel
import io.undertow.websockets.core.protocol.version13.Hybi13Handshake
import io.undertow.websockets.core.protocol.version13.WebSocket13Channel
import io.undertow.websockets.extensions.CompositeExtensionFunction
import io.undertow.websockets.spi.WebSocketHttpExchange
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.server.*
import org.http4k.sse.SseHandler
import org.http4k.websocket.WsHandler
import org.xnio.StreamConnection
import java.net.InetSocketAddress

/*
    This class is inspired by the one provided by http4k project, with the following additions:
    - support for websocket subprotocols
    - support to provide a predicate before upgrading the protocol for a websocket request
 */
class Undertow(
    val port: Int = 8000,
    val enableHttp2: Boolean,
    val acceptWebSocketPredicate: (HttpServerExchange) -> Boolean = { true },
    val wsSubprotocols: Set<String> = setOf()
) : PolyServerConfig {
    override fun toServer(http: HttpHandler?, ws: WsHandler?, sse: SseHandler?): Http4kServer {
        val httpHandler =
            (http ?: { Response(BAD_REQUEST) }).let(::Http4kUndertowHttpHandler).let(::BlockingHandler)
        val wsCallback = ws?.let {
            if (wsSubprotocols.isEmpty()) {
                websocket(Http4kWebSocketCallback(it))
            } else {
                WebSocketProtocolHandshakeHandler(
                    listOf(
                        object : Hybi13Handshake(wsSubprotocols, false) {
                            override fun createChannel(
                                exchange: WebSocketHttpExchange,
                                channel: StreamConnection,
                                pool: ByteBufferPool
                            ): WebSocketChannel {
                                val extensionFunctions = initExtensions(exchange)
                                return WebSocket13Channel(
                                    channel,
                                    pool,
                                    getWebSocketLocation(exchange),
                                    exchange.getResponseHeader(Headers.SEC_WEB_SOCKET_PROTOCOL_STRING),
                                    true,
                                    true,
                                    CompositeExtensionFunction.compose(extensionFunctions),
                                    exchange.peerConnections,
                                    exchange.options
                                )
                            }
                        },
                        object : Hybi08Handshake(wsSubprotocols, false) {
                            override fun createChannel(
                                exchange: WebSocketHttpExchange?,
                                channel: StreamConnection?,
                                pool: ByteBufferPool?
                            ): WebSocketChannel {
                                val extensionFunctions = initExtensions(exchange)
                                return WebSocket08Channel(
                                    channel,
                                    pool,
                                    getWebSocketLocation(exchange),
                                    exchange!!.getResponseHeader(
                                        Headers.SEC_WEB_SOCKET_PROTOCOL_STRING
                                    ),
                                    true,
                                    true,
                                    CompositeExtensionFunction.compose(extensionFunctions),
                                    exchange.peerConnections,
                                    exchange.options
                                )
                            }
                        },
                        object : Hybi07Handshake(wsSubprotocols, false) {
                            override fun createChannel(
                                exchange: WebSocketHttpExchange?,
                                channel: StreamConnection?,
                                pool: ByteBufferPool?
                            ): WebSocketChannel {
                                val extensionFunctions = initExtensions(exchange)
                                return WebSocket07Channel(
                                    channel,
                                    pool,
                                    getWebSocketLocation(exchange),
                                    exchange!!.getResponseHeader(
                                        Headers.SEC_WEB_SOCKET_PROTOCOL_STRING
                                    ),
                                    true,
                                    true,
                                    CompositeExtensionFunction.compose(extensionFunctions),
                                    exchange.peerConnections,
                                    exchange.options
                                )
                            }
                        }
                    ),
                    UndertowWebSocketCallBack(it)
                )
            }
        }
        val sseCallback = sse?.let { serverSentEvents(Http4kSseCallback(sse)) }

        val handlerWithWs = predicate(
            { exch -> requiresWebSocketUpgrade()(exch) && acceptWebSocketPredicate(exch) },
            wsCallback,
            httpHandler
        )

        val handlerWithSse = sseCallback
            ?.let { predicate(hasEventStreamContentType(), sseCallback, handlerWithWs) }
            ?: handlerWithWs

        return object : Http4kServer {
            val server = Undertow.builder()
                .addHttpListener(port, "0.0.0.0")
                .setServerOption(ENABLE_HTTP2, enableHttp2)
                .setWorkerThreads(32 * Runtime.getRuntime().availableProcessors())
                .setHandler(handlerWithSse).build()

            override fun start() = apply { server.start() }

            override fun stop() = apply { server.stop() }

            override fun port(): Int = when {
                port > 0 -> port
                else -> (server.listenerInfo[0].address as InetSocketAddress).port
            }
        }
    }
}
