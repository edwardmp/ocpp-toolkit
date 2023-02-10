package com.izivia.ocpp.soap15

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.izivia.ocpp.core15.model.authorize.AuthorizeReq
import com.izivia.ocpp.core15.model.authorize.AuthorizeResp
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityResp
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationResp
import com.izivia.ocpp.core15.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core15.model.clearcache.ClearCacheResp
import com.izivia.ocpp.core15.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core15.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationResp
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationResp
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsResp
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core15.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core15.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionResp
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionResp
import com.izivia.ocpp.core15.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core15.model.reservenow.ReserveNowResp
import com.izivia.ocpp.core15.model.reset.ResetReq
import com.izivia.ocpp.core15.model.reset.ResetResp
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.soap.SoapBody
import com.izivia.ocpp.soap.SoapFault

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ocpp15SoapBody(
    val authorizeRequest: AuthorizeReq? = null,
    val authorizeResponse: AuthorizeResp? = null,
    val bootNotificationRequest: BootNotificationReq? = null,
    val bootNotificationResponse: BootNotificationResp? = null,
    val cancelReservationRequest: CancelReservationReq? = null,
    val cancelReservationResponse: CancelReservationResp? = null,
    val changeAvailabilityRequest: ChangeAvailabilityReq? = null,
    val changeAvailabilityResponse: ChangeAvailabilityResp? = null,
    val changeConfigurationRequest: ChangeConfigurationReq? = null,
    val changeConfigurationResponse: ChangeConfigurationResp? = null,
    val clearCacheRequest: ClearCacheReq? = null,
    val clearCacheResponse: ClearCacheResp? = null,
    val dataTransferRequest: DataTransferReq? = null,
    val dataTransferResponse: DataTransferResp? = null,
    val diagnosticsStatusNotificationRequest: DiagnosticsStatusNotificationReq? = null,
    val diagnosticsStatusNotificationResponse: DiagnosticsStatusNotificationResp? = null,
    val firmwareStatusNotificationRequest: FirmwareStatusNotificationReq? = null,
    val firmwareStatusNotificationResponse: FirmwareStatusNotificationResp? = null,
    val getConfigurationRequest: GetConfigurationReq? = null,
    val getConfigurationResponse: GetConfigurationResp? = null,
    val getDiagnosticsRequest: GetDiagnosticsReq? = null,
    val getDiagnosticsResponse: GetDiagnosticsResp? = null,
    val getLocalListVersionResponse: GetLocalListVersionReq? = null,
    val getLocalListVersionRequest: GetLocalListVersionReq? = null,
    val heartbeatRequest: HeartbeatReq? = null,
    val heartbeatResponse: HeartbeatResp? = null,
    val meterValuesRequest: MeterValuesReq? = null,
    val meterValuesResponse: MeterValuesResp? = null,
    val remoteStartTransactionRequest: RemoteStartTransactionReq? = null,
    val remoteStartTransactionResponse: RemoteStartTransactionResp? = null,
    val remoteStopTransactionRequest: RemoteStopTransactionReq? = null,
    val remoteStopTransactionResponse: RemoteStopTransactionResp? = null,
    val reserveNowRequest: ReserveNowReq? = null,
    val reserveNowResponse: ReserveNowResp? = null,
    val resetRequest: ResetReq? = null,
    val resetResponse: ResetResp? = null,
    val sendLocalListRequest: SendLocalListReq? = null,
    val sendLocalListResponse: SendLocalListResp? = null,
    val startTransactionRequest: StartTransactionReq? = null,
    val startTransactionResponse: StartTransactionResp? = null,
    val statusNotificationRequest: StatusNotificationReq? = null,
    val statusNotificationResponse: StatusNotificationResp? = null,
    val stopTransactionRequest: StopTransactionReq? = null,
    val stopTransactionResponse: StopTransactionResp? = null,
    val unlockConnectorRequest: UnlockConnectorReq? = null,
    val unlockConnectorResponse: UnlockConnectorResp? = null,
    val updateFirmwareRequest: UpdateFirmwareReq? = null,
    val updateFirmwareResponse: UpdateFirmwareResp? = null,
    val fault: SoapFault? = null
) : SoapBody
