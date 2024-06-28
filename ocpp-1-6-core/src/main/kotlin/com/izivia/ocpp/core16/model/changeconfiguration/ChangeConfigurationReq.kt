package com.izivia.ocpp.core16.model.changeconfiguration

import com.izivia.ocpp.core16.model.Request

data class ChangeConfigurationReq(
    val key: String,
    val value: String
) : Request
