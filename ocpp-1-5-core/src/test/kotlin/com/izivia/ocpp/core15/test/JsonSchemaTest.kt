package com.izivia.ocpp.core.test

import com.izivia.ocpp.core15.model.authorize.AuthorizeReq
import com.izivia.ocpp.core15.model.authorize.AuthorizeResp
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core15.model.bootnotification.enumeration.RegistrationStatus
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core15.model.cancelreservation.enumeration.CancelReservationStatus
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityResp
import com.izivia.ocpp.core15.model.changeavailability.enumeration.AvailabilityStatus
import com.izivia.ocpp.core15.model.changeavailability.enumeration.AvailabilityType
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationResp
import com.izivia.ocpp.core15.model.changeconfiguration.enumeration.ConfigurationStatus
import com.izivia.ocpp.core15.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core15.model.clearcache.ClearCacheResp
import com.izivia.ocpp.core15.model.clearcache.enumeration.ClearCacheStatus
import com.izivia.ocpp.core15.model.common.IdTagInfo
import com.izivia.ocpp.core15.model.common.MeterValue
import com.izivia.ocpp.core15.model.common.enumeration.AuthorisationStatus
import com.izivia.ocpp.core15.model.common.enumeration.RemoteStartStopStatus
import com.izivia.ocpp.core15.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core15.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core15.model.datatransfer.enumeration.DataTransferStatus
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core15.model.firmwarestatusnotification.enumeration.FirmwareStatus
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationResp
import com.izivia.ocpp.core15.model.getconfiguration.KeyValue
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionResp
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core15.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core15.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core15.model.common.enumeration.ChargingRateUnitType
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationResp
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.enumeration.DiagnosticsStatus
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsResp
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionResp
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionResp
import com.izivia.ocpp.core15.model.sendlocallist.AuthorisationData
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core15.model.sendlocallist.enumeration.UpdateStatus
import com.izivia.ocpp.core15.model.sendlocallist.enumeration.UpdateType
import com.izivia.ocpp.core15.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core15.model.reservenow.ReserveNowResp
import com.izivia.ocpp.core15.model.reservenow.enumeration.ReservationStatus
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core15.model.statusnotification.enumeration.ChargePointErrorCode
import com.izivia.ocpp.core15.model.statusnotification.enumeration.ChargePointStatus
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core15.model.unlockconnector.enumeration.UnlockStatus
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.utils.JsonSchemaValidator
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class JsonSchemaTest {

    @Test
    fun `heartbeat request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(HeartbeatReq(), "Heartbeat.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `authorize request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            AuthorizeReq(
                idTag = "Tag1"
            ), "Authorize.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `meterValues request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            MeterValuesReq(
                connectorId = 1,
                values = listOf(MeterValue(timestamp = Instant.parse("2022-02-15T00:00:00.000Z"), value = "0"))
            ), "MeterValues.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            MeterValuesReq(
                connectorId = 1,
                values = listOf(
                    MeterValue(
                        value = "0",
                        timestamp = Instant.parse("2022-02-15T00:00:00.000Z")
                    )
                ),
                transactionId = 1
            ), "MeterValues.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `startTransaction request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            StartTransactionReq(
                connectorId = 1,
                idTag = "Tag1",
                meterStart = 100,
                timestamp = Instant.parse("2022-02-15T00:00:00.000Z")
            ), "StartTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            StartTransactionReq(
                connectorId = 1,
                idTag = "Tag1",
                meterStart = 100,
                timestamp = Instant.parse("2022-02-15T00:00:00.000Z"),
                reservationId = 110
            ), "StartTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `stopTransaction request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            StopTransactionReq(
                meterStop = 200,
                timestamp = Instant.parse("2022-02-15T00:00:00.000Z"),
                transactionId = 12
            ),
            "StopTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            StopTransactionReq(
                meterStop = 200,
                timestamp = Instant.parse("2022-02-15T00:00:00.000Z"),
                transactionId = 12,
                idTag = "Tag1",
                transactionData = listOf(
                    MeterValue(value = "0", timestamp = Instant.parse("2022-02-15T00:00:00.000Z")
                    )
                )
            ),
            "StopTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `statusNotification request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            StatusNotificationReq(
                connectorId = 1, errorCode = ChargePointErrorCode.NoError, status = ChargePointStatus.Occupied
            ), "StatusNotification.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            StatusNotificationReq(
                connectorId = 1,
                errorCode = ChargePointErrorCode.NoError,
                status = ChargePointStatus.Occupied,
                info = "Charging",
                timestamp = Instant.parse("2022-02-15T00:00:00.000Z"),
                vendorErrorCode = "Error",
                vendorId = "vendor1"
            ), "StatusNotification.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

    }

    @Test
    fun `dataTransfer request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            DataTransferReq("vendor1"),
            "DataTransfer.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            DataTransferReq(vendorId = "vendor1", messageId = "message1", data = "data1"),
            "DataTransfer.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `bootNotification request format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            BootNotificationReq(chargePointModel = "model1", chargePointVendor = "vendor1"),
            "BootNotification.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            BootNotificationReq(
                chargePointModel = "model1",
                chargePointVendor = "vendor1",
                chargePointSerialNumber = "SR-100",
                chargeBoxSerialNumber = "BOX-SR-100",
                firmwareVersion = "2.0",
                iccid = "0",
                imsi = "0",
                meterSerialNumber = "SR",
                meterType = "kW"
            ),
            "BootNotification.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `changeAvailability request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ChangeAvailabilityReq(1, AvailabilityType.Operative), "ChangeAvailability.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `clearCache request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ClearCacheReq(), "ClearCache.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `unlockConnector request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            UnlockConnectorReq(1), "UnlockConnector.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `remoteStartTransaction request format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            RemoteStartTransactionReq(idTag = "Tag1"), "RemoteStartTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            RemoteStartTransactionReq(
                idTag = "Tag1", connectorId = 1
            ), "RemoteStartTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `remoteStopTransaction request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            RemoteStopTransactionReq(1), "RemoteStopTransaction.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `getConfiguration request format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            GetConfigurationReq(), "GetConfiguration.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            GetConfigurationReq(listOf("key")), "GetConfiguration.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `changeConfiguration request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ChangeConfigurationReq("key", "value"), "ChangeConfiguration.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `getLocalListVersion request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(GetLocalListVersionReq(), "GetLocalListVersion.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `cancelReservation request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            CancelReservationReq(3), "CancelReservation.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `firmwareStatusNotification request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            FirmwareStatusNotificationReq(FirmwareStatus.Downloaded),
            "FirmwareStatusNotification.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `updateFirmware request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            UpdateFirmwareReq(
                location = "http://www.ietf.org/rfc/rfc2396.txt", // URI
                retries = 2,
                retrieveDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                retryInterval = 3
            ), "UpdateFirmware.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `sendLocalList request format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            SendLocalListReq(listVersion = 1, updateType = UpdateType.Differential), "SendLocalList.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            SendLocalListReq(listVersion = 1,
                updateType = UpdateType.Differential,
                localAuthorizationList = listOf(AuthorisationData(""))), "SendLocalList.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `reserveNow request format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            ReserveNowReq(
                connectorId = 1,
                expiryDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                idTag = "idTag",
                reservationId = 2
            ), "ReserveNow.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }

        errors = JsonSchemaValidator.isValidObjectV4(
            ReserveNowReq(
                connectorId = 1,
                expiryDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                idTag = "idTag",
                reservationId = 2,
                parentIdTag = "partentIdTag"
            ), "ReserveNow.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `diagnosticsStatusNotification request format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            DiagnosticsStatusNotificationReq(
                status = DiagnosticsStatus.Uploaded
            ), "DiagnosticsStatusNotification.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `getDiagnostics request format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            GetDiagnosticsReq("http://www.ietf.org/rfc/rfc2396.txt"),
            "GetDiagnostics.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            GetDiagnosticsReq(
                location = "http://www.ietf.org/rfc/rfc2396.txt",
                retries = 3,
                retryInterval = 2,
                startTime = Instant.parse("2022-02-15T00:00:00.000Z"),
                stopTime = Instant.parse("2022-02-15T00:00:00.000Z")
            ), "GetDiagnostics.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `heartbeat response format`() {
        val heartbeatResp = HeartbeatResp(
            currentTime = Instant.parse("2022-02-15T00:00:00.000Z")
        )
        val errors = JsonSchemaValidator.isValidObjectV4(heartbeatResp, "HeartbeatResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `authorize response format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            AuthorizeResp(
                idTagInfo = IdTagInfo(status = AuthorisationStatus.Accepted)
            ), "AuthorizeResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            AuthorizeResp(
                idTagInfo = IdTagInfo(
                    status = AuthorisationStatus.Accepted,
                    expiryDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                    parentIdTag = "Parent"
                )
            ), "AuthorizeResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `meterValues response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            MeterValuesResp(), "MeterValuesResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `startTransaction response format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            StartTransactionResp(IdTagInfo(status = AuthorisationStatus.Accepted), 12),
            "StartTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            StartTransactionResp(
                idTagInfo = IdTagInfo(
                    status = AuthorisationStatus.Accepted,
                    expiryDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                    parentIdTag = "Tag1"
                ), transactionId = 12
            ),
            "StartTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `stopTransaction response format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            StopTransactionResp(), "StopTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            StopTransactionResp(
                IdTagInfo(
                    status = AuthorisationStatus.Accepted,
                    expiryDate = Instant.parse("2022-02-15T00:00:00.000Z"),
                    parentIdTag = "Tag1"
                )
            ), "StopTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `statusNotification response format`() {
        val errors =
            JsonSchemaValidator.isValidObjectV4(StatusNotificationResp(), "StatusNotificationResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `dataTransfer response format`() {
        /* Required field only */
        var errors = JsonSchemaValidator.isValidObjectV4(
            DataTransferResp(DataTransferStatus.Accepted),
            "DataTransferResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        /* Every field */
        errors = JsonSchemaValidator.isValidObjectV4(
            DataTransferResp(DataTransferStatus.Accepted, "data1"),
            "DataTransferResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `bootNotification response format`() {
        /* Required field only */
        val errors = JsonSchemaValidator.isValidObjectV4(
            BootNotificationResp(
                currentTime = Instant.parse("2022-02-15T00:00:00.000Z"),
                heartbeatInterval = 10,
                status = RegistrationStatus.Accepted
            ),
            "BootNotificationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `changeAvailability response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ChangeAvailabilityResp(AvailabilityStatus.Accepted), "ChangeAvailabilityResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `clearCache response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ClearCacheResp(ClearCacheStatus.Accepted), "ClearCacheResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `unlockConnector response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            UnlockConnectorResp(UnlockStatus.Accepted), "UnlockConnectorResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `remoteStartTransaction response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            RemoteStartTransactionResp(status = RemoteStartStopStatus.Accepted), "RemoteStartTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `remoteStopTransaction response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            RemoteStopTransactionResp(RemoteStartStopStatus.Accepted), "RemoteStopTransactionResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `getConfiguration response format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(
            GetConfigurationResp(), "GetConfigurationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            GetConfigurationResp(listOf(KeyValue("key", true)), listOf("unknown key")), "GetConfigurationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(
            GetConfigurationResp(listOf(KeyValue("key", true, "value"))), "GetConfigurationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `changeConfiguration response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ChangeConfigurationResp(ConfigurationStatus.Accepted), "ChangeConfigurationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `getLocalListVersion response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(GetLocalListVersionResp(1), "GetLocalListVersionResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `firmwareStatusNotification response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(FirmwareStatusNotificationResp(), "FirmwareStatusNotificationResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `cancelReservation response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            CancelReservationResp(CancelReservationStatus.Rejected), "CancelReservationResponse.json"
        )
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `updateFirmware response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            UpdateFirmwareResp(), "UpdateFirmwareResponse.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `sendLocalList response format`() {
        val errors =
            JsonSchemaValidator.isValidObjectV4(SendLocalListResp(status = UpdateStatus.Accepted),
                "SendLocalListResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

    @Test
    fun `reserveNow response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            ReserveNowResp(ReservationStatus.Accepted), "ReserveNowResponse.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `diagnosticsStatusNotification response format`() {
        val errors = JsonSchemaValidator.isValidObjectV4(
            DiagnosticsStatusNotificationResp(), "DiagnosticsStatusNotificationResponse.json"
        )
        expectThat(errors) {
            get { this.size }.isEqualTo(0)
        }
    }

    @Test
    fun `getDiagnostics response format`() {
        var errors = JsonSchemaValidator.isValidObjectV4(GetDiagnosticsResp(), "GetDiagnosticsResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }

        errors = JsonSchemaValidator.isValidObjectV4(GetDiagnosticsResp("fileName"),
            "GetDiagnosticsResponse.json")
        expectThat(errors)
            .and { get { this.size }.isEqualTo(0) }
    }

}
