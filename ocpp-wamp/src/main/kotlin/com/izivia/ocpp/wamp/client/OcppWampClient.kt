package com.izivia.ocpp.wamp.client

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.wamp.client.impl.OkHttpOcppWampClient
import com.izivia.ocpp.wamp.messages.WampMessage
import com.izivia.ocpp.wamp.messages.WampMessageMeta
import com.izivia.ocpp.wamp.messages.WampMessageMetaHeaders
import org.http4k.core.Uri

interface OcppWampClient {
    fun connect()
    fun close()
    fun sendBlocking(message: WampMessage): WampMessage
    fun onAction(fn: WampOnActionHandler)

    companion object {
        fun newClient(
            target: Uri,
            ocppId: CSOcppId,
            ocppVersion: OcppVersion,
            timeoutInMs: Long = 30_000,
            autoReconnect: Boolean = true,
            baseAutoReconnectDelayInMs: Long = 250,
            headers: WampMessageMetaHeaders = emptyList()
        ) = OkHttpOcppWampClient(
            target,
            ocppId,
            ocppVersion,
            timeoutInMs,
            autoReconnect,
            baseAutoReconnectDelayInMs,
            headers
        )
    }
}

typealias WampOnActionHandler = (WampMessageMeta, WampMessage) -> WampMessage?
