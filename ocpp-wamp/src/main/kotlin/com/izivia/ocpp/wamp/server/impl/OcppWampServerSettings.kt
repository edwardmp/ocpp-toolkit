package com.izivia.ocpp.wamp.server.impl

import java.util.concurrent.Executor
import java.util.concurrent.Executors

open class OcppWampServerSettings(
    val timeoutInMs: Long = 30_000
) {
    fun buildCallsExecutor(): Executor {
        return Executors.newCachedThreadPool()
    }
}
