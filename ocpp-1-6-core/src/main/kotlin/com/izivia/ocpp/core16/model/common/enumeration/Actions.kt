package com.izivia.ocpp.core16.model.common.enumeration

enum class Actions(val value: String) {
    AUTHORIZE("authorize"),
    BOOTNOTIFICATION("bootNotification"),
    CANCELRESERVATION("cancelReservation"),
    CHANGEAVAILABILITY("changeAvailability"),
    CHANGECONFIGURATION("changeConfiguration"),
    CLEARCACHE("clearCache"),
    CLEARCHARGINGPROFILE("clearCHargingProfile"),
    DATATRANSFER("dataTransfer"),
    DIAGNOSTICSSTATUSNOTIFICATION("diagnosticsStatusNotification"),
    FIRMWARESTATUSNOTIFICATION("firmwareStatusNotification"),
    GETCOMPOSITESCHEDULE("getCompositeSchedule"),
    GETCONFIGURATION("getConfiguration"),
    GETDIAGNOSTICS("getDiagnostics"),
    GETLOCALLISTVERSION("getLocalListVersion"),
    HEARTBEAT("heartbeat"),
    METERVALUES("meterValues"),
    REMOTESTARTTRANSACTION("remoteStartTransaction"),
    REMOTESTOPTRANSACTION("remoteStopTransaction"),
    RESERVENOW("reserveNow"),
    RESET("reset"),
    SENDLOCALLIST("sendLocalList"),
    SETCHARGINGPROFILE("setChargingProfile"),
    STARTTRANSACTION("startTransaction"),
    STATUSNOTIFICATION("statusNotification"),
    STOPTRANSACTION("stopTransaction"),
    TRIGGERMESSAGE("triggerMessage"),
    UNLOCKCONNECTOR("unlockConnector"),
    UPDATEFIRMWARE("updateFirmware"),

    CERTIFICATESIGNED("certificateSigned"),
    DELETECERTIFICATE("deleteCertificate"),
    EXTENDEDTRIGGERMESSAGE("extendedTriggerMessage"),
    GETINSTALLEDCERTIFICATEIDS("getInstalledCertificateIds"),
    GETLOG("getLog"),
    INSTALLCERTIFICATE("installCertificate"),
    LOGSTATUSNOTIFICATION("logStatusNotification"),
    SECURITYEVENTNOTIFICATION("securityEventNotification"),
    SIGNCERTIFICATE("signCertificate"),
    SIGNEDFIRMWARESTATUSNOTIFICATION("signedFirmwareStatusNotification"),
    SIGNEDUPDATEFIRMWARE("signedUpdateFirmware");

    fun lowercase() = value.lowercase()

    fun camelCaseRequest() = "${value.replaceFirstChar { it.uppercase() }}Req"
}
