package com.izivia.ocpp.wamp.messages

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.OcppVersion
import com.izivia.ocpp.wamp.messages.WampMessageType.*

data class WampMessage(
    val msgType: WampMessageType,
    val msgId: String,
    val action: String? = null,
    val errorCode: String? = null,
    val errorDescription: String? = null,
    val payload: String
) {

    fun toJson(): String = when (msgType) {
        CALL -> """[${msgType.id},"$msgId","${action!!}",$payload]"""
        CALL_RESULT -> """[${msgType.id},"$msgId",$payload]"""
        CALL_ERROR -> """[${msgType.id},"$msgId","${errorCode!!}","${errorDescription!!}",$payload]"""
    }

    companion object {
        fun Call(msgId: String, action: String, payload: String) =
            WampMessage(
                msgType = CALL,
                msgId = msgId,
                action = action,
                payload = payload
            )

        fun CallResult(msgId: String, payload: String) =
            WampMessage(
                msgType = CALL_RESULT,
                msgId = msgId,
                payload = payload
            )

        fun CallError(msgId: String, errorCode: String, errorDescription: String, payload: String) =
            WampMessage(
                msgType = CALL_ERROR,
                msgId = msgId,
                errorCode = errorCode,
                errorDescription = errorDescription,
                payload = payload
            )

        fun parse(str: String) = WampMessageParser.parse(str)
    }
}

object WampMessageParser {
    // Same as JsonMessage - cross-reference 7bb7e3a7-bbef-4ff4-a8e6-6a3622e9bd4b
    private val ocppMsgRegex =
        Regex("""\[\s*(\d+)\s*,\s*"([^"]+)"\s*(?:,\s*"([^"]+)"\s*)?(?:,\s*"([^"]+)"\s*)?,\s*(.+)]""")

    fun parse(string: String): WampMessage? =
        ocppMsgRegex.matchEntire(string.replace("\n", ""))
            ?.destructured
            ?.let {
                when (WampMessageType.fromId(it.component1().toInt())) {
                    CALL -> it.let { (_, msgId, action, _, payload) ->
                        WampMessage.Call(msgId, action, payload)
                    }

                    CALL_RESULT -> it.let { (_, msgId, _, _, payload) ->
                        WampMessage.CallResult(msgId, payload)
                    }

                    CALL_ERROR -> it.let { (_, msgId, errorCode, errorDescription, payload) ->
                        WampMessage.CallError(msgId, errorCode, errorDescription, payload)
                    }
                }
            }
}

enum class WampMessageType(val id: Int) {
    CALL(2), CALL_RESULT(3), CALL_ERROR(4);

    companion object {
        fun fromId(id: Int) = values().filter { it.id == id }.firstOrNull()
            ?: throw IllegalArgumentException(
                "$id is not a valid Wamp message type - supported types: ${values().map { it.id }}"
            )
    }
}

data class WampMessageMeta(val ocppVersion: OcppVersion, val ocppId: CSOcppId)
