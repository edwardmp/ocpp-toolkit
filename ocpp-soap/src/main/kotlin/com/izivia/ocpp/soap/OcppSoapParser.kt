package com.izivia.ocpp.soap

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.izivia.ocpp.utils.*
import com.izivia.ocpp.utils.fault.FAULT
import com.izivia.ocpp.utils.fault.Fault
import kotlin.reflect.KClass

interface OcppSoapParser {

    fun parseAnyRequestFromSoap(messageStr: String): RequestSoapMessage<Any>
    fun parseAnyResponseFromSoap(messageStr: String): ResponseSoapMessage<Any>
    fun <T> mapRequestToSoap(request: RequestSoapMessage<T>): String
    fun <T> mapResponseToSoap(response: ResponseSoapMessage<T>): String

    fun applyDeserializerOptions(node: JsonNode, warningHandler: (warnings: List<ErrorDetail>) -> Unit)

    fun readToEnvelop(soap: String, warningHandler: (warnings: List<ErrorDetail>) -> Unit = {}): SoapEnvelope<*>

    fun getRequestBodyContent(envelope: SoapEnvelope<*>): Any
    fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any

    fun parseSoapFaulted(
        soap: String,
        e: Exception,
        func: (soapFault: SoapFault) -> SoapBody
    ): SoapEnvelope<SoapBody>
}

