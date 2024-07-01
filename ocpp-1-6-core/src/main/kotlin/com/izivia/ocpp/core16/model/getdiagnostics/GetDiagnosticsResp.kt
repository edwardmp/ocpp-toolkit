package com.izivia.ocpp.core16.model.getdiagnostics

import com.izivia.ocpp.core16.model.Response

data class GetDiagnosticsResp(
    val fileName: String? = null
) : Response
