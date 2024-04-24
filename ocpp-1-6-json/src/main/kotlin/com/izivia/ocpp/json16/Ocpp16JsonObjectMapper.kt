package com.izivia.ocpp.json16

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.izivia.ocpp.core16.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core16.model.common.enumeration.Measurand
import com.izivia.ocpp.core16.model.common.enumeration.Phase
import com.izivia.ocpp.core16.model.common.enumeration.ReadingContext
import com.izivia.ocpp.json.OcppJsonMapper

internal object Ocpp16JsonObjectMapper : ObjectMapper(
    OcppJsonMapper()
        .addMixIn(Measurand::class.java, EnumMixin::class.java)
        .addMixIn(ReadingContext::class.java, EnumMixin::class.java)
        .addMixIn(Phase::class.java, EnumMixin::class.java)
        .addMixIn(ChangeConfigurationReq::class.java, ChangeConfigurationReqMixin::class.java)
)

private abstract class EnumMixin(
    @JsonValue val value: String
)

private abstract class ChangeConfigurationReqMixin {
    @get:JsonInclude(JsonInclude.Include.ALWAYS)
    abstract val value: String
}
