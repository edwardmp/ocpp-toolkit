package com.izivia.ocpp.core16.model.signcertificate

import com.izivia.ocpp.core16.model.Request

data class SignCertificateReq(
    val csr: String
) : Request
