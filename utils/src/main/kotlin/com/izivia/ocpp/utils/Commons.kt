package com.izivia.ocpp.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeCreator
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode

inline fun <reified T : Any> Any.isA(function: (T) -> Any = { it -> it }): Any =
    if (this is T) function(this) else this

interface InterfaceFieldOption {
    val fieldPath: String
    val isRequest: Boolean
    fun getBodyAction(): String
}

enum class TypeConvertEnum {
    STRING,
    SET,
    LIST
}

abstract class AbstractForceConvertField(
    open val typeRequested: TypeConvertEnum,
    override val fieldPath: String,
    override val isRequest: Boolean,
    val fieldPathSeparator: String = "."
) : InterfaceFieldOption

abstract class AbstractIgnoredNullRestriction(
    open val defaultNullValue: String,
    override val fieldPath: String,
    override val isRequest: Boolean,
    val fieldPathSeparator: String = "."
) : InterfaceFieldOption

fun setDefaultNullValue(
    keyPath: List<String>,
    value: String,
    node: ObjectNode,
    replacedInfo: (code: ErrorDetailCode, detail: String) -> Unit
): ObjectNode {
    return if (node.has(keyPath[0])) {
        setDefaultNullValue(
            keyPath = keyPath.subList(1, keyPath.size),
            value = value,
            node = node.findValue(keyPath[0]) as ObjectNode,
            replacedInfo = replacedInfo
        )
    } else {
        node.put(keyPath.last(), value).also {
            replacedInfo(
                ErrorDetailCode.MISSING_FIELD_REPLACED,
                "$node : $keyPath replaced by $value"
            )
        }
    }
}

fun List<AbstractIgnoredNullRestriction>.parseNullField(
    node: JsonNode,
    isNodeAction: (node: JsonNode, rule: AbstractIgnoredNullRestriction) -> JsonNode? = { n, r -> node }
): List<ErrorDetail>? {
    val details = mutableListOf<ErrorDetail>()
    forEach { rule ->
        isNodeAction(node, rule)
            ?.let {
                val splitKey = rule.fieldPath.split(rule.fieldPathSeparator)
                it.findValue(splitKey.last())
                    ?: setDefaultNullValue(
                        keyPath = splitKey,
                        value = rule.defaultNullValue,
                        node = (it.takeIf { it.isObject } ?: it.first()) as ObjectNode
                    ) { code, detail ->
                        details.add(ErrorDetail(code = code.value, detail = detail))
                    }
            }
    }
    return details.takeIf { it.size > 0 }
}

fun convertField(
    keyPath: List<String>,
    node: ObjectNode,
    typeConverted: TypeConvertEnum,
    replacedInfo: (code: ErrorDetailCode, detail: String) -> Unit
): ObjectNode {
    val key = keyPath[0]
    return if (node.has(key) && keyPath.size > 1) {
        convertField(
            keyPath = keyPath.subList(1, keyPath.size),
            node = node.findValue(key) as ObjectNode,
            typeConverted = typeConverted,
            replacedInfo = replacedInfo
        )
    } else if (node.has(key)) {
        val oldValue = node[key]
        node.remove(key)
        return try {
            when (typeConverted) {
                TypeConvertEnum.STRING -> node.put(key, oldValue.toString())
                TypeConvertEnum.SET -> node.putPOJO(key, oldValue.toSet())
                TypeConvertEnum.LIST -> node.putPOJO(key, oldValue.toList())
            }.also {
                replacedInfo(
                    ErrorDetailCode.CONVERT_FIELD_REPLACED,
                    "$node : $keyPath converted to $typeConverted"
                )
            }
        } catch (e: Exception) {
            replacedInfo(
                ErrorDetailCode.CONVERT_FIELD_EXCEPTION,
                "$node : $keyPath cannot be converted to $typeConverted use default String"
            )
            node.put(key, oldValue.toString())
        }
    } else {
        replacedInfo(
            ErrorDetailCode.CONVERT_FIELD_EXCEPTION,
            "$node : $keyPath cannot be found"
        )
        node
    }
}

fun List<AbstractForceConvertField>.parseFieldToConvert(
    node: JsonNode,
    isNodeAction: (node: JsonNode, rule: AbstractForceConvertField) -> JsonNode? = { n, r -> node }
): List<ErrorDetail>? {
    val details = mutableListOf<ErrorDetail>()
    forEach { rule ->
        isNodeAction(node, rule)
            ?.let { jsonNode ->
                val splitKey = rule.fieldPath.split(rule.fieldPathSeparator)
                convertField(
                    keyPath = splitKey,
                    node = jsonNode as ObjectNode,
                    typeConverted = rule.typeRequested,
                ) { code, detail ->
                    details.add(ErrorDetail(code = code.value, detail = detail))
                }
            }
    }
    return details.takeIf { it.size > 0 }
}
