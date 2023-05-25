package com.izivia.ocpp.soap

import com.izivia.ocpp.utils.ErrorDetail
import com.izivia.ocpp.utils.OcppInitiator

data class ResponseSoapMessage<T>(
    val action: String,
    val messageId: String,
    val relatesTo: String,
    val to: String?,
    val from: String?,
    val chargeBoxIdentity: String? = null,
    val payload: T,
    val warnings: List<ErrorDetail>? = null,
    val forcedInitiator: OcppInitiator? = null
)
