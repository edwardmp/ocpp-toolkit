package com.izivia.ocpp.json20

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.ObjectMapper
import com.izivia.ocpp.core20.model.common.enumeration.MeasurandEnumType
import com.izivia.ocpp.core20.model.common.enumeration.ReadingContextEnumType
import com.izivia.ocpp.core20.model.notifycustomerinformation.NotifyCustomerInformationReq
import com.izivia.ocpp.core20.model.notifyevent.NotifyEventReq
import com.izivia.ocpp.core20.model.notifymonitoringreport.NotifyMonitoringReportReq
import com.izivia.ocpp.core20.model.notifyreport.NotifyReportReq
import com.izivia.ocpp.core20.model.notifyreport.enumeration.DataEnumType
import com.izivia.ocpp.json.OcppJsonMapper

internal object Ocpp20JsonObjectMapper : ObjectMapper(
    OcppJsonMapper()
        .addMixIn(MeasurandEnumType::class.java, EnumMixin::class.java)
        .addMixIn(ReadingContextEnumType::class.java, EnumMixin::class.java)
        .addMixIn(DataEnumType::class.java, EnumMixin::class.java)
        .addMixIn(NotifyCustomerInformationReq::class.java, HasActionTimestampMixin::class.java)
        .addMixIn(NotifyEventReq::class.java, HasActionTimestampMixin::class.java)
        .addMixIn(NotifyReportReq::class.java, HasActionTimestampMixin::class.java)
        .addMixIn(NotifyMonitoringReportReq::class.java, HasActionTimestampMixin::class.java)
)

private abstract class EnumMixin(
    @JsonValue val value: String
)

@JsonIgnoreProperties(value = ["timestamp"])
private abstract class HasActionTimestampMixin