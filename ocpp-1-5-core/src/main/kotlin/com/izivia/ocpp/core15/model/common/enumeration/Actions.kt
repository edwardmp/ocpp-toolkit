package com.izivia.ocpp.core15.model.common.enumeration

import com.izivia.ocpp.core15.model.authorize.AuthorizeReq
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core15.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core15.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core15.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core15.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core15.model.reset.ResetReq
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareReq

enum class Actions(val value: String, val classRequest: Class<*>) {
    AUTHORIZE("authorize", AuthorizeReq::class.java),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java),
    CHANGECONFIGURATION("changeConfiguration", ChangeConfigurationReq::class.java),
    CLEARCACHE("clearCache", ClearCacheReq::class.java),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java),
    DIAGNOSTICSSTATUSNOTIFICATION("diagnosticsStatusNotification", DiagnosticsStatusNotificationReq::class.java),
    FIRMWARESTATUSNOTIFICATION("firmwareStatusNotification", FirmwareStatusNotificationReq::class.java),
    GETCONFIGURATION("getConfiguration", GetConfigurationReq::class.java),
    GETDIAGNOSTICS("getDiagnostics", GetDiagnosticsReq::class.java),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java),
    METERVALUES("meterValues", MeterValuesReq::class.java),
    REMOTESTARTTRANSACTION("remoteStartTransaction", RemoteStartTransactionReq::class.java),
    REMOTESTOPTRANSACTION("remoteStopTransaction", RemoteStopTransactionReq::class.java),
    RESERVENOW("reserveNow", ReserveNowReq::class.java),
    RESET("reset", ResetReq::class.java),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java),
    STARTTRANSACTION("startTransaction", StartTransactionReq::class.java),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java),
    STOPTRANSACTION("stopTransaction", StopTransactionReq::class.java),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java),
    UPDATEFIRMWARE("updateFirmware", UpdateFirmwareReq::class.java);

    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
