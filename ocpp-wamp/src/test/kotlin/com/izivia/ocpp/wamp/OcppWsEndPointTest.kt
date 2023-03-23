package com.izivia.ocpp.wamp

import com.izivia.ocpp.wamp.server.impl.OcppWsEndpoint
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class OcppWsEndPointTest {

    val ocppWsEndpoint = OcppWsEndpoint("ws")

    @Test
    fun `should Parse with simple slash`() {
        val chargingStationOcppId =
            ocppWsEndpoint.extractChargingStationOcppId("""/ws/BOUY_0736""")
                ?.takeUnless { it.isEmpty() }
                ?: throw IllegalArgumentException("malformed request - no chargingStationOcppId ")

        expectThat(chargingStationOcppId).isEqualTo("BOUY_0736")
    }

    @Test
    fun `should Parse with double slash`() {
        val chargingStationOcppId =
            ocppWsEndpoint.extractChargingStationOcppId("""/ws//BOUY_0736""")
                ?.takeUnless { it.isEmpty() }
                ?: throw IllegalArgumentException("malformed request - no chargingStationOcppId ")

        expectThat(chargingStationOcppId).isEqualTo("BOUY_0736")
    }
}
