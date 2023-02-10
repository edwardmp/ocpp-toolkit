package com.izivia.ocpp.utils

enum class MessageErrorCode(val errorCode: String, val description: String) {
    NOT_IMPLEMENTED("NotImplemented", "Requested Action is not known by receiver"),
    NOT_SUPPORTED("NotSupported", "Requested Action is recognized but not supported by the receiver"),
    INTERNAL_ERROR(
        "InternalError",
        "An internal error occurred and the receiver was not able to process the requested Action successfully"
    ),
    PROTOCOL_ERROR("ProtocolError", "Payload for Action is incomplete"),
    SECURITY_ERROR(
        "SecurityError",
        "During the processing of Action a security issue occurred preventing receiver " +
            "from completing the Action successfully"
    ),
    FORMATION_VIOLATION(
        "FormationViolation",
        "Payload for Action is syntactically incorrect " +
            "or not conform the PDU structure for Action"
    ),
    PROPERTY_CONSTRAINT_VIOLATION(
        "PropertyConstraintViolation",
        "Payload is syntactically correct but at least one field contains an invalid value"
    ),
    OCCURENCE_CONTRAINT_VIOLATION(
        "OccurenceConstraintViolation",
        "Payload for Action is syntactically correct but at least one of the " +
            "fields violates occurence constraints"
    ),
    TYPE_CONTRAINT_VIOLATION(
        "TypeConstraintViolation",
        "Payload for Action is syntactically correct but at least one of the fields " +
            "violates data type constraints (e.g. “somestring”: 12)"
    ),
    GENERIC_ERROR("GenericError", "Any other error not covered by the previous ones"),

    // Specific for OCPP 2.0
    FORMAT_VIOLATION("FormatViolation", FORMATION_VIOLATION.description),
    MESSAGE_TYPE_NOT_SUPPORTED("MessageTypeNotSupported", "Unsupported message type"),
    RPC_FRAMEWORK_ERROR(
        "RpcFrameworkError",
        "Content of the call is not a valid RPC Request, for example: UniqueId could not be read."
    );

    companion object {
        fun fromValue(value: String) =
            MessageErrorCode
                .values()
                .firstOrNull { it.errorCode.lowercase() == value.lowercase() }
                ?: throw IllegalArgumentException(
                    "$value is not a valid json message error code - " +
                        "supported types: ${MessageErrorCode.values().map { it.errorCode }}"
                )
    }
}
