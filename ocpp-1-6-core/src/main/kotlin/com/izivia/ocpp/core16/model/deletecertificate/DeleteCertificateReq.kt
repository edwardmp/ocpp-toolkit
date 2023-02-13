package com.izivia.ocpp.core16.model.deletecertificate

import com.izivia.ocpp.core16.model.deletecertificate.enumeration.HashAlgorithmEnumType

data class DeleteCertificateReq(
    val certificateHashData: CertificateHashDataType
)

data class CertificateHashDataType(
    val hashAlgorithm: HashAlgorithmEnumType,
    val issuerNameHash: String,
    val issuerKeyHash: String
)
