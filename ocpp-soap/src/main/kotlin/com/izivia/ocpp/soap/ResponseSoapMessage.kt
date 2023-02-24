package com.izivia.ocpp.soap

import com.izivia.ocpp.utils.ErrorDetail

data class ResponseSoapMessage<T>(
    val action: String,
    val messageId: String,
    val relatesTo: String,
    val to: String?,
    val from: String?,
    val chargeBoxIdentity: String? = null,
    val payload: T,
    val warnings: List<ErrorDetail>? = null
)
