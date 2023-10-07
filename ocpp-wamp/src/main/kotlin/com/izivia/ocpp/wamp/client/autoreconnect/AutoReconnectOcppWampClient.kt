package com.izivia.ocpp.wamp.client.autoreconnect

import com.izivia.ocpp.wamp.client.ConnectionListener
import com.izivia.ocpp.wamp.client.ConnectionState
import com.izivia.ocpp.wamp.client.OcppWampClient
import com.izivia.ocpp.wamp.client.WampOnActionHandler
import com.izivia.ocpp.wamp.messages.WampMessage
import org.slf4j.LoggerFactory
import java.io.IOException
import java.util.concurrent.*
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class AutoReconnectOcppWampClient(
    val client: OcppWampClient,
    val debugContext: String,
    // the timeout used for calls (sendBlocking), including time to make auto reconnection attempts.
    val callTimeout: Duration = 30.seconds,
    // initial delay before trying to reconnect. This delay will be doubled after each attempt, until successful
    val baseAutoReconnectDelay: Duration = 250.milliseconds,
    // minimum delay before trying to reconnect. This delay will prevent to make too much reconection attempts
    // when reconnection attempts are triggered by send calls
    val minDelayBetweenAttempts: Duration = 100.milliseconds
) : OcppWampClient {

    private val lock = ReentrantLock()
    private var autoReconnectState: AutoReconnectState = AutoReconnectIdleState()
    private var connectionListener: ConnectionListener? = null

    override val state: ConnectionState
        get() = when (autoReconnectState) {
            is AutoReconnectIdleState -> ConnectionState.DISCONNECTED
            is AutoReconnectConnectingState -> ConnectionState.CONNECTING
            is AutoReconnectConnectedState -> ConnectionState.CONNECTED
        }

    /**
     * Asynchronously attempt to connect to the remote wamp server.
     *
     * Once called, this client is in auto reconnect mode, and will always do its best to keep the connection
     * to the wamp server.
     *
     * You can be notified of connections and deconnection by providing a ConnectionListener as parameter.
     *
     * Note that this method does not throw an exception if connection fails, since the connection may later be
     * available. So you need to listen to events, or use a client without auto reconnect if you are not sure
     * of the wamp server url or port and rather want to test a connection.
     */
    override fun connect(listener: ConnectionListener?) {
        logger.info("[$debugContext] connect (current state=${autoReconnectState::class.simpleName})")
        lock.withLock {
            if (connectionListener != null) {
                throw IllegalStateException(
                    "[$debugContext] can't connect with connection listener" +
                        " while already connected with another listener"
                )
            }
            connectionListener = listener
        }
        autoReconnectState.onConnecting()
    }

    /**
     * Closes the current connection to server (if connected) and stops any further auto reconnection attempt
     */
    override fun close() {
        logger.info("[$debugContext] close (current state=${autoReconnectState::class.simpleName})")
        autoReconnectState.onClose()
        lock.withLock {
            connectionListener = null
        }
    }

    override fun sendBlocking(message: WampMessage): WampMessage {
        autoReconnectState.onBeforeCall()
        return client.sendBlocking(message)
    }

    override fun onAction(fn: WampOnActionHandler) {
        client.onAction(fn)
    }

    private fun moveTo(from: AutoReconnectState, to: AutoReconnectState) {
        lock.withLock {
            if (autoReconnectState != from) {
                logger.error(
                    "[$debugContext] auto reconnect state change not accepted:" +
                        " expected from=$from; " +
                        "current=$autoReconnectState; " +
                        "requested to move to: $to"
                )
                return
            }
            autoReconnectState.exit(to)
            autoReconnectState = to
            to.enter()
        }
    }

    private fun emitConnected() {
        autoReconnectState.onConnected()
        connectionListener?.onConnected()
    }

    private fun emitConnectionLost(t: Throwable?) {
        autoReconnectState.onDisconnected()
        connectionListener?.onConnectionLost(t)
    }

    private fun emitConnectionFailure(t: Throwable) {
        autoReconnectState.onDisconnected()
        connectionListener?.onConnectionFailure(t)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AutoReconnectOcppWampClient::class.java)
    }

    private sealed class AutoReconnectState(
        val debugContext: String,
        val moveTo: (AutoReconnectState, AutoReconnectState) -> Unit
    ) {

        fun moveTo(to: AutoReconnectState) {
            moveTo(this, to)
        }

        open fun enter() {}
        open fun exit(to: AutoReconnectState) {}

        open fun onConnecting() {
            throw unsupported("onConnecting")
        }

        open fun onConnected() {
            throw unsupported("onConnected")
        }

        open fun onBeforeCall() {
            throw unsupported("onBeforeCall")
        }

        open fun onDisconnected() {
            throw unsupported("onDisconnected")
        }

        open fun onClose() {
            throw unsupported("onClose")
        }

        private fun unsupported(op: String) = IllegalStateException(
            "[$debugContext] $op not supported when state is ${this::class.simpleName}"
        )
    }

    private inner class AutoReconnectIdleState : AutoReconnectState(debugContext, { f, t -> moveTo(f, t) }) {
        override fun onConnecting() {
            moveTo(AutoReconnectConnectingState())
        }

        override fun onClose() {
            // nothing to do, already idle
        }

        override fun onDisconnected() {
            // nothing to do, if because of concurrency we receive a disconnection event while closed, that's ok
        }

        override fun onBeforeCall() {
            throw IllegalStateException("[$debugContext] not connected")
        }
    }

    private inner class AutoReconnectConnectingState() : AutoReconnectState(debugContext, { f, t -> moveTo(f, t) }) {
        private val handler = AutoReconnectHandler(
            debugContext,
            {
                try {
                    client.connect(object : ConnectionListener {
                        // we handle only connection lost, connection failure will throw an exception
                        // and onConnected is handled by the AutoReconnectHandler

                        override fun onConnectionLost(t: Throwable?) {
                            emitConnectionLost(t)
                        }
                    })
                    null
                } catch (t: Throwable) {
                    t
                }
            },
            object : ConnectionListener {
                override fun onConnected() {
                    emitConnected()
                }

                override fun onConnectionFailure(t: Throwable) {
                    emitConnectionFailure(t)
                }
            },
            baseAutoReconnectDelay,
            minDelayBetweenAttempts
        )
        private val connectionLatch = CountDownLatch(1)

        override fun enter() {
            handler.checkIsCleanAndReady()
            if (connectionLatch.count != 1L) {
                throw IllegalStateException(
                    "$debugContext - invalid state, connection count down latch is not 1: ${connectionLatch.count}"
                )
            }

            logger.info("[$debugContext] connecting [auto-reconnect ON]")
            handler.planConnectionAttemptNow()
        }

        override fun onConnected() {
            connectionLatch.countDown()
            moveTo(AutoReconnectConnectedState())
        }

        override fun onBeforeCall() {
            handler.planConnectionAttemptNow()
            logger.debug("[$debugContext] waiting for reconnection for at most $callTimeout")
            if (!connectionLatch.await(callTimeout.inWholeMilliseconds, TimeUnit.MILLISECONDS)) {
                throw IOException("[$debugContext] currently not connected - retry later")
            }
        }

        override fun onConnecting() {
            // nothing to do, already in connecting state
        }

        override fun onDisconnected() {
            // nothing to do, we are here to make retries
        }

        override fun onClose() {
            handler.close()
            moveTo(AutoReconnectIdleState())
        }
    }

    private inner class AutoReconnectConnectedState : AutoReconnectState(debugContext, { f, t -> moveTo(f, t) }) {
        override fun enter() {
            logger.info("[$debugContext] connected [auto-reconnect ON]")
        }

        override fun exit(to: AutoReconnectState) {
            when (to) {
                is AutoReconnectConnectingState ->
                    logger.info("[$debugContext] disconnected [auto-reconnect ON]")

                is AutoReconnectIdleState ->
                    logger.info("[$debugContext] disconnected [close]")

                is AutoReconnectConnectedState ->
                    logger.info("[$debugContext] disconnected [new connection]")
            }
        }

        override fun onDisconnected() {
            moveTo(AutoReconnectConnectingState())
        }

        override fun onClose() {
            moveTo(AutoReconnectIdleState())
        }

        override fun onBeforeCall() {
            // nothing to do, already connected
        }

        override fun onConnecting() {
            // nothing to do, already connected
        }
    }
}
