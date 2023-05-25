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
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String, override val classRequest: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE("authorize", AuthorizeReq::class.java, OcppInitiator.CHARGING_STATION),
    BOOTNOTIFICATION("bootNotification", BootNotificationReq::class.java, OcppInitiator.CHARGING_STATION),
    CANCELRESERVATION("cancelReservation", CancelReservationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CHANGEAVAILABILITY("changeAvailability", ChangeAvailabilityReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CHANGECONFIGURATION("changeConfiguration", ChangeConfigurationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, OcppInitiator.ALL),
    DIAGNOSTICSSTATUSNOTIFICATION(
        "diagnosticsStatusNotification",
        DiagnosticsStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCONFIGURATION("getConfiguration", GetConfigurationReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETDIAGNOSTICS("getDiagnostics", GetDiagnosticsReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    GETLOCALLISTVERSION("getLocalListVersion", GetLocalListVersionReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    HEARTBEAT("heartbeat", HeartbeatReq::class.java, OcppInitiator.CHARGING_STATION),
    METERVALUES("meterValues", MeterValuesReq::class.java, OcppInitiator.CHARGING_STATION),
    REMOTESTARTTRANSACTION(
        "remoteStartTransaction",
        RemoteStartTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REMOTESTOPTRANSACTION("remoteStopTransaction", RemoteStopTransactionReq::class.java,
        OcppInitiator.CENTRAL_SYSTEM),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SENDLOCALLIST("sendLocalList", SendLocalListReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    STARTTRANSACTION("startTransaction", StartTransactionReq::class.java, OcppInitiator.CHARGING_STATION),
    STATUSNOTIFICATION("statusNotification", StatusNotificationReq::class.java, OcppInitiator.CHARGING_STATION),
    STOPTRANSACTION("stopTransaction", StopTransactionReq::class.java, OcppInitiator.CHARGING_STATION),
    UNLOCKCONNECTOR("unlockConnector", UnlockConnectorReq::class.java, OcppInitiator.CENTRAL_SYSTEM),
    UPDATEFIRMWARE("updateFirmware", UpdateFirmwareReq::class.java, OcppInitiator.CENTRAL_SYSTEM);

    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
