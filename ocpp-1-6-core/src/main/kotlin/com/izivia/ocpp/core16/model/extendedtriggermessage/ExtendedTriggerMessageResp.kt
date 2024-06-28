package com.izivia.ocpp.core16.model.extendedtriggermessage

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.triggermessage.enumeration.TriggerMessageStatus

data class ExtendedTriggerMessageResp(
    val status: TriggerMessageStatus
) : Response
