package com.izivia.ocpp.utils.fault

import com.izivia.ocpp.utils.ErrorDetail

const val FAULT = "Fault"

data class Fault(
    val errorCode: String,
    val errorDescription: String,
    val errorDetails: List<ErrorDetail>
)
