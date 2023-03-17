package com.izivia.ocpp.soap15

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.core15.Ocpp15ForcedFieldType
import com.izivia.ocpp.core15.Ocpp15IgnoredNullRestriction
import com.izivia.ocpp.soap.*
import com.izivia.ocpp.utils.*
import kotlin.reflect.full.memberProperties

class Ocpp15SoapParser(
    override val ignoredNullRestrictions: List<Ocpp15IgnoredNullRestriction>? = null,
    override val forcedFieldTypes: List<Ocpp15ForcedFieldType>? = null,
) :
    OcppSoapParserImpl(
        ns_ocpp = "urn://Ocpp/Cp/2012/06/",
        soapMapperInput = Ocpp15SoapMapperIn,
        soapMapperOutput = Ocpp15SoapMapper
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
                        .readerFor(object : TypeReference<SoapEnvelope<Ocpp15SoapBody>>() {})
                        .readValue(it)
                }
        } catch (e: Exception) {
            parseSoapFaulted(soap, e) {
                Ocpp15SoapBody(fault = it)
            }
        } as SoapEnvelope<Ocpp15SoapBody>

    private fun getRealBodyContent(envelope: SoapEnvelope<Ocpp15SoapBody>): Any {
        for (prop in Ocpp15SoapBody::class.memberProperties) {
            prop.get(envelope.body)?.let { return it }
        }
        throw IllegalArgumentException("Unknown message operation. enveloppe = $envelope")
    }

    override fun getRequestBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealBodyContent(envelope as SoapEnvelope<Ocpp15SoapBody>)
            .isA<SoapFault> { it.toFaultReq() }

    override fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealBodyContent(envelope as SoapEnvelope<Ocpp15SoapBody>)
}
