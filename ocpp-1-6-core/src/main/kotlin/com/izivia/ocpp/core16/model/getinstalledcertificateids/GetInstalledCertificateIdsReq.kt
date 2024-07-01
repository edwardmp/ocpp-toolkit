package com.izivia.ocpp.core16.model.getinstalledcertificateids

import com.izivia.ocpp.core16.model.Request
import com.izivia.ocpp.core16.model.getinstalledcertificateids.enumeration.CertificateUseEnumType

data class GetInstalledCertificateIdsReq(
    val certificateType: CertificateUseEnumType
) : Request
