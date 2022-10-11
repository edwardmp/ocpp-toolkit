package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapEnvelope<T : SoapBody>(
    @JsonProperty("Header")
    val header: SoapHeader,
    @JsonProperty("Body")
    val body: T
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class SoapHeader(
    @JsonProperty("MessageID")
    val messageId: ValueText,
    @JsonProperty("Action")
    val action: ValueText,
    val chargeBoxIdentity: ValueText?,
    @JsonProperty("From")
    val from: SoapHeaderFrom?,
    @JsonProperty("To")
    val to: ValueText?,
    @JsonProperty("RelatesTo")
    val relatesTo: ValueText?
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ValueText(
    @JacksonXmlText
    @JsonProperty("text")
    val value: String = ""
)

data class SoapHeaderFrom(
    @JsonProperty("Address")
    val address: ValueText
)

interface SoapBody
