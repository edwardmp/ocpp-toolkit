package com.izivia.ocpp.core16.model.getlog

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.getlog.enumeration.LogEnumType
import java.sql.Timestamp

data class GetLogReq(
    val logType: LogEnumType,
    val requestId: Int,
    val retries: Int?,
    val retryInterval: Int?,
    val log: LogParametersType
) : Request

data class LogParametersType(
    val remoteLocation: String,
    val oldestTimestamp: Timestamp?,
    val latestTimestamp: Timestamp?
)
