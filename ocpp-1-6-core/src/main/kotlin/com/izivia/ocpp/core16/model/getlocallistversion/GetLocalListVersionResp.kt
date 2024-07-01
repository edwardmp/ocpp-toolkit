package com.izivia.ocpp.core16.model.getlocallistversion

import com.izivia.ocpp.core16.model.Response

data class GetLocalListVersionResp(
    val listVersion: Int
) : Response
