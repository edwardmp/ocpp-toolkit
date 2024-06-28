package com.izivia.ocpp.core16.model.remotestop

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.common.enumeration.RemoteStartStopStatus

data class RemoteStopTransactionResp(
    val status: RemoteStartStopStatus
) : Response
