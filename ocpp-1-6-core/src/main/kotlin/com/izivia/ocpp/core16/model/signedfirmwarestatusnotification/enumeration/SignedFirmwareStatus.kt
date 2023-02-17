package com.izivia.ocpp.core16.model.firmwarestatusnotification.enumeration

enum class SignedFirmwareStatus(val value: String) {
    Downloaded("Downloaded"),
    DownloadFailed("DownloadFailed"),
    DownloadScheduled("DownloadScheduled"),
    DownloadPaused("DownloadPaused"),
    Downloading("Downloading"),
    Idle("Idle"),
    InstallationFailed("InstallationFailed"),
    Installing("Installing"),
    InstallRebooting("InstallRebooting"),
    InstallScheduled("InstallScheduled"),
    InstallVerificationFailed("InstallVerificationFailed"),
    InvalidSignature("InvalidSignature"),
    SignatureVerified("SignatureVerified"),
    Installed("Installed");
}
