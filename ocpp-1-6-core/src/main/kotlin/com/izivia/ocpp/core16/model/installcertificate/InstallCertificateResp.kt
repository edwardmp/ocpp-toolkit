package com.izivia.ocpp.core16.model.installcertificate

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.installcertificate.enumeration.CertificateStatusEnumType

data class InstallCertificateResp(
    val status: CertificateStatusEnumType
) : Response
