package com.izivia.ocpp.core16.model.getlog

import com.izivia.ocpp.core16.model.getlog.enumeration.LogStatusEnumType

data class GetLogResp(
    val status: LogStatusEnumType,
    val filename: String?
)
