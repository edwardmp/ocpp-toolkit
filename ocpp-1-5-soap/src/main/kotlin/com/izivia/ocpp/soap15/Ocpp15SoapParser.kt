package com.izivia.ocpp.soap15

import com.fasterxml.jackson.core.type.TypeReference
import com.izivia.ocpp.soap.*

class Ocpp15SoapParser : OcppSoapParserImpl(
    ns_ocpp = "urn://Ocpp/Cp/2012/06/",
    soapMapperInput = Ocpp15SoapMapperIn,
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

    override fun getRequestBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealRequestBodyContent(envelope as SoapEnvelope<Ocpp15SoapBody>)

    override fun getResponseBodyContent(envelope: SoapEnvelope<*>): Any =
        getRealResponseBodyContent(envelope as SoapEnvelope<Ocpp15SoapBody>)

    private fun getRealRequestBodyContent(envelope: SoapEnvelope<Ocpp15SoapBody>): Any = when {
        envelope.body.authorizeRequest != null -> envelope.body.authorizeRequest!!
        envelope.body.bootNotificationRequest != null -> envelope.body.bootNotificationRequest!!
        envelope.body.cancelReservationRequest != null -> envelope.body.cancelReservationRequest!!
        envelope.body.changeAvailabilityRequest != null -> envelope.body.changeAvailabilityRequest!!
        envelope.body.clearCacheRequest != null -> envelope.body.clearCacheRequest!!
        envelope.body.dataTransferRequest != null -> envelope.body.dataTransferRequest!!
        envelope.body.diagnosticsStatusNotificationRequest != null ->
            envelope.body.diagnosticsStatusNotificationRequest!!

        envelope.body.firmwareStatusNotificationRequest != null -> envelope.body.firmwareStatusNotificationRequest!!
        envelope.body.getConfigurationRequest != null -> envelope.body.getConfigurationRequest!!
        envelope.body.getDiagnosticsRequest != null -> envelope.body.getDiagnosticsRequest!!
        envelope.body.getLocalListVersionRequest != null -> envelope.body.getLocalListVersionRequest!!
        envelope.body.heartbeatRequest != null -> envelope.body.heartbeatRequest!!
        envelope.body.meterValuesRequest != null -> envelope.body.meterValuesRequest!!
        envelope.body.remoteStartTransactionRequest != null -> envelope.body.remoteStartTransactionRequest!!
        envelope.body.remoteStopTransactionRequest != null -> envelope.body.remoteStopTransactionRequest!!
        envelope.body.reserveNowRequest != null -> envelope.body.reserveNowRequest!!
        envelope.body.resetRequest != null -> envelope.body.resetRequest!!
        envelope.body.sendLocalListRequest != null -> envelope.body.sendLocalListRequest!!
        envelope.body.startTransactionRequest != null -> envelope.body.startTransactionRequest!!
        envelope.body.statusNotificationRequest != null -> envelope.body.statusNotificationRequest!!
        envelope.body.stopTransactionRequest != null -> envelope.body.stopTransactionRequest!!
        envelope.body.unlockConnectorRequest != null -> envelope.body.unlockConnectorRequest!!
        envelope.body.updateFirmwareRequest != null -> envelope.body.updateFirmwareRequest!!
        envelope.body.fault != null -> envelope.body.fault!!.toFaultReq()
        else -> throw IllegalArgumentException("Unknown request message operation. enveloppe = $envelope")
    }

    private fun getRealResponseBodyContent(envelope: SoapEnvelope<Ocpp15SoapBody>): Any = when {
        envelope.body.authorizeResponse != null -> envelope.body.authorizeResponse!!
        envelope.body.bootNotificationResponse != null -> envelope.body.bootNotificationResponse!!
        envelope.body.cancelReservationResponse != null -> envelope.body.cancelReservationResponse!!
        envelope.body.changeAvailabilityResponse != null -> envelope.body.changeAvailabilityResponse!!
        envelope.body.changeConfigurationResponse != null -> envelope.body.changeConfigurationResponse!!
        envelope.body.clearCacheResponse != null -> envelope.body.clearCacheResponse!!
        envelope.body.dataTransferResponse != null -> envelope.body.dataTransferResponse!!
        envelope.body.diagnosticsStatusNotificationResponse != null ->
            envelope.body.diagnosticsStatusNotificationResponse!!

        envelope.body.firmwareStatusNotificationResponse != null -> envelope.body.firmwareStatusNotificationResponse!!
        envelope.body.getConfigurationResponse != null -> envelope.body.getConfigurationResponse!!
        envelope.body.getDiagnosticsResponse != null -> envelope.body.getDiagnosticsResponse!!
        envelope.body.getLocalListVersionResponse != null -> envelope.body.getLocalListVersionResponse!!
        envelope.body.heartbeatResponse != null -> envelope.body.heartbeatResponse!!
        envelope.body.meterValuesResponse != null -> envelope.body.meterValuesResponse!!
        envelope.body.remoteStartTransactionResponse != null -> envelope.body.remoteStartTransactionResponse!!
        envelope.body.remoteStopTransactionResponse != null -> envelope.body.remoteStopTransactionResponse!!
        envelope.body.reserveNowResponse != null -> envelope.body.reserveNowResponse!!
        envelope.body.resetResponse != null -> envelope.body.resetResponse!!
        envelope.body.sendLocalListResponse != null -> envelope.body.sendLocalListResponse!!
        envelope.body.startTransactionResponse != null -> envelope.body.startTransactionResponse!!
        envelope.body.statusNotificationResponse != null -> envelope.body.statusNotificationResponse!!
        envelope.body.stopTransactionResponse != null -> envelope.body.stopTransactionResponse!!
        envelope.body.unlockConnectorResponse != null -> envelope.body.unlockConnectorResponse!!
        envelope.body.updateFirmwareResponse != null -> envelope.body.updateFirmwareResponse!!
        envelope.body.fault != null -> envelope.body.fault!!
        else -> throw IllegalArgumentException("Unknown response message operation. enveloppe = $envelope")
    }
}
