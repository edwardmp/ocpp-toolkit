package com.izivia.ocpp.wamp.server.impl

import io.undertow.connector.ByteBufferPool
import io.undertow.connector.PooledByteBuffer
import io.undertow.server.protocol.framed.FrameHeaderData
import io.undertow.util.Headers
import io.undertow.websockets.core.*
import io.undertow.websockets.core.protocol.version13.Hybi13Handshake
import io.undertow.websockets.core.protocol.version13.WebSocket13Channel
import io.undertow.websockets.extensions.CompositeExtensionFunction
import io.undertow.websockets.extensions.ExtensionFunction
import io.undertow.websockets.spi.WebSocketHttpExchange
import org.slf4j.LoggerFactory
import org.xnio.OptionMap
import org.xnio.StreamConnection
import java.io.IOException
import java.nio.ByteBuffer

class IgnoreCorruptMessageWebSocket13Channel(
    channel: StreamConnection?,
    pool: ByteBufferPool?,
    webSocketLocation: String,
    responseHeader: String,
    client: Boolean,
    allowExtensions: Boolean,
    compose: ExtensionFunction,
    peerConnections: MutableSet<WebSocketChannel>,
    options: OptionMap
) : WebSocket13Channel(
    channel,
    pool,
    webSocketLocation,
    responseHeader,
    client,
    allowExtensions,
    compose,
    peerConnections,
    options
) {
    private val logger = LoggerFactory.getLogger(IgnoreCorruptMessageWebSocket13Channel::class.java.name)
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
        logger.info("Parsing with ${IgnoreCorruptMessageWebSocket13Channel::class.java.simpleName}")
        if (customPartialFrame == null || customPartialFrame?.isDone == true) {
            customPartialFrame = receiveFrame()
        }

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


class IgnoreCorruptMessageHybi13Handshake(wsSubprotocols: Set<String>, allowExtensions: Boolean) :
    Hybi13Handshake(wsSubprotocols, allowExtensions) {

    override fun createChannel(
        exchange: WebSocketHttpExchange?,
        channel: StreamConnection?,
        pool: ByteBufferPool?
    ): WebSocketChannel {
        val extensionFunctions = initExtensions(exchange)
        return IgnoreCorruptMessageWebSocket13Channel(
            channel,
            pool,
            getWebSocketLocation(exchange),
            exchange!!.getResponseHeader(Headers.SEC_WEB_SOCKET_PROTOCOL_STRING),
            false,
            extensionFunctions.isNotEmpty(),
            CompositeExtensionFunction.compose(extensionFunctions),
            exchange.peerConnections,
            exchange.options
        )
    }
}
