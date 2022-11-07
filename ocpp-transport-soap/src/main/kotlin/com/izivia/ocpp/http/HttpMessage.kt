package com.izivia.ocpp.http

import com.izivia.ocpp.transport.RequestHeaders

data class HttpMessage(
    val ocppId: String,
    val action: String?,
    val payload: String,
    val headers: RequestHeaders = emptyList()
)
