package com.izivia.ocpp.core16.model.heartbeat

import com.izivia.ocpp.core16.model.Response
import kotlinx.datetime.Instant

data class HeartbeatResp(
    val currentTime: Instant
) : Response
