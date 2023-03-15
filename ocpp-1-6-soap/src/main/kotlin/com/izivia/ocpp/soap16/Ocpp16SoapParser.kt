package com.izivia.ocpp.soap16

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.core16.Ocpp16ForcedFieldType
import com.izivia.ocpp.core16.Ocpp16IgnoredNullRestriction
import com.izivia.ocpp.soap.*
import com.izivia.ocpp.utils.*
import kotlin.reflect.full.memberProperties

class Ocpp16SoapParser(
    override val ignoredNullRestrictions: List<Ocpp16IgnoredNullRestriction>? = null,
    override val forcedFieldTypes: List<Ocpp16ForcedFieldType>? = null
) : OcppSoapParserImpl(
    ns_ocpp = "urn://Ocpp/Cp/2015/10/",
    soapMapperInput = Ocpp16SoapMapperIn,
    soapMapperOutput = Ocpp16SoapMapper
) {
    override fun readToEnvelop(
        soap: String,
        warningHandler: (warnings: List<ErrorDetail>) -> Unit
    ): SoapEnvelope<*> =
        try {
            soapMapperInput
                .readTree(soap)
                .apply {
                    applyDeserializerOptions(this, warningHandler)
                }?.let {
                    soapMapperInput
                        .readerFor(object : TypeReference<SoapEnvelope<Ocpp16SoapBody>>() {})
                        .readValue(it)
                }
        } catch (e: Exception) {
            parseSoapFaulted(soap, e) {
                Ocpp16SoapBody(fault = it)
            }
        } as SoapEnvelope<Ocpp16SoapBody>

    override fun getRequestBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealBodyContent(envelope as SoapEnvelope<Ocpp16SoapBody>)
            .isA<SoapFault> { it.toFaultReq() }

    override fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealBodyContent(envelope as SoapEnvelope<Ocpp16SoapBody>)

    private fun getRealBodyContent(envelope: SoapEnvelope<Ocpp16SoapBody>): Any {
        for (prop in Ocpp16SoapBody::class.memberProperties) {
            prop.get(envelope.body)?.let { return it }
        }
        throw IllegalArgumentException("Unknown request message operation. enveloppe = $envelope")
    }
}
