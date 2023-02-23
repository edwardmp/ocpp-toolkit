package com.izivia.ocpp.soap15

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.soap.*
import com.izivia.ocpp.utils.isA
import kotlin.reflect.full.memberProperties

class Ocpp15SoapParser(override val ignoredNullRestrictions: List<Ocpp15IgnoredNullRestriction> = emptyList()) :
    OcppSoapParserImpl(
        ns_ocpp = "urn://Ocpp/Cp/2012/06/",
        soapMapperInput = getOcpp15SoapMapperIn(ignoredNullRestrictions),
        soapMapperOutput = Ocpp15SoapMapper
    ) {
    override fun readToEnvelop(soap: String): SoapEnvelope<*> =
        try {
            soapMapperInput
                .readerFor(object : TypeReference<SoapEnvelope<Ocpp15SoapBody>>() {})
                .readValue<SoapEnvelope<Ocpp15SoapBody>?>(soap)
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
