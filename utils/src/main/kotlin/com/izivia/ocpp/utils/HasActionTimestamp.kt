package com.izivia.ocpp.utils

import kotlinx.datetime.Instant

interface HasActionTimestamp {

    val timestamp: Instant?

}