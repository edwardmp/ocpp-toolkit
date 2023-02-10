package com.izivia.ocpp.json20

import com.fasterxml.jackson.databind.JsonNode
import com.izivia.ocpp.core20.model.common.enumeration.Actions
import com.izivia.ocpp.json.JsonMessage
import com.izivia.ocpp.json.JsonMessageType
import com.izivia.ocpp.json.OcppJsonParser
import com.izivia.ocpp.json.OcppJsonValidator
import com.izivia.ocpp.utils.MessageTypeException
import com.networknt.schema.SpecVersion
import com.networknt.schema.ValidationMessage

class Ocpp20JsonParser(override val ignoreValidationCodes: List<String> = emptyList()) :
    OcppJsonParser(Ocpp20JsonObjectMapper) {

    override val ocppJsonValidator = OcppJsonValidator(ignoreValidationCodes, SpecVersion.VersionFlag.V6)

    override fun getRequestPayloadClass(action: String, errorHandler: (e: Exception) -> Throwable): Class<out Any> =
        try {
            Actions.valueOf(action.uppercase()).let {
                javaClass.classLoader.loadClass(
                    "com.izivia.ocpp.core20.model.${it.lowercase()}.${it.camelCaseRequest()}"
                )
            }
        } catch (e: Exception) {
            throw errorHandler(e)
        }

    override fun getResponseActionFromClass(className: String): String =
        Actions.valueOf(className.replace("[Resp|Req]$", "").uppercase()).value

    override fun validateJson(
        jsonMessage: JsonMessage<JsonNode>,
        errorsHandler: (errors: List<ValidationMessage>) -> Unit
    ) {
        ocppJsonValidator.isValidObject(
            action = when (jsonMessage.msgType) {
                JsonMessageType.CALL -> "${jsonMessage.action}Request"
                JsonMessageType.CALL_RESULT -> "${jsonMessage.action}Response"
                JsonMessageType.CALL_ERROR -> return
                else -> throw MessageTypeException(
                    message = "MessageType not supported ${jsonMessage.msgType}",
                    messageId = jsonMessage.msgId
                )
            },
            payload = jsonMessage.payload
        )

            ?.let { errorsHandler(it) }
    }
}
