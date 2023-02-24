package com.izivia.ocpp.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

inline fun <reified T : Any> Any.isA(function: (T) -> Any = { it -> it }): Any =
    if (this is T) function(this) else this

abstract class AbstractIgnoredNullRestriction(
    open val fieldPath: String,
    open val isRequest: Boolean,
    open val defaultNullValue: String,
    val fieldPathSeparator: String = "."
) {
    abstract fun getBodyAction(): String
}

fun setDefaultNullValue(
    keyPath: List<String>,
    value: String,
    node: ObjectNode,
    iter: Int = 0,
    replacedInfo: (detail: String) -> Unit
): ObjectNode {
    return if (node.has(keyPath[iter])) {
        setDefaultNullValue(keyPath, value, node.findValue(keyPath[iter]) as ObjectNode, iter + 1, replacedInfo)
    } else {
        node.put(keyPath.last(), value).also { replacedInfo("$node : $keyPath replaced by $value") }
    }
}

fun List<AbstractIgnoredNullRestriction>.parseNullField(
    node: JsonNode,
    isNodeAction: (node: JsonNode, rule: AbstractIgnoredNullRestriction) -> JsonNode? = { n, r -> node }
): List<ErrorDetail>? {
    val details = mutableListOf<ErrorDetail>()
    forEach { validationIgnored ->
        isNodeAction(node, validationIgnored)
            ?.let {
                val splitKey = validationIgnored.fieldPath.split(validationIgnored.fieldPathSeparator)
                it.findValue(splitKey.last())
                    ?: setDefaultNullValue(
                        keyPath = splitKey,
                        value = validationIgnored.defaultNullValue,
                        node = (it.takeIf { it.isObject } ?: it.first()) as ObjectNode
                    ) { detail ->
                        details.add(ErrorDetail(code = ErrorDetailCode.MISSING_FIELD_REPLACED.value, detail = detail))
                    }
            }
    }
    return details.takeIf { it.size > 0 }
}
