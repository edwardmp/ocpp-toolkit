package com.izivia.ocpp.wamp.server.impl

import io.undertow.Handlers.*
import io.undertow.Undertow
import io.undertow.UndertowOptions.ENABLE_HTTP2
import io.undertow.connector.ByteBufferPool
import io.undertow.connector.PooledByteBuffer
import io.undertow.server.HttpServerExchange
import io.undertow.server.handlers.BlockingHandler
import io.undertow.server.protocol.framed.FrameHeaderData
import io.undertow.util.Headers
import io.undertow.websockets.WebSocketProtocolHandshakeHandler
import io.undertow.websockets.core.*
import io.undertow.websockets.core.protocol.version13.Hybi13Handshake
import io.undertow.websockets.core.protocol.version13.WebSocket13Channel
import io.undertow.websockets.extensions.CompositeExtensionFunction
import io.undertow.websockets.extensions.ExtensionFunction
import io.undertow.websockets.spi.WebSocketHttpExchange
import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.BAD_REQUEST
import org.http4k.server.*
import org.http4k.sse.SseHandler
import org.http4k.websocket.WsHandler
import org.xnio.OptionMap
import org.xnio.StreamConnection
import java.io.IOException
import java.net.InetSocketAddress
import java.nio.ByteBuffer


class CustomWebSocket13Channel(
    channel: StreamConnection?,
    pool: ByteBufferPool?,
    webSocketLocation: String,
    responseHeader: String,
    b: Boolean,
    b1: Boolean,
    compose: ExtensionFunction,
    peerConnections: MutableSet<WebSocketChannel>,
    options: OptionMap
) : WebSocket13Channel(channel, pool, webSocketLocation, responseHeader, b, b1, compose, peerConnections, options) {

    private var customPartialFrame: PartialFrame? = null

    override fun createChannel(
        frameHeaderData: FrameHeaderData?,
        frameData: PooledByteBuffer?
    ): StreamSourceFrameChannel? {
        return try {
            super.createChannel(frameHeaderData, frameData)
        } catch (e: IllegalStateException) {
            WebSocketLogger.REQUEST_LOGGER.warnf(e, "receive failed due to corrupted message")
            customPartialFrame = null
            null
        }
    }

    @Throws(IOException::class)
    override fun parseFrame(data: ByteBuffer?): FrameHeaderData? {
        if (customPartialFrame == null || customPartialFrame?.isDone == true) {
            customPartialFrame = receiveFrame()
        }

        println("heh " + data.toString())
        try {
            customPartialFrame?.handle(data)
        } catch (e: WebSocketFrameCorruptedException) {
            WebSocketLogger.REQUEST_LOGGER.warnf(e, "receive failed due to corrupted message")
            customPartialFrame = null
        } catch (e: WebSocketException) {
            //the data was corrupt
            //send a close message
            WebSockets.sendClose(
                CloseMessage(CloseMessage.WRONG_CODE, e.message).toByteBuffer(),
                this, null
            )
            markReadsBroken(e)
            if (WebSocketLogger.REQUEST_LOGGER.isDebugEnabled) {
                WebSocketLogger.REQUEST_LOGGER.debugf(e, "receive failed due to Exception")
            }
            customPartialFrame = null
            throw IOException(e)
        }
        return customPartialFrame
    }
}


class CustomHybi13Handshake(wsSubprotocols: Set<String>, allowExtensions: Boolean) :
    Hybi13Handshake(wsSubprotocols, allowExtensions) {

    override fun createChannel(
        exchange: WebSocketHttpExchange?,
        channel: StreamConnection?,
        pool: ByteBufferPool?
    ): WebSocketChannel? {
        val extensionFunctions = initExtensions(exchange)
        return CustomWebSocket13Channel(
            channel,
            pool,
            getWebSocketLocation(exchange),
            exchange!!.getResponseHeader(Headers.SEC_WEB_SOCKET_PROTOCOL_STRING),
            false,
            !extensionFunctions.isEmpty(),
            CompositeExtensionFunction.compose(extensionFunctions),
            exchange!!.peerConnections,
            exchange!!.options
        )
    }
}

/*
    This class is inspired by the one provided by http4k project, with the following additions:
    - support for websocket subprotocols
    - support to provide a predicate before upgrading the protocol for a websocket request
 */
class Undertow(
    val port: Int = 8000, val enableHttp2: Boolean,
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
                        CustomHybi13Handshake(wsSubprotocols, false),
//                        Hybi08Handshake(wsSubprotocols, true),
//                        Hybi07Handshake(wsSubprotocols, true),
                    ),
                    UndertowWebSocketCallBack(it)
                )
            }
        }

        val sseCallback = sse?.let { serverSentEvents(Http4kSseCallback(sse)) }

        val handlerWithWs = predicate(
            { exch -> requiresWebSocketUpgrade()(exch) && acceptWebSocketPredicate(exch) },
            wsCallback, httpHandler
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
