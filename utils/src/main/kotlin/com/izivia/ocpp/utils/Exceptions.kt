package com.izivia.ocpp.utils

import java.lang.RuntimeException

data class ErrorDetail(
    val code: String,
    val detail: String
)

enum class ErrorDetailCode(val value: String) {
    STACKTRACE("stackTrace"),
    ACTION("action"),
    MISSING_FIELD_REPLACED("missingFieldReplaced"),
    MESSAGE("message");
}

open class OcppParserException(
    override val message: String,
    val errorCode: MessageErrorCode,
    open val messageId: String? = null,
    open val errorDetails: List<ErrorDetail>? = null
) : RuntimeException()

class ValidationException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        errorCode = MessageErrorCode.PROTOCOL_ERROR,
        messageId = messageId,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.PROTOCOL_ERROR.errorCode,
                detail = MessageErrorCode.PROTOCOL_ERROR.description
            )
        ).plus(errorDetails)
    )

class FormatViolationException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        messageId = messageId,
        errorCode = MessageErrorCode.FORMAT_VIOLATION,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.FORMAT_VIOLATION.errorCode,
                detail = MessageErrorCode.FORMAT_VIOLATION.description
            )
        ).plus(errorDetails)
    )

class ActionRequestNullOrUnknownException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        messageId = messageId,
        errorCode = MessageErrorCode.NOT_IMPLEMENTED,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.NOT_IMPLEMENTED.errorCode,
                detail = MessageErrorCode.NOT_IMPLEMENTED.description
            )
        ).plus(errorDetails)
    )

class MessageTypeException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        messageId = messageId,
        errorCode = MessageErrorCode.MESSAGE_TYPE_NOT_SUPPORTED,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.MESSAGE_TYPE_NOT_SUPPORTED.errorCode,
                detail = MessageErrorCode.MESSAGE_TYPE_NOT_SUPPORTED.description
            )
        ).plus(errorDetails)
    )

class MalformedOcppMessageException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        messageId = messageId,
        errorCode = MessageErrorCode.FORMAT_VIOLATION,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.FORMAT_VIOLATION.errorCode,
                detail = MessageErrorCode.FORMAT_VIOLATION.description
            )
        ).plus(errorDetails)
    )

class MissingRelatesToHeaderException(
    override val message: String,
    override val messageId: String?,
    override val errorDetails: List<ErrorDetail> = emptyList()
) :
    OcppParserException(
        message = message,
        messageId = messageId,
        errorCode = MessageErrorCode.GENERIC_ERROR,
        errorDetails = listOf(
            ErrorDetail(
                code = MessageErrorCode.GENERIC_ERROR.errorCode,
                detail = MessageErrorCode.GENERIC_ERROR.description
            )
        ).plus(errorDetails)
    )
