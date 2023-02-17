package com.izivia.ocpp.core16.model.logstatusnotification.enumeration

enum class UpdateLogStatusEnumType(val value: String) {
    BadMessage("BadMessage"),
    Idle("Idle"),
    NotSupportedOperation("NotSupportedOperation"),
    PermissionDenied("PermissionDenied"),
    Uploaded("Uploaded"),
    UploadFailure("UploadFailure"),
    Uploading("Uploading")
}
