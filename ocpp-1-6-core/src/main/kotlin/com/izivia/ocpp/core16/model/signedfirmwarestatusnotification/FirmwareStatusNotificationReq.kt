package com.izivia.ocpp.core16.model.firmwarestatusnotification

import com.izivia.ocpp.core16.model.firmwarestatusnotification.enumeration.SignedFirmwareStatus

data class SignedFirmwareStatusNotificationReq(
    val status: SignedFirmwareStatus,
    val requestId: Int?
)
