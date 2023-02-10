package com.izivia.ocpp.core15.model.common.enumeration

enum class Actions(val value: String) {
    AUTHORIZE("authorize"),
    BOOTNOTIFICATION("bootNotification"),
    CANCELRESERVATION("cancelReservation"),
    CHANGEAVAILABILITY("changeAvailability"),
    CHANGECONFIGURATION("changeConfiguration"),
    CLEARCACHE("clearCache"),
    DATATRANSFER("dataTransfer"),
    DIAGNOSTICSSTATUSNOTIFICATION("diagnosticsStatusNotification"),
    FIRMWARESTATUSNOTIFICATION("firmwareStatusNotification"),
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
    STARTTRANSACTION("startTransaction"),
    STATUSNOTIFICATION("statusNotification"),
    STOPTRANSACTION("stopTransaction"),
    UNLOCKCONNECTOR("unlockConnector"),
    UPDATEFIRMWARE("updateFirmware");

    fun lowercase() = value.lowercase()

    fun camelCaseRequest() = "${value.replaceFirstChar { it.uppercase() }}Req"
}
