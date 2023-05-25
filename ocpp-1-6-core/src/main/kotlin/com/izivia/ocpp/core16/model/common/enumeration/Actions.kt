package com.izivia.ocpp.core16.model.common.enumeration

import com.izivia.ocpp.core16.model.authorize.AuthorizeReq
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core16.model.certificatesigned.CertificateSignedReq
import com.izivia.ocpp.core16.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core16.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core16.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core16.model.clearchargingprofile.ClearChargingProfileReq
import com.izivia.ocpp.core16.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core16.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.SignedFirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core16.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core16.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core16.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core16.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core16.model.getlog.GetLogReq
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core16.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core16.model.logstatusnotification.LogStatusNotificationReq
import com.izivia.ocpp.core16.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core16.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core16.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core16.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core16.model.reset.ResetReq
import com.izivia.ocpp.core16.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core16.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core16.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core16.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core16.model.triggermessage.ExtendedTriggerMessageReq
import com.izivia.ocpp.core16.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core16.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core16.model.updatefirmware.SignedUpdateFirmwareReq
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String, override val classRequest: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE("authorize", AuthorizeReq::class.java, OcppInitiator.CHARGING_STATION),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java, OcppInitiator.CHARGING_STATION),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CHANGECONFIGURATION("changeConfiguration", ChangeConfigurationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCHARGINGPROFILE(
        "clearChargingProfile", ClearChargingProfileReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, OcppInitiator.ALL),
    DIAGNOSTICSSTATUSNOTIFICATION(
        "diagnosticsStatusNotification",
        DiagnosticsStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCOMPOSITESCHEDULE(
        "getCompositeSchedule", GetCompositeScheduleReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETCONFIGURATION("getConfiguration", GetConfigurationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETDIAGNOSTICS("getDiagnostics", GetDiagnosticsReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java, OcppInitiator.CHARGING_STATION),
    METERVALUES("meterValues", MeterValuesReq::class.java, OcppInitiator.CHARGING_STATION),
    REMOTESTARTTRANSACTION(
        "remoteStartTransaction",
        RemoteStartTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REMOTESTOPTRANSACTION(
        "remoteStopTransaction", RemoteStopTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SETCHARGINGPROFILE("setChargingProfile", SetChargingProfileReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    STARTTRANSACTION("startTransaction", StartTransactionReq::class.java, OcppInitiator.CHARGING_STATION),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java, OcppInitiator.CHARGING_STATION),
    STOPTRANSACTION("stopTransaction", StopTransactionReq::class.java, OcppInitiator.CHARGING_STATION),
    TRIGGERMESSAGE("triggerMessage", TriggerMessageReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UPDATEFIRMWARE("updateFirmware", UpdateFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM),

    CERTIFICATESIGNED("certificateSigned", CertificateSignedReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    DELETECERTIFICATE("deleteCertificate", DeleteCertificateReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    EXTENDEDTRIGGERMESSAGE(
        "extendedTriggerMessage",
        ExtendedTriggerMessageReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETINSTALLEDCERTIFICATEIDS(
        "getInstalledCertificateIds",
        GetInstalledCertificateIdsReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOG("getLog", GetLogReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    INSTALLCERTIFICATE("installCertificate", InstallCertificateReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    LOGSTATUSNOTIFICATION(
        "logStatusNotification",
        LogStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SECURITYEVENTNOTIFICATION(
        "securityEventNotification",
        SecurityEventNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SIGNCERTIFICATE("signCertificate", SignCertificateReq::class.java, OcppInitiator.CHARGING_STATION),
    SIGNEDFIRMWARESTATUSNOTIFICATION(
        "signedFirmwareStatusNotification",
        SignedFirmwareStatusNotificationReq::class.java, OcppInitiator.CHARGING_STATION
    ),
    SIGNEDUPDATEFIRMWARE("signedUpdateFirmware", SignedUpdateFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM);

    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
