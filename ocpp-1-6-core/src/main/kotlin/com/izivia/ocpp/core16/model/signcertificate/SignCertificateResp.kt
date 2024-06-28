package com.izivia.ocpp.core16.model.signcertificate

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.common.enumeration.GenericStatusEnumType

data class SignCertificateResp(
    val status: GenericStatusEnumType
) : Response
