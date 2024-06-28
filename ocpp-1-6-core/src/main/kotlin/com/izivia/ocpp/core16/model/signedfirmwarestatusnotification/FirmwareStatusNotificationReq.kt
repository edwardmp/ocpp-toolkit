package com.izivia.ocpp.core16.model.signedfirmwarestatusnotification

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.firmwarestatusnotification.enumeration.SignedFirmwareStatus

data class SignedFirmwareStatusNotificationReq(
    val status: SignedFirmwareStatus,
    val requestId: Int?
) : Request
