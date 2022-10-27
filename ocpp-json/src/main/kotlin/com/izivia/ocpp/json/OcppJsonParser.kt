package com.izivia.ocpp.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.izivia.ocpp.json.JsonMessageType.*
import kotlin.reflect.KClass

abstract class OcppJsonParser(private val mapper: ObjectMapper) {

    protected abstract fun getRequestPayloadClass(action: String, context: String): Class<out Any>

    fun parseAnyRequestFromJson(messageStr: String): JsonMessage<Any> {
        val parsed = parseAsStringPayloadFromJson(messageStr)
            ?: throw IllegalArgumentException("Impossible parsing of message. message = $messageStr")

        if (parsed.action == null)
            throw IllegalArgumentException("The message action must not be null. message = $messageStr")

        val clazz = getRequestPayloadClass(parsed.action, messageStr)

        return JsonMessage(
            msgType = parsed.msgType,
            msgId = parsed.msgId,
            action = parsed.action,
            payload = mapper.readValue(parsed.payload, clazz)
        )
    }

    fun <T : Any> parseFromJson(messageStr: String, clazz: KClass<T>): JsonMessage<T> {
        val parsed = parseAsStringPayloadFromJson(messageStr)
            ?: throw IllegalArgumentException("Impossible parsing of message. message = $messageStr")

        // Cannot use .copy() because of class cast error between JsonMessage<T> and JsonMessage<String>
        return JsonMessage(
            msgType = parsed.msgType,
            msgId = parsed.msgId,
            action = parsed.action,
            errorCode = parsed.errorCode,
            errorDescription = parsed.errorDescription,
            payload = mapper.readValue(parsed.payload, clazz.java)
        )
    }

    fun parseAsStringPayloadFromJson(messageStr: String): JsonMessage<String>? =
        ocppMsgRegex.matchEntire(messageStr.replace("\n", ""))
            ?.destructured
            ?.let {
                when (val msgType = it.component1().toInt()) {
                    CALL.id -> it.let { (_, msgId, action, _, payload) ->
                        JsonMessage.Call(msgId, action, payload)
                    }

                    CALL_RESULT.id -> it.let { (_, msgId, _, _, payload) ->
                        JsonMessage.CallResult(msgId, payload)
                    }

                    CALL_ERROR.id -> it.let { (_, msgId, errorCode, errorDescription, payload) ->
                        JsonMessage.CallError(msgId, JsonMessageErrorCode.valueOf(errorCode), errorDescription, payload)
                    }

                    else -> throw IllegalArgumentException("message type $msgType not known. message = $messageStr")
                }
            }

    fun <T : Any> parsePayloadFromJson(payload: String, clazz: KClass<T>): T =
        mapper.readValue(payload, clazz.java)

    fun <T> mapPayloadToString(payload: T): String =
        mapper.writeValueAsString(payload)

    fun <T> mapToJson(message: JsonMessage<T>): String =
        message
            .let {
                message
                    .payload
                    .takeUnless { it == null || (it is String && it.isBlank()) }
                    ?: JsonMessageEmptyPayload()
            }
            .let { payload ->
                when (message.msgType) {
                    CALL -> listOf(
                        message.msgType.id,
                        message.msgId,
                        message.action,
                        payload
                    )

                    CALL_RESULT -> listOf(
                        message.msgType.id,
                        message.msgId,
                        payload
                    )

                    CALL_ERROR -> listOf(
                        message.msgType.id,
                        message.msgId,
                        message.errorCode!!.value,
                        message.errorDescription,
                        payload
                    )
                }
            }
            .let { mapper.writeValueAsString(it) }

    companion object {
        // Same as WampMessage - cross-reference 7bb7e3a7-bbef-4ff4-a8e6-6a3622e9bd4b
        private val ocppMsgRegex =
            Regex("""\[\s*(\d+)\s*,\s*"([^"]+)"\s*(?:,\s*"([^"]+)"\s*)?(?:,\s*"([^"]+)"\s*)?,\s*(.+)]""")
    }
}

inline fun <reified T : Any> OcppJsonParser.parseFromJson(messageStr: String): JsonMessage<T> =
    parseFromJson(messageStr, T::class)
