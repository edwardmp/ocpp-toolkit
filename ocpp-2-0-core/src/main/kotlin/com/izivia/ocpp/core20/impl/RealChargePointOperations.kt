package com.izivia.ocpp.core20.impl

import com.izivia.ocpp.core20.CSMSOperations
import com.izivia.ocpp.core20.ChargePointOperations
import com.izivia.ocpp.core20.model.authorize.AuthorizeReq
import com.izivia.ocpp.core20.model.authorize.AuthorizeResp
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core20.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core20.model.certificateSigned.CertificateSignedReq
import com.izivia.ocpp.core20.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core20.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core20.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core20.model.cleardisplaymessage.ClearDisplayMessageReq
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitReq
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitResp
import com.izivia.ocpp.core20.model.clearvariablemonitoring.ClearVariableMonitoringReq
import com.izivia.ocpp.core20.model.costupdated.CostUpdatedReq
import com.izivia.ocpp.core20.model.customerinformation.CustomerInformationReq
import com.izivia.ocpp.core20.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core20.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core20.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core20.model.getbasereport.GetBaseReportReq
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusReq
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusResp
import com.izivia.ocpp.core20.model.getchargingprofiles.GetChargingProfilesReq
import com.izivia.ocpp.core20.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core20.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core20.model.getdisplaymessages.GetDisplayMessagesReq
import com.izivia.ocpp.core20.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core20.model.getlog.GetLogReq
import com.izivia.ocpp.core20.model.getmonitoringreport.GetMonitoringReportReq
import com.izivia.ocpp.core20.model.getreport.GetReportReq
import com.izivia.ocpp.core20.model.gettransactionstatus.GetTransactionStatusReq
import com.izivia.ocpp.core20.model.getvariables.GetVariablesReq
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core20.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core20.model.logstatusnotification.LogStatusNotificationReq
import com.izivia.ocpp.core20.model.logstatusnotification.LogStatusNotificationResp
import com.izivia.ocpp.core20.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core20.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core20.model.notifycharginglimit.NotifyChargingLimitReq
import com.izivia.ocpp.core20.model.notifycharginglimit.NotifyChargingLimitResp
import com.izivia.ocpp.core20.model.notifycustomerinformation.NotifyCustomerInformationReq
import com.izivia.ocpp.core20.model.notifycustomerinformation.NotifyCustomerInformationResp
import com.izivia.ocpp.core20.model.notifydisplaymessages.NotifyDisplayMessagesReq
import com.izivia.ocpp.core20.model.notifydisplaymessages.NotifyDisplayMessagesResp
import com.izivia.ocpp.core20.model.notifyevchargingneeds.NotifyEVChargingNeedsReq
import com.izivia.ocpp.core20.model.notifyevchargingneeds.NotifyEVChargingNeedsResp
import com.izivia.ocpp.core20.model.notifyevchargingschedule.NotifyEVChargingScheduleReq
import com.izivia.ocpp.core20.model.notifyevchargingschedule.NotifyEVChargingScheduleResp
import com.izivia.ocpp.core20.model.notifyevent.NotifyEventReq
import com.izivia.ocpp.core20.model.notifyevent.NotifyEventResp
import com.izivia.ocpp.core20.model.notifymonitoringreport.NotifyMonitoringReportReq
import com.izivia.ocpp.core20.model.notifymonitoringreport.NotifyMonitoringReportResp
import com.izivia.ocpp.core20.model.notifyreport.NotifyReportReq
import com.izivia.ocpp.core20.model.notifyreport.NotifyReportResp
import com.izivia.ocpp.core20.model.publishfirmware.PublishFirmwareReq
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationResp
import com.izivia.ocpp.core20.model.remotestart.RequestStartTransactionReq
import com.izivia.ocpp.core20.model.remotestop.RequestStopTransactionReq
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesReq
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesResp
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateReq
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateResp
import com.izivia.ocpp.core20.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core20.model.reset.ResetReq
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationResp
import com.izivia.ocpp.core20.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core20.model.setdisplaymessage.SetDisplayMessageReq
import com.izivia.ocpp.core20.model.setvariablemonitoring.SetVariableMonitoringReq
import com.izivia.ocpp.core20.model.setmonitoringlevel.SetMonitoringLevelReq
import com.izivia.ocpp.core20.model.setnetworkprofile.SetNetworkProfileReq
import com.izivia.ocpp.core20.model.setmonitoringbase.SetMonitoringBaseReq
import com.izivia.ocpp.core20.model.setvariables.SetVariablesReq
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateResp
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventReq
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventResp
import com.izivia.ocpp.core20.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core20.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core20.model.unpublishfirmware.UnpublishFirmwareReq
import com.izivia.ocpp.core20.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.operation.information.ExecutionMetadata
import com.izivia.ocpp.operation.information.OperationExecution
import com.izivia.ocpp.operation.information.RequestMetadata
import com.izivia.ocpp.operation.information.RequestStatus
import com.izivia.ocpp.transport.Transport
import com.izivia.ocpp.transport.receiveMessage
import com.izivia.ocpp.transport.sendMessage
import kotlinx.datetime.Clock
import java.net.ConnectException

