package com.izivia.ocpp.wamp.server.impl

import io.undertow.server.HttpServerExchange
import io.undertow.websockets.WebSocketConnectionCallback
import io.undertow.websockets.core.WebSocketChannel
import io.undertow.websockets.core.WebSockets
import io.undertow.websockets.core.AbstractReceiveListener
import io.undertow.websockets.core.BufferedTextMessage
import io.undertow.websockets.core.BufferedBinaryMessage
import io.undertow.websockets.spi.WebSocketHttpExchange
import org.http4k.core.StreamBody
import org.http4k.core.Body
import org.http4k.core.Request
import org.http4k.core.RequestSource
import org.http4k.core.Method
import org.http4k.websocket.PushPullAdaptingWebSocket
import org.http4k.websocket.WsHandler
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsStatus
import org.slf4j.LoggerFactory
import java.io.IOException

/**
 * Taken from Http4kUndertowWebSocketCallBack
 * With improvement on upgradeRequest to add source field
 * with caller ip address
 */
class UndertowWebSocketCallBack(private val ws: WsHandler) : WebSocketConnectionCallback {

    private val SEC_WEBSOCKET_LOCATION = "Sec-WebSocket-Location"

    companion object {
        private val logger = LoggerFactory.getLogger(UndertowWebSocketCallBack::class.java)
    }

    override fun onConnect(exchange: WebSocketHttpExchange, channel: WebSocketChannel) {
        // When chargerStation connect with //
        if (exchange.getResponseHeader(SEC_WEBSOCKET_LOCATION)?.contains(Regex("""[^:]//""")) == true) {
            logger.info(
                "Replace Sec-WebSocket-Location header ${exchange.getResponseHeader(SEC_WEBSOCKET_LOCATION)} " +
                    "double slash"
            )
            exchange.setResponseHeader(
                SEC_WEBSOCKET_LOCATION,
                exchange.getResponseHeader(SEC_WEBSOCKET_LOCATION).replace(Regex("""([^:])//"""), "$1/")
            )
        }

        val upgradeRequest = exchange.asRequest(channel)

        val socket = object : PushPullAdaptingWebSocket(upgradeRequest) {
            override fun send(message: WsMessage) =
                if (message.body is StreamBody) {
                    WebSockets.sendBinary(
                        message.body.payload,
                        channel,
                        null
                    )
                } else {
                    WebSockets.sendText(message.bodyString(), channel, null)
                }

            override fun close(status: WsStatus) {
                logger.warn(
                    "Closing websocket : WsStatus $status, channel $channel, closeCode: ${channel.closeCode}," +
                        " closeReason: ${channel.closeReason}"
                )
                WebSockets.sendClose(status.code, status.description, channel, null)
            }
        }.apply(ws(upgradeRequest))

        channel.addCloseTask {
            logger.warn("Closing websocket : WsStatus $it")
            socket.triggerClose(WsStatus(it.closeCode, it.closeReason ?: "unknown"))
        }

        channel.receiveSetter.set(object : AbstractReceiveListener() {
            override fun onFullTextMessage(
                channel: WebSocketChannel,
                message: BufferedTextMessage
            ) {
                try {
                    socket.triggerMessage(WsMessage(Body(message.data)))
                } catch (e: IOException) {
                    logger.error("IOException $message", e)
                    throw e
                } catch (e: Exception) {
                    logger.error("Exception $message", e)
                    socket.triggerError(e)
                    throw e
                }
            }

            override fun onFullBinaryMessage(
                channel: WebSocketChannel,
                message: BufferedBinaryMessage
            ) =
                message.data.resource.forEach { socket.triggerMessage(WsMessage(Body(it))) }

            override fun onError(channel: WebSocketChannel, error: Throwable) {
                logger.warn("Exception, requestURI: ${exchange?.requestURI}", error)
                return socket.triggerError(error)
            }
        })
        channel.resumeReceives()
    }
}

private fun WebSocketHttpExchange.asRequest(channel: WebSocketChannel) =
    Request(Method.GET, requestURI.replace("//", "/"))
        .headers(requestHeaders.toList().flatMap { h -> h.second.map { h.first to it } })
        .source(
            RequestSource(
                channel.sourceAddress.hostString,
                channel.sourceAddress.port,
                channel.requestScheme
            )
        )

fun requiresWebSocketUpgrade(): (HttpServerExchange) -> Boolean = {
    (
        it.requestHeaders["Connection"]?.any { header ->
            header.equals("upgrade", true)
        } ?: false
        ) &&
        (
            it.requestHeaders["Upgrade"]?.any { header ->
                header.equals("websocket", true)
            }
                ?: false
            )
}
