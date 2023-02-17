package com.izivia.ocpp.core20.model.get15118evcertificate

import com.izivia.ocpp.core20.model.get15118evcertificate.enumeration.CertificateActionEnumType

data class Get15118EVCertificateReq(
    val iso15118SchemaVersion: String,
    val action: CertificateActionEnumType,
    val exiRequest: String
)