class RealChargePointOperations(
    private val chargeStationId: String,
    private val client: Transport,
    private val csmsOperations: CSMSOperations
) : ChargePointOperations {

    init {
        client.receiveMessage("Reset") { req: ResetReq ->
            csmsOperations.reset(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("ChangeAvailability") { req: ChangeAvailabilityReq ->
            csmsOperations.changeAvailability(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("SetVariables") { req: SetVariablesReq ->
            csmsOperations.setVariables(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("ClearCache") { req: ClearCacheReq ->
            csmsOperations.clearCache(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("RequestStartTransaction") { req: RequestStartTransactionReq ->
            csmsOperations.requestStartTransaction(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("RequestStopTransaction") { req: RequestStopTransactionReq ->
            csmsOperations.requestStopTransaction(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("UnlockConnector") { req: UnlockConnectorReq ->
            csmsOperations.unlockConnector(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetVariables") { req: GetVariablesReq ->
            csmsOperations.getVariables(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetReport") { req: GetReportReq ->
            csmsOperations.getReport(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetBaseReport") { req: GetBaseReportReq ->
            csmsOperations.getBaseReport(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("SetChargingProfile") { req: SetChargingProfileReq ->
            csmsOperations.setChargingProfile(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("TriggerMessage") { req: TriggerMessageReq ->
            csmsOperations.triggerMessage(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("SendLocalList") { req: SendLocalListReq ->
            csmsOperations.sendLocalList(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetLocalListVersion") { req: GetLocalListVersionReq ->
            csmsOperations.getLocalListVersion(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("CancelReservation") { req: CancelReservationReq ->
            csmsOperations.cancelReservation(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("ClearChargingProfile") { req: ClearChargingProfileReq ->
            csmsOperations.clearChargingProfile(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetCompositeSchedule") { req: GetCompositeScheduleReq ->
            csmsOperations.getCompositeSchedule(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("UpdateFirmware") { req: UpdateFirmwareReq ->
            csmsOperations.updateFirmware(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("ReserveNow") { req: ReserveNowReq ->
            csmsOperations.reserveNow(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("DataTransfer") { req: DataTransferReq ->
            csmsOperations.dataTransfer(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("CertificateSigned") { req: CertificateSignedReq ->
            csmsOperations.certificateSigned(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetLog") { req: GetLogReq ->
            csmsOperations.getLog(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("ClearDisplayMessage") { req: ClearDisplayMessageReq ->
            csmsOperations.clearDisplayMessage(
                RequestMetadata(chargeStationId),
                req
            ).response
        }

        client.receiveMessage("GetInstalledCertificateIds") { req: GetInstalledCertificateIdsReq ->
            csmsOperations.getInstalledCertificateIds(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("InstallCertificate") { req: InstallCertificateReq ->
            csmsOperations.installCertificate(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("CustomerInformation") { req: CustomerInformationReq ->
            csmsOperations.customerInformation(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("UnpublishFirmware") { req: UnpublishFirmwareReq ->
            csmsOperations.unpublishFirmware(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("GetChargingProfiles") { req: GetChargingProfilesReq ->
            csmsOperations.getChargingProfiles(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("PublishFirmware") { req: PublishFirmwareReq ->
            csmsOperations.publishFirmware(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("SetVariableMonitoring") { req: SetVariableMonitoringReq ->
            csmsOperations.setVariableMonitoring(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }


        client.receiveMessage("SetMonitoringLevel") { req: SetMonitoringLevelReq ->
            csmsOperations.setMonitoringLevel(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("SetNetworkProfile") { req: SetNetworkProfileReq ->
            csmsOperations.setNetworkProfile(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("GetTransactionStatus") { req: GetTransactionStatusReq ->
            csmsOperations.getTransactionStatus(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("SetMonitoringBase") { req: SetMonitoringBaseReq ->
            csmsOperations.setMonitoringBase(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("GetDisplayMessages") { req: GetDisplayMessagesReq ->
            csmsOperations.getDisplayMessages(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("CostUpdated") { req: CostUpdatedReq ->
            csmsOperations.costUpdated(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("SetDisplayMessage") { req: SetDisplayMessageReq ->
            csmsOperations.setDisplayMessage(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("DeleteCertificate") { req: DeleteCertificateReq ->
            csmsOperations.deleteCertificate(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("GetMonitoringReport") { req: GetMonitoringReportReq ->
            csmsOperations.getMonitoringReport(
                    RequestMetadata(chargeStationId),
                    req
            ).response
        }

        client.receiveMessage("ClearVariableMonitoring") { req: ClearVariableMonitoringReq ->
            csmsOperations.clearVariableMonitoring(
                RequestMetadata(chargeStationId),
                req
            ).response
        }
    }

    private inline fun <T, reified P> sendMessage(
        meta: RequestMetadata,
        action: String,
        request: T
    ): OperationExecution<T, P> {
        val requestTime = Clock.System.now()
        val response: P = client.sendMessage(action, request)
        val responseTime = Clock.System.now()
        return OperationExecution(ExecutionMetadata(meta, RequestStatus.SUCCESS, requestTime, responseTime), request, response)
    }

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun heartbeat(meta: RequestMetadata, request: HeartbeatReq): OperationExecution<HeartbeatReq, HeartbeatResp> =
        sendMessage(meta, "Heartbeat", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun authorize(meta: RequestMetadata, request: AuthorizeReq): OperationExecution<AuthorizeReq, AuthorizeResp> =
        sendMessage(meta, "Authorize", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun meterValues(meta: RequestMetadata, request: MeterValuesReq): OperationExecution<MeterValuesReq, MeterValuesResp> =
        sendMessage(meta, "MeterValues", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun transactionEvent(
        meta: RequestMetadata,
        request: TransactionEventReq
    ): OperationExecution<TransactionEventReq, TransactionEventResp> =
        sendMessage(meta, "TransactionEvent", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun statusNotification(
        meta: RequestMetadata,
        request: StatusNotificationReq
    ): OperationExecution<StatusNotificationReq, StatusNotificationResp> =
        sendMessage(meta, "StatusNotification", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun dataTransfer(meta: RequestMetadata, request: DataTransferReq): OperationExecution<DataTransferReq, DataTransferResp> =
        sendMessage(meta, "DataTransfer", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun bootNotification(
        meta: RequestMetadata,
        request: BootNotificationReq
    ): OperationExecution<BootNotificationReq, BootNotificationResp> =
        sendMessage(meta, "BootNotification", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun notifyReport(
        meta: RequestMetadata,
        request: NotifyReportReq
    ): OperationExecution<NotifyReportReq, NotifyReportResp> =
        sendMessage(meta, "NotifyReport", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun firmwareStatusNotification(
        meta: RequestMetadata,
        request: FirmwareStatusNotificationReq
    ): OperationExecution<FirmwareStatusNotificationReq, FirmwareStatusNotificationResp> =
            sendMessage(meta, "FirmwareStatusNotification", request)

    override fun clearedChargingLimit(
        meta: RequestMetadata,
        request: ClearedChargingLimitReq
    ): OperationExecution<ClearedChargingLimitReq, ClearedChargingLimitResp> =
        sendMessage(meta, "ClearedChargingLimit", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun getCertificateStatus(
        meta: RequestMetadata,
        request: GetCertificateStatusReq
    ): OperationExecution<GetCertificateStatusReq, GetCertificateStatusResp> =
        sendMessage(meta, "GetCertificateStatus", request)

    override fun notifyCustomerInformation(
        meta: RequestMetadata,
        request: NotifyCustomerInformationReq
    ): OperationExecution<NotifyCustomerInformationReq, NotifyCustomerInformationResp> =
        sendMessage(meta, "NotifyCustomerInformation", request)

    override fun notifyEvent(
        meta: RequestMetadata,
        request: NotifyEventReq
    ): OperationExecution<NotifyEventReq, NotifyEventResp> =
        sendMessage(meta, "NotifyEvent", request)

    override fun notifyEVChargingSchedule(
        meta: RequestMetadata,
        request: NotifyEVChargingScheduleReq
    ): OperationExecution<NotifyEVChargingScheduleReq, NotifyEVChargingScheduleResp> =
        sendMessage(meta, "NotifyEVChargingSchedule", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun notifyChargingLimit(
        meta: RequestMetadata,
        request: NotifyChargingLimitReq
    ): OperationExecution<NotifyChargingLimitReq, NotifyChargingLimitResp> =
        sendMessage(meta, "NotifyChargingLimit", request)

    override fun notifyDisplayMessages(
        meta: RequestMetadata,
        request: NotifyDisplayMessagesReq
    ): OperationExecution<NotifyDisplayMessagesReq, NotifyDisplayMessagesResp> =
        sendMessage(meta, "NotifyDisplayMessages", request)

    @Throws(IllegalStateException::class, ConnectException::class)
    override fun notifyEVChargingNeeds(
        meta: RequestMetadata,
        request: NotifyEVChargingNeedsReq
    ): OperationExecution<NotifyEVChargingNeedsReq, NotifyEVChargingNeedsResp> =
        sendMessage(meta, "NotifyEVChargingNeeds", request)

    override fun logStatusNotification(
        meta: RequestMetadata,
        request: LogStatusNotificationReq
    ): OperationExecution<LogStatusNotificationReq, LogStatusNotificationResp> =
        sendMessage(meta, "LogStatusNotification", request)

    override fun publishFirmwareStatusNotification(
        meta: RequestMetadata,
        request: PublishFirmwareStatusNotificationReq
    ): OperationExecution<PublishFirmwareStatusNotificationReq, PublishFirmwareStatusNotificationResp> =
        sendMessage(meta, "PublishFirmwareStatusNotification", request)

    override fun notifyMonitoringReport(
        meta: RequestMetadata,
        request: NotifyMonitoringReportReq
    ): OperationExecution<NotifyMonitoringReportReq, NotifyMonitoringReportResp> =
        sendMessage(meta, "NotifyMonitoringReport", request)

    override fun reservationStatusUpdate(
        meta: RequestMetadata,
        request: ReservationStatusUpdateReq
    ): OperationExecution<ReservationStatusUpdateReq, ReservationStatusUpdateResp> =
        sendMessage(meta, "ReservationStatusUpdate", request)

    override fun securityEventNotification(
        meta: RequestMetadata,
        request: SecurityEventNotificationReq
    ): OperationExecution<SecurityEventNotificationReq, SecurityEventNotificationResp> =
        sendMessage(meta, "SecurityEventNotification", request)

    override fun signCertificate(
        meta: RequestMetadata,
        request: SignCertificateReq
    ): OperationExecution<SignCertificateReq, SignCertificateResp> =
        sendMessage(meta, "SignCertificate", request)

    override fun reportChargingProfiles(
            meta: RequestMetadata,
            request: ReportChargingProfilesReq
    ): OperationExecution<ReportChargingProfilesReq, ReportChargingProfilesResp> =
        sendMessage(meta,"ReportChargingProfiles",request)

    override fun connect() {
        client.connect()
    }

    override fun close() {
        client.close()
    }

}