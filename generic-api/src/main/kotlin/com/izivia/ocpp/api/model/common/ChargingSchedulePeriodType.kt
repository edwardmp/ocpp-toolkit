package com.izivia.ocpp.api.model.common

data class ChargingSchedulePeriodType(
    val startPeriod: Int,
    val limit: Double,
    val numberPhases: Int? = null,
    val phaseToUse: Int? = null
)
