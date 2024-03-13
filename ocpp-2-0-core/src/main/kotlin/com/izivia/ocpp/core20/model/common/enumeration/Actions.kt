package com.izivia.ocpp.core20.model.common.enumeration

import com.izivia.ocpp.core20.model.authorize.AuthorizeReq
import com.izivia.ocpp.core20.model.authorize.AuthorizeResp
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core20.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core20.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core20.model.certificateSigned.CertificateSignedReq
import com.izivia.ocpp.core20.model.certificateSigned.CertificateSignedResp
import com.izivia.ocpp.core20.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core20.model.changeavailability.ChangeAvailabilityResp
import com.izivia.ocpp.core20.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core20.model.clearcache.ClearCacheResp
import com.izivia.ocpp.core20.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core20.model.clearchargingprofile.ClearChargingProfileResp
import com.izivia.ocpp.core20.model.cleardisplaymessage.ClearDisplayMessageReq
import com.izivia.ocpp.core20.model.cleardisplaymessage.ClearDisplayMessageResp
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitReq
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitResp
import com.izivia.ocpp.core20.model.clearvariablemonitoring.ClearVariableMonitoringReq
import com.izivia.ocpp.core20.model.clearvariablemonitoring.ClearVariableMonitoringResp
import com.izivia.ocpp.core20.model.costupdated.CostUpdatedReq
import com.izivia.ocpp.core20.model.costupdated.CostUpdatedResp
import com.izivia.ocpp.core20.model.customerinformation.CustomerInformationReq
import com.izivia.ocpp.core20.model.customerinformation.CustomerInformationResp
import com.izivia.ocpp.core20.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core20.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core20.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core20.model.deletecertificate.DeleteCertificateResp
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core20.model.get15118evcertificate.Get15118EVCertificateReq
import com.izivia.ocpp.core20.model.get15118evcertificate.Get15118EVCertificateResp
import com.izivia.ocpp.core20.model.getbasereport.GetBaseReportReq
import com.izivia.ocpp.core20.model.getbasereport.GetBaseReportResp
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusReq
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusResp
import com.izivia.ocpp.core20.model.getchargingprofiles.GetChargingProfilesReq
import com.izivia.ocpp.core20.model.getchargingprofiles.GetChargingProfilesResp
import com.izivia.ocpp.core20.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core20.model.getcompositeschedule.GetCompositeScheduleResp
import com.izivia.ocpp.core20.model.getdisplaymessages.GetDisplayMessagesReq
import com.izivia.ocpp.core20.model.getdisplaymessages.GetDisplayMessagesResp
import com.izivia.ocpp.core20.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core20.model.getinstalledcertificateids.GetInstalledCertificateIdsResp
import com.izivia.ocpp.core20.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core20.model.getlocallistversion.GetLocalListVersionResp
import com.izivia.ocpp.core20.model.getlog.GetLogReq
import com.izivia.ocpp.core20.model.getlog.GetLogResp
import com.izivia.ocpp.core20.model.getmonitoringreport.GetMonitoringReportReq
import com.izivia.ocpp.core20.model.getmonitoringreport.GetMonitoringReportResp
import com.izivia.ocpp.core20.model.getreport.GetReportReq
import com.izivia.ocpp.core20.model.getreport.GetReportResp
import com.izivia.ocpp.core20.model.gettransactionstatus.GetTransactionStatusReq
import com.izivia.ocpp.core20.model.gettransactionstatus.GetTransactionStatusResp
import com.izivia.ocpp.core20.model.getvariables.GetVariablesReq
import com.izivia.ocpp.core20.model.getvariables.GetVariablesResp
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core20.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core20.model.installcertificate.InstallCertificateResp
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
import com.izivia.ocpp.core20.model.publishfirmware.PublishFirmwareResp
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationResp
import com.izivia.ocpp.core20.model.remotestart.RequestStartTransactionReq
import com.izivia.ocpp.core20.model.remotestart.RequestStartTransactionResp
import com.izivia.ocpp.core20.model.remotestop.RequestStopTransactionReq
import com.izivia.ocpp.core20.model.remotestop.RequestStopTransactionResp
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesReq
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesResp
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateReq
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateResp
import com.izivia.ocpp.core20.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core20.model.reservenow.ReserveNowResp
import com.izivia.ocpp.core20.model.reset.ResetReq
import com.izivia.ocpp.core20.model.reset.ResetResp
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationResp
import com.izivia.ocpp.core20.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core20.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileResp
import com.izivia.ocpp.core20.model.setdisplaymessage.SetDisplayMessageReq
import com.izivia.ocpp.core20.model.setdisplaymessage.SetDisplayMessageResp
import com.izivia.ocpp.core20.model.setmonitoringbase.SetMonitoringBaseReq
import com.izivia.ocpp.core20.model.setmonitoringbase.SetMonitoringBaseResp
import com.izivia.ocpp.core20.model.setmonitoringlevel.SetMonitoringLevelReq
import com.izivia.ocpp.core20.model.setmonitoringlevel.SetMonitoringLevelResp
import com.izivia.ocpp.core20.model.setnetworkprofile.SetNetworkProfileReq
import com.izivia.ocpp.core20.model.setnetworkprofile.SetNetworkProfileResp
import com.izivia.ocpp.core20.model.setvariablemonitoring.SetVariableMonitoringReq
import com.izivia.ocpp.core20.model.setvariablemonitoring.SetVariableMonitoringResp
import com.izivia.ocpp.core20.model.setvariables.SetVariablesReq
import com.izivia.ocpp.core20.model.setvariables.SetVariablesResp
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateResp
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventReq
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventResp
import com.izivia.ocpp.core20.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core20.model.triggermessage.TriggerMessageResp
import com.izivia.ocpp.core20.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core20.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core20.model.unpublishfirmware.UnpublishFirmwareReq
import com.izivia.ocpp.core20.model.unpublishfirmware.UnpublishFirmwareResp
import com.izivia.ocpp.core20.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core20.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String,
    override val classRequest: Class<*>,
    override val classResponse: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE(
        "authorize", AuthorizeReq::class.java, AuthorizeResp::class.java, OcppInitiator.CHARGING_STATION
    ),
    BOOTNOTIFICATION(
        "bootNotification",
        BootNotificationReq::class.java,
        BootNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    CANCELRESERVATION(
        "cancelReservation",
        CancelReservationReq::class.java,
        CancelReservationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CERTIFICATESIGNED(
        "certificateSigned",
        CertificateSignedReq::class.java,
        CertificateSignedResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CHANGEAVAILABILITY(
        "changeAvailability",
        ChangeAvailabilityReq::class.java,
        ChangeAvailabilityResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, ClearCacheResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCHARGINGPROFILE(
        "clearChargingProfile",
        ClearChargingProfileReq::class.java,
        ClearChargingProfileResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CLEARDISPLAYMESSAGE(
        "clearDisplayMessage",
        ClearDisplayMessageReq::class.java,
        ClearDisplayMessageResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CLEAREDCHARGINGLIMIT(
        "clearedChargingLimit",
        ClearedChargingLimitReq::class.java,
        ClearedChargingLimitResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    CLEARVARIABLEMONITORING(
        "clearVariableMonitoring",
        ClearVariableMonitoringReq::class.java,
        ClearVariableMonitoringResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    COSTUPDATED("costUpdated", CostUpdatedReq::class.java, CostUpdatedResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CUSTOMERINFORMATION(
        "customerInformation",
        CustomerInformationReq::class.java,
        CustomerInformationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, DataTransferResp::class.java, OcppInitiator.ALL),
    DELETECERTIFICATE(
        "deleteCertificate",
        DeleteCertificateReq::class.java,
        DeleteCertificateResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        FirmwareStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GET15118EVCERTIFICATE(
        "get15118EVCertificate",
        Get15118EVCertificateReq::class.java,
        Get15118EVCertificateResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETBASEREPORT(
        "getBaseReport",
        GetBaseReportReq::class.java,
        GetBaseReportResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETCERTIFICATESTATUS(
        "getCertificateStatus",
        GetCertificateStatusReq::class.java,
        GetCertificateStatusResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCHARGINGPROFILES(
        "getChargingProfiles",
        GetChargingProfilesReq::class.java,
        GetChargingProfilesResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETCOMPOSITESCHEDULE(
        "getCompositeSchedule",
        GetCompositeScheduleReq::class.java,
        GetCompositeScheduleResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETDISPLAYMESSAGES(
        "getDisplayMessages",
        GetDisplayMessagesReq::class.java,
        GetDisplayMessagesResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETINSTALLEDCERTIFICATEIDS(
        "getInstalledCertificateIds",
        GetInstalledCertificateIdsReq::class.java,
        GetInstalledCertificateIdsResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOCALLISTVERSION(
        "getLocalListVersion",
        GetLocalListVersionReq::class.java,
        GetLocalListVersionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOG("getLog", GetLogReq::class.java, GetLogResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETMONITORINGREPORT(
        "getMonitoringReport",
        GetMonitoringReportReq::class.java,
        GetMonitoringReportResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETREPORT("getReport", GetReportReq::class.java, GetReportResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETTRANSACTIONSTATUS(
        "getTransactionStatus",
        GetTransactionStatusReq::class.java,
        GetTransactionStatusResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETVARIABLES(
        "getVariables",
        GetVariablesReq::class.java,
        GetVariablesResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java, HeartbeatResp::class.java, OcppInitiator.CHARGING_STATION),
    INSTALLCERTIFICATE(
        "installCertificate",
        InstallCertificateReq::class.java,
        InstallCertificateResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    LOGSTATUSNOTIFICATION(
        "logStatusNotification",
        LogStatusNotificationReq::class.java,
        LogStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    METERVALUES("meterValues", MeterValuesReq::class.java, MeterValuesResp::class.java, OcppInitiator.CHARGING_STATION),
    NOTIFYCHARGINGLIMIT(
        "notifyChargingLimit",
        NotifyChargingLimitReq::class.java,
        NotifyChargingLimitResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYCUSTOMERINFORMATION(
        "notifyCustomerInformation",
        NotifyCustomerInformationReq::class.java,
        NotifyCustomerInformationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYDISPLAYMESSAGES(
        "notifyDisplayMessages",
        NotifyDisplayMessagesReq::class.java,
        NotifyDisplayMessagesResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVCHARGINGNEEDS(
        "notifyEVChargingNeeds",
        NotifyEVChargingNeedsReq::class.java,
        NotifyEVChargingNeedsResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVCHARGINGSCHEDULE(
        "notifyEVChargingSchedule",
        NotifyEVChargingScheduleReq::class.java,
        NotifyEVChargingScheduleResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVENT("notifyEvent", NotifyEventReq::class.java, NotifyEventResp::class.java, OcppInitiator.CHARGING_STATION),
    NOTIFYMONITORINGREPORT(
        "notifyMonitoringReport",
        NotifyMonitoringReportReq::class.java,
        NotifyMonitoringReportResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYREPORT(
        "notifyReport",
        NotifyReportReq::class.java,
        NotifyReportResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    PUBLISHFIRMWARE(
        "publishFirmware",
        PublishFirmwareReq::class.java,
        PublishFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    PUBLISHFIRMWARESTATUSNOTIFICATION(
        "publishFirmwareStatusNotification",
        PublishFirmwareStatusNotificationReq::class.java,
        PublishFirmwareStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    REPORTCHARGINGPROFILES(
        "reportChargingProfiles",
        ReportChargingProfilesReq::class.java,
        ReportChargingProfilesResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REQUESTSTARTTRANSACTION(
        "requestStartTransaction",
        RequestStartTransactionReq::class.java,
        RequestStartTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REQUESTSTOPTRANSACTION(
        "requestStopTransaction",
        RequestStopTransactionReq::class.java,
        RequestStopTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    RESERVATIONSTATUSUPDATE(
        "reservationStatusUpdate",
        ReservationStatusUpdateReq::class.java,
        ReservationStatusUpdateResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, ReserveNowResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, ResetResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SECURITYEVENTNOTIFICATION(
        "securityEventNotification",
        SecurityEventNotificationReq::class.java,
        SecurityEventNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SENDLOCALLIST(
        "sendLocalList",
        SendLocalListReq::class.java,
        SendLocalListResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETCHARGINGPROFILE(
        "setChargingProfile",
        SetChargingProfileReq::class.java,
        SetChargingProfileResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETDISPLAYMESSAGE(
        "setDisplayMessage",
        SetDisplayMessageReq::class.java,
        SetDisplayMessageResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETMONITORINGBASE(
        "setMonitoringBase",
        SetMonitoringBaseReq::class.java,
        SetMonitoringBaseResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETMONITORINGLEVEL(
        "setMonitoringLevel",
        SetMonitoringLevelReq::class.java,
        SetMonitoringLevelResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETNETWORKPROFILE(
        "setNetworkProfile",
        SetNetworkProfileReq::class.java,
        SetNetworkProfileResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETVARIABLEMONITORING(
        "setVariableMonitoring",
        SetVariableMonitoringReq::class.java,
        SetVariableMonitoringResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETVARIABLES(
        "setVariables",
        SetVariablesReq::class.java,
        SetVariablesResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SIGNCERTIFICATE(
        "signCertificate",
        SignCertificateReq::class.java,
        SignCertificateResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    STATUSNOTIFICATION(
        "statusNotification",
        StatusNotificationReq::class.java,
        StatusNotificationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    TRANSACTIONEVENT(
        "transactionEvent",
        TransactionEventReq::class.java,
        TransactionEventResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    TRIGGERMESSAGE(
        "triggerMessage",
        TriggerMessageReq::class.java,
        TriggerMessageResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    UNLOCKCONNECTOR(
        "unlockConnector",
        UnlockConnectorReq::class.java,
        UnlockConnectorResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    UNPUBLISHFIRMWARE(
        "unpublishFirmware",
        UnpublishFirmwareReq::class.java,
        UnpublishFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    UPDATEFIRMWARE(
        "updateFirmware",
        UpdateFirmwareReq::class.java,
        UpdateFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    );


    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
