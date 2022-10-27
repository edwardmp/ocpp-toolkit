package com.izivia.ocpp.core15.model.common

import com.izivia.ocpp.core15.model.common.enumeration.*

data class SampledValue(
    val value: String,
    val context: ReadingContext? = ReadingContext.SamplePeriodic,
    val format: ValueFormat? = ValueFormat.Raw,
    val measurand: Measurand? = Measurand.EnergyActiveImportRegister,
    val location: Location? = Location.Outlet,
    val unit: UnitOfMeasure? = UnitOfMeasure.Wh
) {
    /* default constructor is required by jackson but will never be used */
    constructor() : this("VALUE_WILL_NEVER_BE_USED")
}
