package com.izivia.ocpp.core16.model.authorize

import com.izivia.ocpp.core16.model.Request

data class AuthorizeReq(
    val idTag: String
) : Request
