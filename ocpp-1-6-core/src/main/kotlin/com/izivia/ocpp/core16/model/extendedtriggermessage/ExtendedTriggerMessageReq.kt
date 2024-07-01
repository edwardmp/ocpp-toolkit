package com.izivia.ocpp.core16.model.extendedtriggermessage

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.triggermessage.enumeration.MessageTrigger

data class ExtendedTriggerMessageReq(
    val requestedMessage: MessageTrigger,
    val connectorId: Int? = null
) : Request
