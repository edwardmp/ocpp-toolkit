package com.izivia.ocpp.core20.model.get15118evcertificate

import com.izivia.ocpp.core20.model.common.StatusInfoType
import com.izivia.ocpp.core20.model.get15118evcertificate.enumeration.Iso15118EVCertificateStatusEnumType

data class Get15118EVCertificateResp(
    val status: Iso15118EVCertificateStatusEnumType,
    val exiRequest: String,
    val statusInfo: StatusInfoType
)
