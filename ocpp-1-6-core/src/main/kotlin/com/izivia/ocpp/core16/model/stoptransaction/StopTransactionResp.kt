package com.izivia.ocpp.core16.model.stoptransaction

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.common.IdTagInfo

data class StopTransactionResp(
    val idTagInfo: IdTagInfo? = null
) : Response