abstract class OcppSoapParserImpl(
    private val ns_ocpp: String,
    private val soapMapperOutput: ObjectMapper,
    val soapMapperInput: ObjectMapper,
    open val ignoredNullRestrictions: List<AbstractIgnoredNullRestriction>? = null,
    open val forcedFieldTypes: List<AbstractForcedFieldType>? = null,
) : OcppSoapParser {

    companion object {
        val isNodeAction: (node: JsonNode, rule: InterfaceFieldOption) -> JsonNode? =
            { node, rule ->
                node.first().takeIf { node.has(rule.getBodyAction()) }
            }
    }

    override fun applyDeserializerOptions(node: JsonNode, warningHandler: (warnings: List<ErrorDetail>) -> Unit) {
        ignoredNullRestrictions?.parseNullField(node.findValue(OcppConstant.BODY), isNodeAction)
            ?.let { warns -> warningHandler(warns) }

        forcedFieldTypes?.parseFieldToConvert(node.findValue(OcppConstant.BODY), isNodeAction)
            ?.let { warns -> warningHandler(warns) }
    }

    override fun <T> mapResponseToSoap(response: ResponseSoapMessage<T>): String {
        val headers = SoapHeaderOut(
            messageId = response.messageId,
            action = "/${response.action.replaceFirstChar { it.uppercase() }}Response",
            relatesTo = response.relatesTo,
            to = response.to ?: OcppConstant.SOAP_ANONYMOUS,
            from = response.from?.let { SoapHeaderFromOut(response.from.toValueText()) }
                ?: OcppConstant.SOAP_ANONYMOUS_FROM,
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
        val warnings = mutableListOf<ErrorDetail>()
        val envelope = readToEnvelop(messageStr) { warnings.addAll(it) }
        try {
            return RequestSoapMessage(
                messageId = envelope.header.messageId.value,
                chargingStationId = envelope.header.chargeBoxIdentity?.value ?: "Undefined",
                action = envelope.header.action.value.removePrefix("/"),
                from = envelope.header.from.address.value,
                to = envelope.header.to.value,
                payload = getRequestBodyContent(envelope),
                warnings = warnings.takeIf { it.size > 0 }
            )
        } catch (e: Exception) {
            return RequestSoapMessage(
                messageId = envelope.header.messageId.value,
                chargingStationId = envelope.header.chargeBoxIdentity?.value ?: "Undefined",
                action = envelope.header.action.value.removePrefix("/"),
                payload = buildSoapFault(action = envelope.header.action.value, soap = messageStr, e = e),
                from = envelope.header.from.address.value,
                to = envelope.header.to.toString(),
                warnings = warnings.takeIf { it.size > 0 }
            )
        }
    }

    override fun parseAnyResponseFromSoap(messageStr: String): ResponseSoapMessage<Any> {
        val warnings = mutableListOf<ErrorDetail>()
        val envelope = readToEnvelop(messageStr) {
            warnings.addAll(it)
        }
        return ResponseSoapMessage(
            messageId = envelope.header.messageId.value,
            relatesTo = envelope.header.relatesTo?.value ?: "missing-relatesTo".also {
                warnings.add(
                    ErrorDetail(
                        code = ErrorDetailCode.MISSING_FIELD_REPLACED.value,
                        detail = "Missing relatesTo value : $envelope"
                    )
                )
            },
            action = envelope.header.action.value.removePrefix("/").removeSuffix("Response"),
            payload = try {
                getResponseBodyContent(envelope)
            } catch (e: Exception) {
                buildSoapFault(action = envelope.header.action.value, soap = messageStr, e = e)
            },
            from = envelope.header.from.address.value,
            to = envelope.header.to.toString(),
            warnings = warnings.takeIf { it.size > 0 }
        )
    }

    override fun <T> mapRequestToSoap(request: RequestSoapMessage<T>): String {
        val headers = SoapHeaderOut(
            messageId = request.messageId,
            action = "/${request.action.replaceFirstChar { it.uppercase() }}",
            to = request.to ?: OcppConstant.SOAP_ANONYMOUS,
            relatesTo = null,
            from = request.from?.let { SoapHeaderFromOut(request.from.toValueText()) }
                ?: OcppConstant.SOAP_ANONYMOUS_FROM,
            chargeBoxIdentity = request.chargingStationId
        )
        val xmlBuilder = SoapEnvelopeOut(
            header = headers,
            body = request.payload,
            ocpp = ns_ocpp
        )
        return soapMapperOutput.writeValueAsString(xmlBuilder)
    }

    override fun parseSoapFaulted(
        soap: String,
        e: Exception,
        func: (soapFault: SoapFault) -> SoapBody
    ): SoapEnvelope<SoapBody> {
        val chargeBoxIdentity: String? =
            """${OcppConstant.CHARGEBOX_IDENTITY.lowercase()}>(.*?)</""".toRegex(RegexOption.IGNORE_CASE)
                .find(soap)?.destructured?.component1()
        val messageId: String? =
            """${OcppConstant.MESSAGE_ID.lowercase()}>(.*?)</""".toRegex(RegexOption.IGNORE_CASE)
                .find(soap)?.destructured?.component1()
        val action: String? =
            """${OcppConstant.ACTION.lowercase()}>(.*?)</""".toRegex(RegexOption.IGNORE_CASE)
                .find(soap)?.destructured?.component1()

        return SoapEnvelope(
            header = SoapHeader(
                chargeBoxIdentity = chargeBoxIdentity?.let { ValueText(it) },
                messageId = messageId?.let { ValueText(it) }
                    ?: ValueText("Unknown-messageId"),
                action = ValueText(FAULT),
                relatesTo = null
            ),
            body = func(buildSoapFault(action, soap, e))
        )
    }
}

fun SoapFault.toFaultReq() =
    MessageErrorCode.fromValue(this.code.subCode.value.value).let { error ->
        Fault(
            errorCode = error.errorCode,
            errorDescription = error.description, // reason.text.value.value,
            errorDetails = listOf(
                ErrorDetail(
                    code = this.code.subCode.value.value,
                    detail = reason.text.value.value
                )
            ).plus(
                this.value?.errorDetails?.map {
                    ErrorDetail(code = it.key.value, detail = it.value.value)
                } ?: emptyList()
            )
        )
    }

private fun buildSoapFault(action: String?, soap: String, e: Exception) = SoapFault(
    code = FaultCode(
        value = FaultCodeValue.SENDER,
        subCode = FaultSubCode(value = FaultSubCodeValue.PROTOCOL_ERROR)
    ),
    reason = FaultReason(text = FaultReasonText(value = FaultReasonTextValue.PROTOCOL_ERROR)),
    value = FaultValue(
        errorDescription = ValueText(e.toString()),
        errorDetails = mutableMapOf(
            Pair(ValueText(ErrorDetailCode.STACKTRACE.value), ValueText(e.stackTraceToString())),
            Pair(ValueText(ErrorDetailCode.PAYLOAD.value), ValueText(soap))
        )
            .also { map ->
                action?.let {
                    map[ValueText(ErrorDetailCode.ACTION.value)] = ValueText(it)
                }
            }
            .toMap()
    )
)

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
