package com.izivia.ocpp.soap16

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.soap.*
import com.izivia.ocpp.utils.isA
import kotlin.reflect.full.memberProperties

class Ocpp16SoapParser(
    override val ignoredNullRestrictions: List<Ocpp16IgnoredNullRestriction> = emptyList()
) : OcppSoapParserImpl(
    ns_ocpp = "urn://Ocpp/Cp/2015/10/",
    soapMapperInput = getOcpp16SoapMapperIn(ignoredNullRestrictions),
    soapMapperOutput = Ocpp16SoapMapper
) {
    override fun readToEnvelop(soap: String): SoapEnvelope<*> =
        try {
            soapMapperInput
                .readerFor(object : TypeReference<SoapEnvelope<Ocpp16SoapBody>>() {})
                .readValue(soap)
        } catch (e: Exception) {
            parseSoapFaulted(soap, e) {
                Ocpp16SoapBody(fault = it)
            } as SoapEnvelope<Ocpp16SoapBody>
        }

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
