package com.izivia.ocpp.core16.model.certificatesigned

import com.izivia.ocpp.core16.model.Request

data class CertificateSignedReq(
    val certificateChain: String
) : Request
