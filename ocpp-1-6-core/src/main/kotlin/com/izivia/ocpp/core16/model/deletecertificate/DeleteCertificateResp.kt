package com.izivia.ocpp.core16.model.certificatesigned

import com.izivia.ocpp.core16.model.certificatesigned.enumeration.DeleteCertificateStatusEnumType

data class DeleteCertificateResp(
    val status: DeleteCertificateStatusEnumType
)
