package com.izivia.ocpp.soap

import com.fasterxml.jackson.databind.ObjectMapper
import com.izivia.ocpp.soap.OcppSoapParser.Companion.SOAP_ANONYMOUS
import kotlin.reflect.KClass

interface OcppSoapParser {
    companion object {
        const val SOAP_ANONYMOUS = "http://www.w3.org/2005/08/addressing/anonymous"
    }
    fun parseAnyRequestFromSoap(messageStr: String): RequestSoapMessage<Any>
    fun parseAnyResponseFromSoap(messageStr: String): ResponseSoapMessage<Any>
    fun <T> mapRequestToSoap(request: RequestSoapMessage<T>): String
    fun <T> mapResponseToSoap(response: ResponseSoapMessage<T>): String

    fun readToEnvelop(soap: String): SoapEnvelope<*>

    fun getRequestBodyContent(envelope: SoapEnvelope<*>): Any
    fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any
}

abstract class OcppSoapParserImpl(
    private val ns_ocpp: String,
    private val soapMapperOutput: ObjectMapper,
    val soapMapperInput: ObjectMapper
) : OcppSoapParser {

    override fun <T> mapResponseToSoap(response: ResponseSoapMessage<T>): String {
        val headers = SoapHeaderOut(
            messageId = response.messageId,
            action = "/${response.action.replaceFirstChar { it.uppercase() }}Response",
            relatesTo = response.relatesTo,
            to = response.to,
            from = SoapHeaderFromOut(response.from ?: SOAP_ANONYMOUS),
            chargeBoxIdentity = response.chargeBoxIdentity
        )
        val xmlBuilder = SoapEnvelopeOut(
            header = headers,
            body = response.payload,
            ocpp = ns_ocpp
        )
        return soapMapperOutput.writeValueAsString(xmlBuilder)
    }

    override fun parseAnyRequestFromSoap(messageStr: String): RequestSoapMessage<Any> {
        val envelope = readToEnvelop(messageStr)
        return RequestSoapMessage(
            messageId = envelope.header.messageId.value,
            chargingStationId = envelope.header.chargeBoxIdentity?.value ?: "Undefined",
            action = envelope.header.action.value.removePrefix("/"),
            from = envelope.header.from?.address?.value,
            to = envelope.header.to?.value,
            payload = getRequestBodyContent(envelope)
        )
    }

    override fun parseAnyResponseFromSoap(messageStr: String): ResponseSoapMessage<Any> {
        val envelope = readToEnvelop(messageStr)
        return ResponseSoapMessage(
            messageId = envelope.header.messageId.value,
            relatesTo = envelope.header.relatesTo?.value
                ?: throw IllegalArgumentException(
                    "Malformed envelope: missing <RelatesTo> in the header. envelope = $envelope"
                ),
            action = envelope.header.action.value.removePrefix("/").removeSuffix("Response"),
            payload = getResponseBodyContent(envelope),
            from = envelope.header.from?.address?.value,
            to = envelope.header.to.toString()
        )
    }

    override fun <T> mapRequestToSoap(request: RequestSoapMessage<T>): String {
        val headers = SoapHeaderOut(
            messageId = request.messageId,
            action = "/${request.action.replaceFirstChar { it.uppercase() }}",
            to = request.to ?: SOAP_ANONYMOUS,
            relatesTo = null,
            from = SoapHeaderFromOut(request.from ?: SOAP_ANONYMOUS),
            chargeBoxIdentity = request.chargingStationId
        )
        val xmlBuilder = SoapEnvelopeOut(
            header = headers,
            body = request.payload,
            ocpp = ns_ocpp
        )
        return soapMapperOutput.writeValueAsString(xmlBuilder)
    }
}

inline fun <reified T> OcppSoapParser.parseRequestFromSoap(messageStr: String): RequestSoapMessage<T> {
    val request = parseAnyRequestFromSoap(messageStr)
    if (request.payload !is T) {
        throw IllegalArgumentException(
            "The given request is not an instance of ${T::class.simpleName}. message = $messageStr"
        )
    }

    @Suppress("UNCHECKED_CAST")
    return request as RequestSoapMessage<T>
}

fun <T : Any> OcppSoapParser.parseRequestFromSoap(messageStr: String, clazz: KClass<T>): RequestSoapMessage<T> {
    val request = parseAnyRequestFromSoap(messageStr)
    if (request.payload::class != clazz) {
        throw IllegalArgumentException(
            "The given request is not an instance of ${clazz.simpleName}. message = $messageStr"
        )
    }

    @Suppress("UNCHECKED_CAST")
    return request as RequestSoapMessage<T>
}

inline fun <reified T> OcppSoapParser.parseResponseFromSoap(messageStr: String): ResponseSoapMessage<T> {
    val request = parseAnyResponseFromSoap(messageStr)
    if (request.payload !is T) {
        throw IllegalArgumentException(
            "The given response is not an instance of ${T::class.simpleName}. message = $messageStr"
        )
    }

    @Suppress("UNCHECKED_CAST")
    return request as ResponseSoapMessage<T>
}

fun <T : Any> OcppSoapParser.parseResponseFromSoap(messageStr: String, clazz: KClass<T>): ResponseSoapMessage<T> {
    val request = parseAnyResponseFromSoap(messageStr)
    if (request.payload::class != clazz) {
        throw IllegalArgumentException(
            "The given response is not an instance of ${clazz.simpleName}. message = $messageStr"
        )
    }

    @Suppress("UNCHECKED_CAST")
    return request as ResponseSoapMessage<T>
}
