package com.izivia.ocpp.adapter20.test

import com.izivia.ocpp.adapter20.Ocpp20CSApiAdapter
import com.izivia.ocpp.api.CSApi
import com.izivia.ocpp.api.model.common.enumeration.ChargingProfilePurposeEnumType as ChargingProfilePurposeEnumTypeGen
import com.izivia.ocpp.api.model.setchargingprofile.SetChargingProfileResp
import com.izivia.ocpp.api.model.setchargingprofile.enumeration.ChargingProfileStatusEnumType as ChargingProfileStatusEnumTypeGen
import com.izivia.ocpp.core20.model.common.ChargingProfileType
import com.izivia.ocpp.core20.model.common.ChargingSchedulePeriodType
import com.izivia.ocpp.core20.model.common.ChargingScheduleType
import com.izivia.ocpp.core20.model.common.enumeration.ChargingProfilePurposeEnumType
import com.izivia.ocpp.core20.model.common.enumeration.ChargingRateUnitEnumType
import com.izivia.ocpp.core20.model.remotestart.enumeration.ChargingProfileKindEnumType
import com.izivia.ocpp.core20.model.setchargingprofile.SetChargingProfileReq
import com.izivia.ocpp.core20.model.setchargingprofile.enumeration.ChargingProfileStatusEnumType
import com.izivia.ocpp.operation.information.ExecutionMetadata
import com.izivia.ocpp.operation.information.OperationExecution
import com.izivia.ocpp.operation.information.RequestMetadata
import com.izivia.ocpp.operation.information.RequestStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class CSApiAdapterTest {
    private lateinit var csApi: CSApi

    @BeforeEach
    fun init() {
        csApi = mockk()
    }

    @AfterEach
    fun destroy() {
        unmockkAll()
    }

    @Test
    fun `setChargingProfile request`() {
        val requestMetadata = RequestMetadata("")

        every { csApi.setChargingProfile(any(), any()) } answers {
            val req = secondArg<com.izivia.ocpp.api.model.setchargingprofile.SetChargingProfileReq>()
            OperationExecution(
                executionMeta = ExecutionMetadata(requestMetadata, RequestStatus.SUCCESS),
                req,
                SetChargingProfileResp(
                    status = when {
                        req.chargingProfile.chargingProfilePurpose == ChargingProfilePurposeEnumTypeGen.ChargingStationMaxProfile
                                && req.chargingProfile.transactionId == null -> ChargingProfileStatusEnumTypeGen.Accepted

                        req.chargingProfile.chargingProfilePurpose == ChargingProfilePurposeEnumTypeGen.TxProfile
                                && req.chargingProfile.transactionId == "1" -> ChargingProfileStatusEnumTypeGen.Accepted

                        else -> ChargingProfileStatusEnumTypeGen.Rejected
                    }
                )
            )
        }

        val operations = Ocpp20CSApiAdapter(csApi)
        val request = SetChargingProfileReq(
            evseId = 1,
            chargingProfile = ChargingProfileType(
                id = 1,
                stackLevel = 2,
                chargingProfilePurpose = ChargingProfilePurposeEnumType.ChargingStationMaxProfile,
                chargingProfileKind = ChargingProfileKindEnumType.Relative,
                chargingSchedule = listOf(
                    ChargingScheduleType(
                        1, ChargingRateUnitEnumType.A, listOf(ChargingSchedulePeriodType(1, 1.0))
                    )
                )
            )
        )
        val response = operations.setChargingProfile(requestMetadata, request)

        expectThat(response) {
            get { this.request }.isEqualTo(request)
            get { this.response.status }.isEqualTo(ChargingProfileStatusEnumType.Accepted)
        }
    }
}
