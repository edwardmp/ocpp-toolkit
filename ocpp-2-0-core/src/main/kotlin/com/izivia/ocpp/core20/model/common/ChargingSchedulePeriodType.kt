package com.izivia.ocpp.core20.model.common

data class ChargingSchedulePeriodType(
    val startPeriod: Int,
    val limit: Double,
    val numberPhases: Int? = null,
    val phaseToUse: Int? = null
)
