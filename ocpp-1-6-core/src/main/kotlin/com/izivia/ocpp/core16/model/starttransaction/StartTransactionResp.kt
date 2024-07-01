package com.izivia.ocpp.core16.model.starttransaction

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.common.IdTagInfo

data class StartTransactionResp(
    val idTagInfo: IdTagInfo,
    val transactionId: Int
) : Response
