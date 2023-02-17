package com.izivia.ocpp.json

import com.fasterxml.jackson.databind.JsonNode
import com.networknt.schema.JsonSchema
import com.networknt.schema.JsonSchemaFactory
import com.networknt.schema.SpecVersion
import com.networknt.schema.ValidationMessage
import com.networknt.schema.ValidatorTypeCode
import org.slf4j.LoggerFactory
import java.io.InputStream

class OcppJsonValidator(
    private val ignoreValidationCodes: List<ValidatorTypeCode> = emptyList(),
    private val specVersion: SpecVersion.VersionFlag
) {
    private val logger = LoggerFactory.getLogger(OcppJsonValidator::class.java)
    private val jsonSchemas = mutableMapOf<String, JsonSchema>()

    private fun getJsonSchema(file: String): JsonSchema {
        val factory: JsonSchemaFactory = JsonSchemaFactory.getInstance(specVersion)
        val input: InputStream? = Thread.currentThread().contextClassLoader.getResourceAsStream(file)
        return factory.getSchema(input)
    }

    /**
     * Serialize the object with jackson and verify that the format is conformed to the
     * json schema
     */
    fun isValidObject(action: String, payload: JsonNode): List<ValidationMessage>? =
        // Info :  loading JsonSchema is not thread safe. Can affect performance during the first instanciation
        (jsonSchemas[action] ?: getJsonSchema("$action.json").also { jsonSchemas[action] = it })
            .validate(payload)
            .mapNotNull {
                if (ignoreValidationCodes.contains(ValidatorTypeCode.fromValue(it.type))) {
                    logger.warn("Ignoring validation code ${it.code} : ${it.message}")
                    null
                } else {
                    it
                }
            }
            .ifEmpty { null }
}
