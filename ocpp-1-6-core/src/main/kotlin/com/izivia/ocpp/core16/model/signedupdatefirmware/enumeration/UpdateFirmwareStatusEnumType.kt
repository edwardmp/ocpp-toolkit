package com.izivia.ocpp.core16.model.signedupdatefirmware.enumeration

enum class UpdateFirmwareStatusEnumType(val value: String) {
    Accepted("Accepted"),
    Rejected("Rejected"),
    AcceptedCanceled("AcceptedCanceled"),
    InvalidCertificate("InvalidCertificate"),
    RevokedCertificate("RevokedCertificate")
}
