package com.izivia.ocpp.json

import com.izivia.ocpp.json.JsonMessageType.*

data class JsonMessage<T>(
    val msgType: JsonMessageType,
    val msgId: String,
    val action: String? = null,
    val errorCode: JsonMessageErrorCode? = null,
    val errorDescription: String? = null,
    val payload: T
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

        fun <T> CallError(msgId: String, errorCode: JsonMessageErrorCode, errorDescription: String, payload: T) =
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
            ?: throw IllegalArgumentException("$id is not a valid json message type - supported types: ${values().map { it.id }}")
    }
}

enum class JsonMessageErrorCode(val value: String) {
    NOT_IMPLEMENTED("NotImplemented"),
    NOT_SUPPORTED("NotSupported"),
    INTERNAL_ERROR("InternalError"),
    PROTOCOL_ERROR("ProtocolError"),
    SECURITY_ERROR("SecurityError"),
    FORMATION_VIOLATION("FormationViolation"),
    PROPERTY_CONSTRAINT_VIOLATION("PropertyConstraintViolation"),
    OCCURENCE_CONSTRAINT_VIOLATION("OccurenceConstraintViolation"),
    TYPE_CONSTRAINT_VIOLATION("TypeConstraintViolation"),
    GENERIC_ERROR("GenericError"),
    // Specific for OCPP 2.0
    FORMAT_VIOLATION("FormatViolation"),
    MESSAGE_TYPE_NOT_SUPPORTED("MessageTypeNotSupported"),
    OCCURRENCE_CONSTRAINT_VIOLATION("OccurrenceConstraintViolation"),
    RPC_FRAMEWORK_ERROR("RpcFrameworkError"),
}

class JsonMessageEmptyPayload
