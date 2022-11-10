package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

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

    const val XMLNS_S = "xmlns:s"
    const val XMLNS_A = "xmlns:a"
    const val XMLNS_O = "xmlns:o"
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapEnvelope<T : SoapBody>(
    @JsonProperty(OcppConstant.HEADER)
    val header: SoapHeader,
    @JsonProperty(OcppConstant.BODY)
    val body: T
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapHeader(
    @JsonProperty(OcppConstant.MESSAGE_ID)
    val messageId: ValueText,
    @JsonProperty(OcppConstant.ACTION)
    val action: ValueText,
    val chargeBoxIdentity: ValueText?,
    @JsonProperty(OcppConstant.FROM)
    val from: SoapHeaderFrom?,
    @JsonProperty(OcppConstant.TO)
    val to: ValueText?,
    @JsonProperty(OcppConstant.RELATES_TO)
    val relatesTo: ValueText?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValueText(
    @JacksonXmlText
    @JsonProperty(OcppConstant.TEXT)
    val value: String = ""
)

data class SoapHeaderFrom(
    @JsonProperty(OcppConstant.ADDRESS)
    val address: ValueText
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
    val from: SoapHeaderFromOut?,
    @JsonProperty(OcppConstant.NS_TO)
    val to: String?,
    @JsonProperty(OcppConstant.NS_RELATE_TO)
    val relatesTo: String?
)

data class SoapHeaderFromOut(
    @JsonProperty(OcppConstant.NS_ADDRESS)
    val address: String
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
