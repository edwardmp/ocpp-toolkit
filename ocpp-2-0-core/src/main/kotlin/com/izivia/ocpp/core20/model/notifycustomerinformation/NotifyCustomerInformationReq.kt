package com.izivia.ocpp.core20.model.notifycustomerinformation

import com.izivia.ocpp.utils.HasActionTimestamp
import kotlinx.datetime.Instant

data class NotifyCustomerInformationReq(
    val data: String,
    val tbc: Boolean = false,
    val seqNo: Int,
    val generatedAt: Instant,
    val requestId: Int
) : HasActionTimestamp {
    override val timestamp: Instant
        get() = generatedAt
}