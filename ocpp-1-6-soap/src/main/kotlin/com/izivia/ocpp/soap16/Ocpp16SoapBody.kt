package com.izivia.ocpp.soap16

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.izivia.ocpp.core16.model.authorize.AuthorizeReq
import com.izivia.ocpp.core16.model.authorize.AuthorizeResp
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core16.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core16.model.changeavailability.ChangeAvailabilityResp
import com.izivia.ocpp.core16.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core16.model.changeconfiguration.ChangeConfigurationResp
import com.izivia.ocpp.core16.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core16.model.clearcache.ClearCacheResp
import com.izivia.ocpp.core16.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core16.model.clearchargingprofile.ClearChargingProfileResp
import com.izivia.ocpp.core16.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core16.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationResp
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core16.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core16.model.getcompositeschedule.GetCompositeScheduleResp
import com.izivia.ocpp.core16.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core16.model.getconfiguration.GetConfigurationResp
import com.izivia.ocpp.core16.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core16.model.getdiagnostics.GetDiagnosticsResp
import com.izivia.ocpp.core16.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core16.model.getlocallistversion.GetLocalListVersionResp
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core16.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core16.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core16.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core16.model.remotestart.RemoteStartTransactionResp
import com.izivia.ocpp.core16.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core16.model.remotestop.RemoteStopTransactionResp
import com.izivia.ocpp.core16.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core16.model.reservenow.ReserveNowResp
import com.izivia.ocpp.core16.model.reset.ResetReq
import com.izivia.ocpp.core16.model.reset.ResetResp
import com.izivia.ocpp.core16.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core16.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core16.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core16.model.setchargingprofile.SetChargingProfileResp
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.core16.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core16.model.triggermessage.TriggerMessageResp
import com.izivia.ocpp.core16.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core16.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.soap.SoapBody
import com.izivia.ocpp.soap.SoapFault

@JsonIgnoreProperties(ignoreUnknown = true)
internal data class Ocpp16SoapBody(
    val authorizeRequest: AuthorizeReq?,
    val authorizeResponse: AuthorizeResp?,
    val bootNotificationRequest: BootNotificationReq?,
    val bootNotificationResponse: BootNotificationResp?,
    val cancelReservationRequest: CancelReservationReq?,
    val cancelReservationResponse: CancelReservationResp?,
    val changeAvailabilityRequest: ChangeAvailabilityReq?,
    val changeAvailabilityResponse: ChangeAvailabilityResp?,
    val changeConfigurationRequest: ChangeConfigurationReq?,
    val changeConfigurationResponse: ChangeConfigurationResp?,
    val clearCacheRequest: ClearCacheReq?,
    val clearCacheResponse: ClearCacheResp?,
    val clearChargingProfileRequest: ClearChargingProfileReq?,
    val clearChargingProfileResponse: ClearChargingProfileResp?,
    val dataTransferRequest: DataTransferReq?,
    val dataTransferResponse: DataTransferResp?,
    val diagnosticsStatusNotificationRequest: DiagnosticsStatusNotificationReq?,
    val diagnosticsStatusNotificationResponse: DiagnosticsStatusNotificationResp?,
    val firmwareStatusNotificationRequest: FirmwareStatusNotificationReq?,
    val firmwareStatusNotificationResponse: FirmwareStatusNotificationResp?,
    val getCompositeScheduleRequest: GetCompositeScheduleReq?,
    val getCompositeScheduleResponse: GetCompositeScheduleResp?,
    val getConfigurationRequest: GetConfigurationReq?,
    val getConfigurationResponse: GetConfigurationResp?,
    val getDiagnosticsRequest: GetDiagnosticsReq?,
    val getDiagnosticsResponse: GetDiagnosticsResp?,
    val getLocalListVersionResponse: GetLocalListVersionResp?,
    val getLocalListVersionRequest: GetLocalListVersionReq?,
    val heartbeatRequest: HeartbeatReq?,
    val heartbeatResponse: HeartbeatResp?,
    val meterValuesRequest: MeterValuesReq?,
    val meterValuesResponse: MeterValuesResp?,
    val remoteStartTransactionRequest: RemoteStartTransactionReq?,
    val remoteStartTransactionResponse: RemoteStartTransactionResp?,
    val remoteStopTransactionRequest: RemoteStopTransactionReq?,
    val remoteStopTransactionResponse: RemoteStopTransactionResp?,
    val reserveNowRequest: ReserveNowReq?,
    val reserveNowResponse: ReserveNowResp?,
    val resetRequest: ResetReq?,
    val resetResponse: ResetResp?,
    val sendLocalListRequest: SendLocalListReq?,
    val sendLocalListResponse: SendLocalListResp?,
    val setChargingProfileRequest: SetChargingProfileReq?,
    val setChargingProfileResponse: SetChargingProfileResp?,
    val startTransactionRequest: StartTransactionReq?,
    val startTransactionResponse: StartTransactionResp?,
    val statusNotificationRequest: StatusNotificationReq?,
    val statusNotificationResponse: StatusNotificationResp?,
    val stopTransactionRequest: StopTransactionReq?,
    val stopTransactionResponse: StopTransactionResp?,
    val triggerMessageRequest: TriggerMessageReq?,
    val triggerMessageResponse: TriggerMessageResp?,
    val unlockConnectorRequest: UnlockConnectorReq?,
    val unlockConnectorResponse: UnlockConnectorResp?,
    val updateFirmwareRequest: UpdateFirmwareReq?,
    val updateFirmwareResponse: UpdateFirmwareResp?,
    val fault: SoapFault?
) : SoapBody
