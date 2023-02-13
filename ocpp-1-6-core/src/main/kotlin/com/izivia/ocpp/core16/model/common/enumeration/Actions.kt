package com.izivia.ocpp.core16.model.common.enumeration

import com.izivia.ocpp.core16.model.authorize.AuthorizeReq
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core16.model.certificatesigned.CertificateSignedReq
import com.izivia.ocpp.core16.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core16.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core16.model.clearcache.ClearCacheReq
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

enum class Actions(val value: String, val classRequest: Class<*>) {
    AUTHORIZE("authorize", AuthorizeReq::class.java),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java),
    CHANGECONFIGURATION("changeConfiguration", ChangeConfigurationReq::class.java),
    CLEARCACHE("clearCache", ClearCacheReq::class.java),
    CLEARCHARGINGPROFILE("clearCHargingProfile", ClearCacheReq::class.java),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java),
    DIAGNOSTICSSTATUSNOTIFICATION("diagnosticsStatusNotification", DiagnosticsStatusNotificationReq::class.java),
    FIRMWARESTATUSNOTIFICATION("firmwareStatusNotification", FirmwareStatusNotificationReq::class.java),
    GETCOMPOSITESCHEDULE("getCompositeSchedule", GetCompositeScheduleReq::class.java),
    GETCONFIGURATION("getConfiguration", GetConfigurationReq::class.java),
    GETDIAGNOSTICS("getDiagnostics", GetDiagnosticsReq::class.java),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java),
    METERVALUES("meterValues", MeterValuesReq::class.java),
    REMOTESTARTTRANSACTION("remoteStartTransaction", RemoteStartTransactionReq::class.java),
    REMOTESTOPTRANSACTION("remoteStopTransaction", RemoteStopTransactionReq::class.java),
    RESERVENOW("reserveNow", ReserveNowReq::class.java),
    RESET("reset", ResetReq::class.java),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java),
    SETCHARGINGPROFILE("setChargingProfile", SetChargingProfileReq::class.java),
    STARTTRANSACTION("startTransaction", StartTransactionReq::class.java),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java),
    STOPTRANSACTION("stopTransaction", StopTransactionReq::class.java),
    TRIGGERMESSAGE("triggerMessage", TriggerMessageReq::class.java),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java),
    UPDATEFIRMWARE("updateFirmware", SignedUpdateFirmwareReq::class.java),

    CERTIFICATESIGNED("certificateSigned", CertificateSignedReq::class.java),
    DELETECERTIFICATE("deleteCertificate", DeleteCertificateReq::class.java),
    EXTENDEDTRIGGERMESSAGE("extendedTriggerMessage", ExtendedTriggerMessageReq::class.java),
    GETINSTALLEDCERTIFICATEIDS("getInstalledCertificateIds", GetInstalledCertificateIdsReq::class.java),
    GETLOG("getLog", GetLogReq::class.java),
    INSTALLCERTIFICATE("installCertificate", InstallCertificateReq::class.java),
    LOGSTATUSNOTIFICATION("logStatusNotification", LogStatusNotificationReq::class.java),
    SECURITYEVENTNOTIFICATION("securityEventNotification", SecurityEventNotificationReq::class.java),
    SIGNCERTIFICATE("signCertificate", SignCertificateReq::class.java),
    SIGNEDFIRMWARESTATUSNOTIFICATION(
        "signedFirmwareStatusNotification",
        SignedFirmwareStatusNotificationReq::class.java
    ),
    SIGNEDUPDATEFIRMWARE("signedUpdateFirmware", SignedUpdateFirmwareReq::class.java);

    fun lowercase() = value.lowercase()

    fun camelCaseRequest() = "${value.replaceFirstChar { it.uppercase() }}Req"
}
