package com.izivia.ocpp.core16.model.firmwarestatusnotification

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.firmwarestatusnotification.enumeration.FirmwareStatus

data class FirmwareStatusNotificationReq(
    val status: FirmwareStatus
) : Request
