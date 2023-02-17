package com.izivia.ocpp.core16.model.triggermessage.enumeration

enum class MessageTriggerEnumType(val value: String) {
    BootNotification("BootNotification"),
    LogStatusNotification("LogStatusNotification"),

    FirmwareStatusNotification("FirmwareStatusNotification"),

    Heartbeat("Heartbeat"),

    MeterValues("MeterValues"),
    SignedChargePointCertificate("SignedChargePointCertificate"),

    StatusNotification("StatusNotification");
}
