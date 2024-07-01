package com.izivia.ocpp.core16.model.common.enumeration

import com.izivia.ocpp.core16.model.authorize.AuthorizeReq
import com.izivia.ocpp.core16.model.authorize.AuthorizeResp
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core16.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core16.model.certificatesigned.CertificateSignedReq
import com.izivia.ocpp.core16.model.certificatesigned.CertificateSignedResp
import com.izivia.ocpp.core16.model.deletecertificate.DeleteCertificateResp
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
import com.izivia.ocpp.core16.model.deletecertificate.DeleteCertificateReq
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationResp
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core16.model.signedfirmwarestatusnotification.SignedFirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.signedfirmwarestatusnotification.SignedFirmwareStatusNotificationResp
import com.izivia.ocpp.core16.model.getcompositeschedule.GetCompositeScheduleReq
import com.izivia.ocpp.core16.model.getcompositeschedule.GetCompositeScheduleResp
import com.izivia.ocpp.core16.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core16.model.getconfiguration.GetConfigurationResp
import com.izivia.ocpp.core16.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core16.model.getdiagnostics.GetDiagnosticsResp
import com.izivia.ocpp.core16.model.getinstalledcertificateids.GetInstalledCertificateIdsReq
import com.izivia.ocpp.core16.model.getinstalledcertificateids.GetInstalledCertificateIdsResp
import com.izivia.ocpp.core16.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core16.model.getlocallistversion.GetLocalListVersionResp
import com.izivia.ocpp.core16.model.getlog.GetLogReq
import com.izivia.ocpp.core16.model.getlog.GetLogResp
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core16.model.installcertificate.InstallCertificateReq
import com.izivia.ocpp.core16.model.installcertificate.InstallCertificateResp
import com.izivia.ocpp.core16.model.logstatusnotification.LogStatusNotificationReq
import com.izivia.ocpp.core16.model.logstatusnotification.LogStatusNotificationResp
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
import com.izivia.ocpp.core16.model.securityeventnotification.SecurityEventNotificationReq
import com.izivia.ocpp.core16.model.securityeventnotification.SecurityEventNotificationResp
import com.izivia.ocpp.core16.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core16.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core16.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core16.model.setchargingprofile.SetChargingProfileResp
import com.izivia.ocpp.core16.model.signcertificate.SignCertificateReq
import com.izivia.ocpp.core16.model.signcertificate.SignCertificateResp
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.core16.model.extendedtriggermessage.ExtendedTriggerMessageReq
import com.izivia.ocpp.core16.model.extendedtriggermessage.ExtendedTriggerMessageResp
import com.izivia.ocpp.core16.model.triggermessage.TriggerMessageReq
import com.izivia.ocpp.core16.model.triggermessage.TriggerMessageResp
import com.izivia.ocpp.core16.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core16.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core16.model.signedupdatefirmware.SignedUpdateFirmwareReq
import com.izivia.ocpp.core16.model.signedupdatefirmware.SignedUpdateFirmwareResp
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core16.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String,
    override val classRequest: Class<*>,
    override val classResponse: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE("authorize", AuthorizeReq::class.java, AuthorizeResp::class.java, OcppInitiator.CHARGING_STATION),
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
    CHANGEAVAILABILITY(
        "changeAvailability",
        ChangeAvailabilityReq::class.java,
        ChangeAvailabilityResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CHANGECONFIGURATION(
        "changeConfiguration",
        ChangeConfigurationReq::class.java,
        ChangeConfigurationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, ClearCacheResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCHARGINGPROFILE(
        "clearChargingProfile",
        ClearChargingProfileReq::class.java,
        ClearChargingProfileResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, DataTransferResp::class.java, OcppInitiator.ALL),
    DIAGNOSTICSSTATUSNOTIFICATION(
        "diagnosticsStatusNotification",
        DiagnosticsStatusNotificationReq::class.java,
        DiagnosticsStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        FirmwareStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCOMPOSITESCHEDULE(
        "getCompositeSchedule",
        GetCompositeScheduleReq::class.java,
        GetCompositeScheduleResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETCONFIGURATION(
        "getConfiguration",
        GetConfigurationReq::class.java,
        GetConfigurationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETDIAGNOSTICS(
        "getDiagnostics",
        GetDiagnosticsReq::class.java,
        GetDiagnosticsResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOCALLISTVERSION(
        "getLocalListVersion",
        GetLocalListVersionReq::class.java,
        GetLocalListVersionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java, HeartbeatResp::class.java, OcppInitiator.CHARGING_STATION),
    METERVALUES(
        "meterValues",
        MeterValuesReq::class.java,
        MeterValuesResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    REMOTESTARTTRANSACTION(
        "remoteStartTransaction",
        RemoteStartTransactionReq::class.java,
        RemoteStartTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REMOTESTOPTRANSACTION(
        "remoteStopTransaction",
        RemoteStopTransactionReq::class.java,
        RemoteStopTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, ReserveNowResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, ResetResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
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
    STARTTRANSACTION(
        "startTransaction",
        StartTransactionReq::class.java,
        StartTransactionResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    STATUSNOTIFICATION(
        "statusNotification",
        StatusNotificationReq::class.java,
        StatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    STOPTRANSACTION(
        "stopTransaction",
        StopTransactionReq::class.java,
        StopTransactionResp::class.java,
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
    UPDATEFIRMWARE(
        "updateFirmware",
        UpdateFirmwareReq::class.java,
        UpdateFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CERTIFICATESIGNED(
        "certificateSigned",
        CertificateSignedReq::class.java,
        CertificateSignedResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    DELETECERTIFICATE(
        "deleteCertificate",
        DeleteCertificateReq::class.java,
        DeleteCertificateResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    EXTENDEDTRIGGERMESSAGE(
        "extendedTriggerMessage",
        ExtendedTriggerMessageReq::class.java,
        ExtendedTriggerMessageResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETINSTALLEDCERTIFICATEIDS(
        "getInstalledCertificateIds",
        GetInstalledCertificateIdsReq::class.java,
        GetInstalledCertificateIdsResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOG("getLog", GetLogReq::class.java, GetLogResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
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
    SECURITYEVENTNOTIFICATION(
        "securityEventNotification",
        SecurityEventNotificationReq::class.java,
        SecurityEventNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SIGNCERTIFICATE(
        "signCertificate",
        SignCertificateReq::class.java,
        SignCertificateResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SIGNEDFIRMWARESTATUSNOTIFICATION(
        "signedFirmwareStatusNotification",
        SignedFirmwareStatusNotificationReq::class.java,
        SignedFirmwareStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    SIGNEDUPDATEFIRMWARE(
        "signedUpdateFirmware",
        SignedUpdateFirmwareReq::class.java,
        SignedUpdateFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    );


    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
