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
import com.izivia.ocpp.core16.model.getlog.GetLogReq
import com.izivia.ocpp.core16.model.getlog.GetLogResp
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
import com.izivia.ocpp.core16.model.updatefirmware.SignedUpdateFirmwareReq
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.soap.SoapBody
import com.izivia.ocpp.soap.SoapFault

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ocpp16SoapBody(
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
    val clearChargingProfileRequest: ClearChargingProfileReq? = null,
    val clearChargingProfileResponse: ClearChargingProfileResp? = null,
    val dataTransferRequest: DataTransferReq? = null,
    val dataTransferResponse: DataTransferResp? = null,
    val diagnosticsStatusNotificationRequest: DiagnosticsStatusNotificationReq? = null,
    val diagnosticsStatusNotificationResponse: DiagnosticsStatusNotificationResp? = null,
    val firmwareStatusNotificationRequest: FirmwareStatusNotificationReq? = null,
    val firmwareStatusNotificationResponse: FirmwareStatusNotificationResp? = null,
    val getCompositeScheduleRequest: GetCompositeScheduleReq? = null,
    val getCompositeScheduleResponse: GetCompositeScheduleResp? = null,
    val getConfigurationRequest: GetConfigurationReq? = null,
    val getConfigurationResponse: GetConfigurationResp? = null,
    val getDiagnosticsRequest: GetDiagnosticsReq? = null,
    val getDiagnosticsResponse: GetDiagnosticsResp? = null,
    val getLocalListVersionResponse: GetLocalListVersionResp? = null,
    val getLocalListVersionRequest: GetLocalListVersionReq? = null,
    val getLogRequest: GetLogReq? = null,
    val getLogResponse: GetLogResp? = null,
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
    val setChargingProfileRequest: SetChargingProfileReq? = null,
    val setChargingProfileResponse: SetChargingProfileResp? = null,
    val startTransactionRequest: StartTransactionReq? = null,
    val startTransactionResponse: StartTransactionResp? = null,
    val statusNotificationRequest: StatusNotificationReq? = null,
    val statusNotificationResponse: StatusNotificationResp? = null,
    val stopTransactionRequest: StopTransactionReq? = null,
    val stopTransactionResponse: StopTransactionResp? = null,
    val triggerMessageRequest: TriggerMessageReq? = null,
    val triggerMessageResponse: TriggerMessageResp? = null,
    val unlockConnectorRequest: UnlockConnectorReq? = null,
    val unlockConnectorResponse: UnlockConnectorResp? = null,
    val updateFirmwareRequest: SignedUpdateFirmwareReq? = null,
    val updateFirmwareResponse: UpdateFirmwareResp? = null,
    val fault: SoapFault? = null
) : SoapBody
