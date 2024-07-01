package com.izivia.ocpp.core16.model.unlockconnector

import com.izivia.ocpp.core16.model.Request

data class UnlockConnectorReq(
    val connectorId: Int
) : Request
