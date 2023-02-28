package com.izivia.ocpp.json

import com.izivia.ocpp.json.JsonMessageType.*
import com.izivia.ocpp.utils.ErrorDetail
import com.izivia.ocpp.utils.MessageErrorCode

data class JsonMessage<T>(
    val msgType: JsonMessageType,
    val msgId: String,
    val action: String? = null,
    val errorCode: MessageErrorCode? = null,
    val errorDescription: String? = null,
    val payload: T,
    val warnings: List<ErrorDetail>? = null
) {
    companion object {
        fun <T> Call(msgId: String, action: String, payload: T) =
            JsonMessage(
                msgType = CALL,
                msgId = msgId,
                action = action,
                payload = payload
            )

        fun <T> CallResult(msgId: String, payload: T) =
            JsonMessage(
                msgType = CALL_RESULT,
                msgId = msgId,
                payload = payload
            )

        fun <T> CallError(msgId: String, errorCode: MessageErrorCode, errorDescription: String, payload: T) =
            JsonMessage(
                msgType = CALL_ERROR,
                msgId = msgId,
                errorCode = errorCode,
                errorDescription = errorDescription,
                payload = payload
            )
    }
}

enum class JsonMessageType(val id: Int) {
    CALL(2),
    CALL_RESULT(3),
    CALL_ERROR(4);

    companion object {
        fun fromId(id: Int) = values()
            .firstOrNull { it.id == id }
            ?: throw IllegalArgumentException(
                "$id is not a valid json message type - supported types: ${values().map { it.id }}"
            )
    }
}

class JsonMessageEmptyPayload
