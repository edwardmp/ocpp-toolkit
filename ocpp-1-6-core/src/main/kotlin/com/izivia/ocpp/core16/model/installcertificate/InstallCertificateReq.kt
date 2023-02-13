package com.izivia.ocpp.core16.model.installcertificate

import com.izivia.ocpp.core16.model.getinstalledcertificateids.enumeration.CertificateUseEnumType

data class InstallCertificateReq(
    val certificateType: CertificateUseEnumType,
    val certificate: String
)
