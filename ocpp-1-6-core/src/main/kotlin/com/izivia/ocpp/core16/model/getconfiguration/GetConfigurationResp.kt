package com.izivia.ocpp.core16.model.getconfiguration

import com.izivia.ocpp.core16.model.Response

data class GetConfigurationResp(
    val configurationKey: List<KeyValue>? = null,
    val unknownKey: List<String>? = null
) : Response
