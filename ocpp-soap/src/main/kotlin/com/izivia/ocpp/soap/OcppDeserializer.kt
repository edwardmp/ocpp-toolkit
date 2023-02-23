package com.izivia.ocpp.soap

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode

abstract class AbstractIgnoredNullRestriction(
    open val fieldPath: String,
    open val isRequest: Boolean,
    open val defaultNullValue: String,
    val fieldPathSeparator: String = "."
) {
    abstract fun getBodyAction(): String
}

class OcppDeserializer<T : SoapBody>(
    val defaultKeyValue: List<AbstractIgnoredNullRestriction> = emptyList(),
    val subMapper: ObjectMapper,
    val bodyClass: Class<T>
) :
    JsonDeserializer<T>() {
    override fun deserialize(p: JsonParser?, ctxt: DeserializationContext?): T {
        ctxt?.readTree(p).let { node ->
            node?.let {
                defaultKeyValue.forEach { validationIgnored ->
                    node.takeIf { it.has(validationIgnored.getBodyAction()) }
                        ?.let {
                            val splitKey = validationIgnored.fieldPath.split(validationIgnored.fieldPathSeparator)
                            node.findValue(splitKey.last())
                                ?: setDefaultNullValue(
                                    keyPath = splitKey,
                                    value = validationIgnored.defaultNullValue,
                                    node.first() as ObjectNode
                                )
                        }
                }
            }
            return subMapper.treeToValue(node, bodyClass)
        }
    }
}

private fun setDefaultNullValue(keyPath: List<String>, value: String, node: ObjectNode, iter: Int = 0): ObjectNode {
    return if (node.has(keyPath[iter])) {
        setDefaultNullValue(keyPath, value, node.findValue(keyPath[iter]) as ObjectNode, iter + 1)
    } else {
        node.put(keyPath.last(), value)
    }
}
