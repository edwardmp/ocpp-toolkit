package com.izivia.ocpp.core15.model.common.enumeration

import com.izivia.ocpp.core15.model.authorize.AuthorizeReq
import com.izivia.ocpp.core15.model.authorize.AuthorizeResp
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core15.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationReq
import com.izivia.ocpp.core15.model.cancelreservation.CancelReservationResp
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityReq
import com.izivia.ocpp.core15.model.changeavailability.ChangeAvailabilityResp
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationReq
import com.izivia.ocpp.core15.model.changeconfiguration.ChangeConfigurationResp
import com.izivia.ocpp.core15.model.clearcache.ClearCacheReq
import com.izivia.ocpp.core15.model.clearcache.ClearCacheResp
import com.izivia.ocpp.core15.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core15.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core15.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationResp
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core15.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationReq
import com.izivia.ocpp.core15.model.getconfiguration.GetConfigurationResp
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsReq
import com.izivia.ocpp.core15.model.getdiagnostics.GetDiagnosticsResp
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionReq
import com.izivia.ocpp.core15.model.getlocallistversion.GetLocalListVersionResp
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core15.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core15.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core15.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionReq
import com.izivia.ocpp.core15.model.remotestart.RemoteStartTransactionResp
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionReq
import com.izivia.ocpp.core15.model.remotestop.RemoteStopTransactionResp
import com.izivia.ocpp.core15.model.reservenow.ReserveNowReq
import com.izivia.ocpp.core15.model.reservenow.ReserveNowResp
import com.izivia.ocpp.core15.model.reset.ResetReq
import com.izivia.ocpp.core15.model.reset.ResetResp
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListReq
import com.izivia.ocpp.core15.model.sendlocallist.SendLocalListResp
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core15.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core15.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core15.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorReq
import com.izivia.ocpp.core15.model.unlockconnector.UnlockConnectorResp
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareReq
import com.izivia.ocpp.core15.model.updatefirmware.UpdateFirmwareResp
import com.izivia.ocpp.utils.IActions
import com.izivia.ocpp.utils.OcppInitiator

enum class Actions(
    override val value: String,
    override val classRequest: Class<*>,
    override val classResponse: Class<*>,
    override val initiatedBy: OcppInitiator
) : IActions {
    AUTHORIZE("authorize", AuthorizeReq::class.java, AuthorizeResp::class.java, OcppInitiator.CHARGING_STATION),
    BOOTNOTIFICATION(
        "bootNotification",
        BootNotificationReq::class.java,
        BootNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    CANCELRESERVATION(
        "cancelReservation",
        CancelReservationReq::class.java,
        CancelReservationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CHANGEAVAILABILITY(
        "changeAvailability",
        ChangeAvailabilityReq::class.java,
        ChangeAvailabilityResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CHANGECONFIGURATION(
        "changeConfiguration",
        ChangeConfigurationReq::class.java,
        ChangeConfigurationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    CLEARCACHE("clearCache", ClearCacheReq::class.java, ClearCacheResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    DATATRANSFER("dataTransfer", DataTransferReq::class.java, DataTransferResp::class.java, OcppInitiator.ALL),
    DIAGNOSTICSSTATUSNOTIFICATION(
        "diagnosticsStatusNotification",
        DiagnosticsStatusNotificationReq::class.java,
        DiagnosticsStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    FIRMWARESTATUSNOTIFICATION(
        "firmwareStatusNotification",
        FirmwareStatusNotificationReq::class.java,
        FirmwareStatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    GETCONFIGURATION(
        "getConfiguration",
        GetConfigurationReq::class.java,
        GetConfigurationResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETDIAGNOSTICS(
        "getDiagnostics",
        GetDiagnosticsReq::class.java,
        GetDiagnosticsResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    GETLOCALLISTVERSION(
        "getLocalListVersion",
        GetLocalListVersionReq::class.java,
        GetLocalListVersionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    HEARTBEAT(
        "heartbeat",
        HeartbeatReq::class.java,
        HeartbeatResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    METERVALUES(
        "meterValues",
        MeterValuesReq::class.java,
        MeterValuesResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    REMOTESTARTTRANSACTION(
        "remoteStartTransaction",
        RemoteStartTransactionReq::class.java,
        RemoteStartTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    REMOTESTOPTRANSACTION(
        "remoteStopTransaction",
        RemoteStopTransactionReq::class.java,
        RemoteStopTransactionResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    RESERVENOW("reserveNow", ReserveNowReq::class.java, ReserveNowResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    RESET("reset", ResetReq::class.java, ResetResp::class.java, OcppInitiator.CENTRAL_SYSTEM),
    SENDLOCALLIST(
        "sendLocalList",
        SendLocalListReq::class.java,
        SendLocalListResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    STARTTRANSACTION(
        "startTransaction",
        StartTransactionReq::class.java,
        StartTransactionResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    STATUSNOTIFICATION(
        "statusNotification",
        StatusNotificationReq::class.java,
        StatusNotificationResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    STOPTRANSACTION(
        "stopTransaction",
        StopTransactionReq::class.java,
        StopTransactionResp::class.java,
        OcppInitiator.CHARGING_STATION
    ),
    UNLOCKCONNECTOR(
        "unlockConnector",
        UnlockConnectorReq::class.java,
        UnlockConnectorResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    ),
    UPDATEFIRMWARE(
        "updateFirmware",
        UpdateFirmwareReq::class.java,
        UpdateFirmwareResp::class.java,
        OcppInitiator.CENTRAL_SYSTEM
    );


    fun lowercase() = value.lowercase()

    fun camelCase() = value.replaceFirstChar { it.uppercase() }

    fun camelCaseRequest() = "${camelCase()}Req"
}
