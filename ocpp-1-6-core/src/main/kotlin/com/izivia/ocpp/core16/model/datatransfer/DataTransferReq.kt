package com.izivia.ocpp.core16.model.datatransfer

import com.izivia.ocpp.core16.model.Request

data class DataTransferReq(
    val vendorId: String,
    val messageId: String? = null,
    val data: String? = null
) : Request
