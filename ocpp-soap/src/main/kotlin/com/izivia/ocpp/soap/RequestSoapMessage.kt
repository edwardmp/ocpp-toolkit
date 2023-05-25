package com.izivia.ocpp.soap

import com.izivia.ocpp.utils.ErrorDetail
import com.izivia.ocpp.utils.OcppInitiator

data class RequestSoapMessage<T>(
    val messageId: String,
    val chargingStationId: String,
    val action: String,
    val from: String?,
    val to: String?,
    val payload: T,
    val warnings: List<ErrorDetail>? = null,
    val forcedInitiator: OcppInitiator? = null
)
