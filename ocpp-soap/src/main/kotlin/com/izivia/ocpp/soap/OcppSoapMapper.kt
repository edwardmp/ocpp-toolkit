package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.izivia.ocpp.utils.KotlinxInstantModule
import javax.xml.stream.XMLInputFactory

class OcppSoapMapper : ObjectMapper(
    XmlMapper(getNewFactory(true), CustomXmlModule)
        .registerModule(
            kotlinModule {
                configure(KotlinFeature.NullIsSameAsDefault, true)
            }
        )
        .registerModule(KotlinxInstantModule())
        .setSerializationInclusion(Include.NON_EMPTY)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setDefaultPropertyInclusion(Include.NON_EMPTY)
        .enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
)

fun getNewFactory(nsAware: Boolean): XmlFactory =
    XMLInputFactory
        .newFactory()
        .apply {
            setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, nsAware)
        }
        .let { XmlFactory(it) }

private object CustomXmlModule : JacksonXmlModule() {
    init {
        setDefaultUseWrapper(false)
        setXMLTextElementName("text")
    }
}

abstract class EnumMixin(
    @JsonValue val value: String
)

abstract class SoapFaultMixin(
    @JacksonXmlProperty(localName = "s:Code")
    val code: FaultCode,
    @JacksonXmlProperty(localName = "s:Reason")
    val reason: FaultReason
)

abstract class FaultCodeMixin(
    @JacksonXmlProperty(localName = "s:Value")
    val value: FaultCodeValue,
    @JacksonXmlProperty(localName = "o:Subcode")
    val subCode: FaultSubCode
)

abstract class FaultSubCodeMixin(
    @JacksonXmlProperty(localName = "o:Value")
    val value: FaultSubCodeValue
)

abstract class FaultReasonMixin(
    @JacksonXmlProperty(localName = "s:Text")
    val text: FaultReasonText
)
