package com.izivia.ocpp.core16.model.remotestop

import com.izivia.ocpp.core16.model.Request

data class RemoteStopTransactionReq(
    val transactionId: Int
) : Request
