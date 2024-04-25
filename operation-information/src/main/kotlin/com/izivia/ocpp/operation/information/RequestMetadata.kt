package com.izivia.ocpp.operation.information

data class RequestMetadata(
    val chargingStationId: String,
    val messageId: String? = null
)
