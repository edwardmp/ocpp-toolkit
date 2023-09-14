package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import java.util.UUID

object OcppConstant {
    const val HEADER = "Header"
    const val BODY = "Body"
    const val MESSAGE_ID = "MessageID"
    const val ACTION = "Action"
    const val FROM = "From"
    const val TO = "To"
    const val RELATES_TO = "RelatesTo"
    const val TEXT = "text"
    const val ADDRESS = "Address"
    const val CHARGEBOX_IDENTITY = "chargeBoxIdentity"
    const val ENVELOP = "Envelope"

    const val NS_HEADER = "s:$HEADER"
    const val NS_BODY = "s:$BODY"
    const val NS_MESSAGE_ID = "a:$MESSAGE_ID"
    const val NS_ACTION = "a:$ACTION"
    const val NS_FROM = "a:$FROM"
    const val NS_TO = "a:$TO"
    const val NS_RELATE_TO = "a:$RELATES_TO"
    const val NS_CHARGEBOX_IDENTITY = "o:$CHARGEBOX_IDENTITY"
    const val NS_ADDRESS = "a:$ADDRESS"
    const val NS_ENVELOP = "s:$ENVELOP"

    const val SOAP = "http://www.w3.org/2003/05/soap-envelope"
    const val ADDRESSING = "http://www.w3.org/2005/08/addressing"
    const val SOAP_ANONYMOUS = "http://www.w3.org/2005/08/addressing/anonymous"
    val SOAP_ANONYMOUS_VT = ValueText(SOAP_ANONYMOUS)
    val SOAP_ANONYMOUS_FROM = SoapHeaderFromOut(SOAP_ANONYMOUS_VT)

    const val XMLNS_S = "xmlns:s"
    const val XMLNS_A = "xmlns:a"
    const val XMLNS_O = "xmlns:o"
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapEnvelope<T : SoapBody>(
    @JsonProperty(OcppConstant.HEADER)
    val header: SoapHeader = SoapHeader(),
    @JsonProperty(OcppConstant.BODY)
    val body: T
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapHeader(
    @JsonProperty(OcppConstant.MESSAGE_ID)
    val messageId: ValueText = ValueText(UUID.randomUUID().toString()),
    @JsonProperty(OcppConstant.ACTION)
    val action: ValueText? = null,
    @JsonProperty(OcppConstant.CHARGEBOX_IDENTITY)
    val chargeBoxIdentity: ValueText? = null,
    @JsonProperty(OcppConstant.FROM)
    val from: SoapHeaderFrom = SoapHeaderFrom(OcppConstant.SOAP_ANONYMOUS_VT),
    @JsonProperty(OcppConstant.TO)
    val to: ValueText = OcppConstant.SOAP_ANONYMOUS_VT,
    @JsonProperty(OcppConstant.RELATES_TO)
    val relatesTo: ValueText? = null
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValueText(
    @JacksonXmlText
    @JsonProperty(OcppConstant.TEXT)
    val value: String = ""
)

data class SoapHeaderFrom(
    @JsonProperty(OcppConstant.ADDRESS)
    val address: ValueText = OcppConstant.SOAP_ANONYMOUS_VT
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapHeaderOut(
    @JsonProperty(OcppConstant.NS_MESSAGE_ID)
    val messageId: String,
    @JsonProperty(OcppConstant.NS_ACTION)
    val action: String,
    @JsonProperty(OcppConstant.NS_CHARGEBOX_IDENTITY)
    val chargeBoxIdentity: String?,
    @JsonProperty(OcppConstant.NS_FROM)
    val from: SoapHeaderFromOut,
    @JsonProperty(OcppConstant.NS_TO)
    val to: String,
    @JsonProperty(OcppConstant.NS_RELATE_TO)
    val relatesTo: String?
)

data class SoapHeaderFromOut(
    @JsonProperty(OcppConstant.NS_ADDRESS)
    val address: ValueText
)

@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = OcppConstant.NS_ENVELOP)
data class SoapEnvelopeOut<T>(
    @JacksonXmlProperty(isAttribute = true, localName = OcppConstant.XMLNS_S)
    val soap: String = OcppConstant.SOAP,
    @JacksonXmlProperty(isAttribute = true, localName = OcppConstant.XMLNS_A)
    val addr: String = OcppConstant.ADDRESSING,
    @JacksonXmlProperty(isAttribute = true, localName = OcppConstant.XMLNS_O)
    val ocpp: String,
    @JsonProperty(OcppConstant.NS_HEADER)
    val header: SoapHeaderOut,
    @JsonProperty(OcppConstant.NS_BODY)
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM)
    @JsonTypeIdResolver(BaseResolver::class)
    val body: T
)

interface SoapBody

fun String.toValueText(): ValueText = ValueText(this)
