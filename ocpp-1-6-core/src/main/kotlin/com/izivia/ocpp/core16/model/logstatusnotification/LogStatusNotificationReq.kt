package com.izivia.ocpp.core16.model.logstatusnotification

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.logstatusnotification.enumeration.UpdateLogStatusEnumType

data class LogStatusNotificationReq(
    val status: UpdateLogStatusEnumType,
    val requestId: Int?
) : Request
