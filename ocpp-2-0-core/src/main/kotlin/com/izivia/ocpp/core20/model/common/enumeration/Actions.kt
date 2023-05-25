package com.izivia.ocpp.core20.model.common.enumeration

import com.izivia.ocpp.core20.model.authorize.AuthorizeReq
import com.izivia.ocpp.core20.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core20.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core20.model.certificateSigned.CertificateSignedReq
import com.izivia.ocpp.core20.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core20.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core20.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core20.model.cleardisplaymessage.ClearDisplayMessageReq
import com.izivia.ocpp.core20.model.clearedcharginglimit.ClearedChargingLimitReq
import com.izivia.ocpp.core20.model.clearvariablemonitoring.ClearVariableMonitoringReq
import com.izivia.ocpp.core20.model.costupdated.CostUpdatedReq
import com.izivia.ocpp.core20.model.customerinformation.CustomerInformationReq
import com.izivia.ocpp.core20.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core20.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core20.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.get15118evcertificate.Get15118EVCertificateReq
import com.izivia.ocpp.core20.model.getbasereport.GetBaseReportReq
import com.izivia.ocpp.core20.model.getcertificatestatus.GetCertificateStatusReq
import com.izivia.ocpp.core20.model.getchargingprofiles.GetChargingProfilesReq
import com.izivia.ocpp.core20.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core20.model.getdisplaymessages.GetDisplayMessagesReq
import com.izivia.ocpp.core20.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core20.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core20.model.getlog.GetLogReq
import com.izivia.ocpp.core20.model.getmonitoringreport.GetMonitoringReportReq
import com.izivia.ocpp.core20.model.getreport.GetReportReq
import com.izivia.ocpp.core20.model.gettransactionstatus.GetTransactionStatusReq
import com.izivia.ocpp.core20.model.getvariables.GetVariablesReq
import com.izivia.ocpp.core20.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core20.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core20.model.logstatusnotification.LogStatusNotificationReq
import com.izivia.ocpp.core20.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core20.model.notifycharginglimit.NotifyChargingLimitReq
import com.izivia.ocpp.core20.model.notifycustomerinformation.NotifyCustomerInformationReq
import com.izivia.ocpp.core20.model.notifydisplaymessages.NotifyDisplayMessagesReq
import com.izivia.ocpp.core20.model.notifyevchargingneeds.NotifyEVChargingNeedsReq
import com.izivia.ocpp.core20.model.notifyevchargingschedule.NotifyEVChargingScheduleReq
import com.izivia.ocpp.core20.model.notifyevent.NotifyEventReq
import com.izivia.ocpp.core20.model.notifymonitoringreport.NotifyMonitoringReportReq
import com.izivia.ocpp.core20.model.notifyreport.NotifyReportReq
import com.izivia.ocpp.core20.model.publishfirmware.PublishFirmwareReq
import com.izivia.ocpp.core20.model.publishfirmwarestatusnotification.PublishFirmwareStatusNotificationReq
import com.izivia.ocpp.core20.model.remotestart.RequestStartTransactionReq
import com.izivia.ocpp.core20.model.remotestop.RequestStopTransactionReq
import com.izivia.ocpp.core20.model.reportchargingprofiles.ReportChargingProfilesReq
import com.izivia.ocpp.core20.model.reservationstatusupdate.ReservationStatusUpdateReq
import com.izivia.ocpp.core20.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core20.model.reset.ResetReq
import com.izivia.ocpp.core20.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core20.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core20.model.setdisplaymessage.SetDisplayMessageReq
import com.izivia.ocpp.core20.model.setmonitoringbase.SetMonitoringBaseReq
import com.izivia.ocpp.core20.model.setmonitoringlevel.SetMonitoringLevelReq
import com.izivia.ocpp.core20.model.setnetworkprofile.SetNetworkProfileReq
import com.izivia.ocpp.core20.model.setvariablemonitoring.SetVariableMonitoringReq
import com.izivia.ocpp.core20.model.setvariables.SetVariablesReq
import com.izivia.ocpp.core20.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core20.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core20.model.transactionevent.TransactionEventReq
import com.izivia.ocpp.core20.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core20.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core20.model.unpublishfirmware.UnpublishFirmwareReq
import com.izivia.ocpp.core20.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String, override val classRequest: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE("authorize", AuthorizeReq::class.java, OcppInitiator.CHARGING_STATION),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java, OcppInitiator.CHARGING_STATION),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CERTIFICATESIGNED("certificateSigned", CertificateSignedReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCHARGINGPROFILE("clearChargingProfile", ClearChargingProfileReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARDISPLAYMESSAGE("clearDisplayMessage", ClearDisplayMessageReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEAREDCHARGINGLIMIT("clearedChargingLimit", ClearedChargingLimitReq::class.java, OcppInitiator.CHARGING_STATION),
    CLEARVARIABLEMONITORING(
        "clearVariableMonitoring",
        ClearVariableMonitoringReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    COSTUPDATED("costUpdated", CostUpdatedReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CUSTOMERINFORMATION("customerInformation", CustomerInformationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, OcppInitiator.ALL),
    DELETECERTIFICATE("deleteCertificate", DeleteCertificateReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GET15118EVCERTIFICATE(
        "get15118EVCertificate",
        Get15118EVCertificateReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETBASEREPORT("getBaseReport", GetBaseReportReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETCERTIFICATESTATUS(
        "getCertificateStatus", GetCertificateStatusReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCHARGINGPROFILES("getChargingProfiles", GetChargingProfilesReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETCOMPOSITESCHEDULE(
        "getCompositeSchedule", GetCompositeScheduleReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETDISPLAYMESSAGES("getDisplayMessages", GetDisplayMessagesReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETINSTALLEDCERTIFICATEIDS(
        "getInstalledCertificateIds",
        GetInstalledCertificateIdsReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETLOG("getLog", GetLogReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETMONITORINGREPORT("getMonitoringReport", GetMonitoringReportReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETREPORT("getReport", GetReportReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETTRANSACTIONSTATUS(
        "getTransactionStatus", GetTransactionStatusReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETVARIABLES("getVariables", GetVariablesReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java, OcppInitiator.CHARGING_STATION),
    INSTALLCERTIFICATE("installCertificate", InstallCertificateReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    LOGSTATUSNOTIFICATION(
        "logStatusNotification",
        LogStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    METERVALUES("meterValues", MeterValuesReq::class.java, OcppInitiator.CHARGING_STATION),
    NOTIFYCHARGINGLIMIT(
        "notifyChargingLimit", NotifyChargingLimitReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYCUSTOMERINFORMATION(
        "notifyCustomerInformation",
        NotifyCustomerInformationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYDISPLAYMESSAGES(
        "notifyDisplayMessages",
        NotifyDisplayMessagesReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVCHARGINGNEEDS(
        "notifyEVChargingNeeds",
        NotifyEVChargingNeedsReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVCHARGINGSCHEDULE(
        "notifyEVChargingSchedule",
        NotifyEVChargingScheduleReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYEVENT("notifyEvent", NotifyEventReq::class.java, OcppInitiator.CHARGING_STATION),
    NOTIFYMONITORINGREPORT(
        "notifyMonitoringReport",
        NotifyMonitoringReportReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    NOTIFYREPORT("notifyReport", NotifyReportReq::class.java, OcppInitiator.CHARGING_STATION),
    PUBLISHFIRMWARE("publishFirmware", PublishFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    PUBLISHFIRMWARESTATUSNOTIFICATION(
        "publishFirmwareStatusNotification",
        PublishFirmwareStatusNotificationReq::class.java, OcppInitiator.CHARGING_STATION
    ),
    REPORTCHARGINGPROFILES(
        "reportChargingProfiles",
        ReportChargingProfilesReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REQUESTSTARTTRANSACTION(
        "requestStartTransaction",
        RequestStartTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REQUESTSTOPTRANSACTION(
        "requestStopTransaction",
        RequestStopTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    RESERVATIONSTATUSUPDATE(
        "reservationStatusUpdate",
        ReservationStatusUpdateReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SECURITYEVENTNOTIFICATION(
        "securityEventNotification",
        SecurityEventNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETCHARGINGPROFILE("setChargingProfile", SetChargingProfileReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETDISPLAYMESSAGE("setDisplayMessage", SetDisplayMessageReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETMONITORINGBASE("setMonitoringBase", SetMonitoringBaseReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETMONITORINGLEVEL("setMonitoringLevel", SetMonitoringLevelReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETNETWORKPROFILE("setNetworkProfile", SetNetworkProfileReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETVARIABLEMONITORING(
        "setVariableMonitoring", SetVariableMonitoringReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    SETVARIABLES("setVariables", SetVariablesReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SIGNCERTIFICATE("signCertificate", SignCertificateReq::class.java, OcppInitiator.CHARGING_STATION),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    TRANSACTIONEVENT("transactionEvent", TransactionEventReq::class.java, OcppInitiator.CHARGING_STATION),
    TRIGGERMESSAGE("triggerMessage", TriggerMessageReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UNPUBLISHFIRMWARE("unpublishFirmware", UnpublishFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UPDATEFIRMWARE("updateFirmware", UpdateFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM);

    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
