package com.izivia.ocpp.core16.model.cancelreservation

import com.izivia.ocpp.core16.model.Request

data class CancelReservationReq(
    val reservationId: Int
) : Request
