package com.izivia.ocpp.core16.model.updatefirmware

import kotlinx.datetime.Instant

data class SignedUpdateFirmwareReq(
    val firmware: FirmwareType,
    val retries: Int?,
    val requestId: Int,
    val retryInterval: Int?
)

data class FirmwareType(
    val location: String,
    val retrieveDateTime: Instant,
    val installDateTime: Instant?,
    val signingCertificate: String,
    val signature: String
)
