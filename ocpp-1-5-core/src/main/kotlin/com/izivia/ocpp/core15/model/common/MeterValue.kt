package com.izivia.ocpp.core15.model.common

import kotlinx.datetime.Instant

data class MeterValue(
    val timestamp: Instant,
    val value: List<SampledValue>
)
