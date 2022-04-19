package fr.simatix.cs.simulator.api.model.certificateSigned

import fr.simatix.cs.simulator.api.model.certificateSigned.enumeration.CertificateSigningUseEnumType

data class CertificateSignedReq(
    val certificateChain: String,
    val certificateType: CertificateSigningUseEnumType? = null,
)