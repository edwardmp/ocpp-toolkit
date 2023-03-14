package com.izivia.ocpp.json15

import com.fasterxml.jackson.databind.JsonNode
import com.izivia.ocpp.core15.Ocpp15ForceConvertField
import com.izivia.ocpp.core15.Ocpp15IgnoredNullRestriction
import com.izivia.ocpp.core15.model.common.enumeration.Actions
import com.izivia.ocpp.json.JsonMessage
import com.izivia.ocpp.json.JsonMessageType
import com.izivia.ocpp.json.OcppJsonParser
import com.izivia.ocpp.json.OcppJsonValidator
import com.izivia.ocpp.utils.MessageTypeException
import com.networknt.schema.SpecVersion
import com.networknt.schema.ValidationMessage
import com.networknt.schema.ValidatorTypeCode

class Ocpp15JsonParser(
    override val ignoredNullRestrictions: List<Ocpp15IgnoredNullRestriction>? = null,
    override val ignoredValidationCodes: List<ValidatorTypeCode>? = null,
    override val forceConvertFields: List<Ocpp15ForceConvertField>? = null,
    enableValidation: Boolean = true
) :
    OcppJsonParser(
        mapper = Ocpp15JsonObjectMapper,
        forceConvertFields = forceConvertFields,
        ignoredValidationCodes = ignoredValidationCodes,
        ignoredNullRestrictions = ignoredNullRestrictions,
        ocppJsonValidator = if (enableValidation) {
            OcppJsonValidator(SpecVersion.VersionFlag.V4)
        } else null
    ) {

    override fun getRequestPayloadClass(action: String, errorHandler: (e: Exception) -> Throwable): Class<out Any> =
        try {
            Actions.valueOf(action.uppercase()).classRequest
        } catch (e: Exception) {
            throw errorHandler(e)
        }

    override fun getActionFromClass(className: String): String =
        Actions.valueOf(className.replace(classActionRegex, "").uppercase()).value

    override fun validateJson(
        jsonMessage: JsonMessage<JsonNode>,
        errorsHandler: (errors: List<ValidationMessage>) -> Unit
    ) {
        val action = Actions.valueOf(jsonMessage.action!!.uppercase()).camelCase()
        ocppJsonValidator?.isValidObject(
            action = when (jsonMessage.msgType) {
                JsonMessageType.CALL -> action
                JsonMessageType.CALL_RESULT -> "${action}Response"
                JsonMessageType.CALL_ERROR -> return
                else -> throw MessageTypeException(
                    message = "MessageType not supported ${jsonMessage.msgType}",
                    messageId = jsonMessage.msgId
                )
            },
            payload = jsonMessage.payload
        )
            ?.let {
                errorsHandler(it)
            }
    }
}
