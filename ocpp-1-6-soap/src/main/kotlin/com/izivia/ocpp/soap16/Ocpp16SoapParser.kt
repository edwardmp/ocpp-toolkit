package com.izivia.ocpp.soap16

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.soap.*

class Ocpp16SoapParser : OcppSoapParserImpl(
    ns_ocpp = "urn://Ocpp/Cp/2015/10/",
    soapMapperInput = Ocpp16SoapMapperIn,
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
        getRealRequestBodyContent(envelope as SoapEnvelope<Ocpp16SoapBody>)

    override fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealResponseBodyContent(envelope as SoapEnvelope<Ocpp16SoapBody>)

    private fun getRealRequestBodyContent(envelope: SoapEnvelope<Ocpp16SoapBody>): Any = when {
        envelope.body.authorizeRequest != null -> envelope.body.authorizeRequest!!
        envelope.body.bootNotificationRequest != null -> envelope.body.bootNotificationRequest!!
        envelope.body.dataTransferRequest != null -> envelope.body.dataTransferRequest!!
        envelope.body.diagnosticsStatusNotificationRequest != null ->
            envelope.body.diagnosticsStatusNotificationRequest!!

        envelope.body.firmwareStatusNotificationRequest != null -> envelope.body.firmwareStatusNotificationRequest!!
        envelope.body.heartbeatRequest != null -> envelope.body.heartbeatRequest!!
        envelope.body.meterValuesRequest != null -> envelope.body.meterValuesRequest!!
        envelope.body.startTransactionRequest != null -> envelope.body.startTransactionRequest!!
        envelope.body.remoteStartTransactionRequest != null -> envelope.body.remoteStartTransactionRequest!!
        envelope.body.statusNotificationRequest != null -> envelope.body.statusNotificationRequest!!
        envelope.body.stopTransactionRequest != null -> envelope.body.stopTransactionRequest!!
        envelope.body.remoteStopTransactionRequest != null -> envelope.body.remoteStopTransactionRequest!!
        envelope.body.getCompositeScheduleRequest != null -> envelope.body.getCompositeScheduleRequest!!
        envelope.body.setChargingProfileRequest != null -> envelope.body.setChargingProfileRequest!!
        envelope.body.fault != null -> envelope.body.fault!!.toFaultReq()
        else -> throw IllegalArgumentException("Unknown request message operation. enveloppe = $envelope")
    }

    private fun getRealResponseBodyContent(envelope: SoapEnvelope<Ocpp16SoapBody>): Any = when {
        envelope.body.authorizeResponse != null -> envelope.body.authorizeResponse!!
        envelope.body.bootNotificationResponse != null -> envelope.body.bootNotificationResponse!!
        envelope.body.dataTransferResponse != null -> envelope.body.dataTransferResponse!!
        envelope.body.diagnosticsStatusNotificationResponse != null ->
            envelope.body.diagnosticsStatusNotificationResponse!!

        envelope.body.firmwareStatusNotificationResponse != null -> envelope.body.firmwareStatusNotificationResponse!!
        envelope.body.heartbeatResponse != null -> envelope.body.heartbeatResponse!!
        envelope.body.meterValuesResponse != null -> envelope.body.meterValuesResponse!!
        envelope.body.startTransactionResponse != null -> envelope.body.startTransactionResponse!!
        envelope.body.remoteStartTransactionResponse != null -> envelope.body.remoteStartTransactionResponse!!
        envelope.body.statusNotificationResponse != null -> envelope.body.statusNotificationResponse!!
        envelope.body.stopTransactionResponse != null -> envelope.body.stopTransactionResponse!!
        envelope.body.remoteStopTransactionResponse != null -> envelope.body.remoteStopTransactionResponse!!
        envelope.body.getCompositeScheduleResponse != null -> envelope.body.getCompositeScheduleResponse!!
        envelope.body.setChargingProfileResponse != null -> envelope.body.setChargingProfileResponse!!
        envelope.body.fault != null -> envelope.body.fault!!
        else -> throw IllegalArgumentException("Unknown response message operation. enveloppe = $envelope")
    }
}
