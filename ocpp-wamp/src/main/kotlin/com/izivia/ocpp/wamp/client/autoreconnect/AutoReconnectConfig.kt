package com.izivia.ocpp.wamp.client.autoreconnect

import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

data class AutoReconnectConfig(
    // the timeout used for calls (sendBlocking), including time to make auto reconnection attempts.
    val callTimeout: Duration = 30.seconds,
    // initial delay before trying to reconnect. This delay will be doubled after each attempt, until successful
    val baseAutoReconnectDelay: Duration = 250.milliseconds,
    // minimum delay before trying to reconnect. This delay will prevent to make too much reconection attempts
    // when reconnection attempts are triggered by send calls
    val minDelayBetweenAttempts: Duration = 100.milliseconds
)
