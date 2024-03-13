package com.izivia.ocpp.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ObjectNode

inline fun <reified T : Any> Any.isA(function: (T) -> Any = { it -> it }): Any =
    if (this is T) function(this) else this

interface IActions {
    val value: String
    val classRequest: Class<*>
    val classResponse: Class<*>
    val initiatedBy: OcppInitiator
}

interface InterfaceFieldOption {
    val fieldPath: String
    val actionType: ActionTypeEnum
    val action: IActions
    val fieldPathSeparator: String
    fun getBodyAction(): String
}

enum class OcppInitiator {
    CHARGING_STATION,
    CENTRAL_SYSTEM,
    LOCAL_CONTROLLER,
    ALL
}

enum class TypeConvertEnum {
    STRING,
    SET,
    LIST
}

enum class ActionTypeEnum {
    REQUEST,
    RESPONSE
}

abstract class AbstractForcedFieldType(
    open val typeRequested: TypeConvertEnum,
    override val fieldPath: String,
    override val actionType: ActionTypeEnum,
    override val action: IActions,
    override val fieldPathSeparator: String = "."
) : DefaultImplementation(action)

abstract class AbstractIgnoredNullRestriction(
    open val defaultNullValue: String,
    override val fieldPath: String,
    override val actionType: ActionTypeEnum,
    override val action: IActions,
    override val fieldPathSeparator: String = "."
) : DefaultImplementation(action)

abstract class DefaultImplementation(
    override val action: IActions
) : InterfaceFieldOption {

    override fun getBodyAction(): String =
        when (actionType) {
            ActionTypeEnum.REQUEST -> "${action.value}Request"
            ActionTypeEnum.RESPONSE -> "${action.value}Response"
        }
}

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
        return try {
            when (typeConverted) {
                TypeConvertEnum.STRING -> if (!oldValue.isTextual) {
                    replacedInfo(
                        ErrorDetailCode.CONVERT_FIELD_REPLACED,
                        "$node : $keyPath converted to $typeConverted"
                    )
                    node.put(key, oldValue.toString())
                } else node

                TypeConvertEnum.SET,
                TypeConvertEnum.LIST -> if (!oldValue.isPojo) {
                    replacedInfo(
                        ErrorDetailCode.CONVERT_FIELD_REPLACED,
                        "$node : $keyPath converted to $typeConverted"
                    )
                    node.putPOJO(key, oldValue.toList())
                } else node
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

fun List<AbstractForcedFieldType>.parseFieldToConvert(
    node: JsonNode,
    isNodeAction: (node: JsonNode, rule: AbstractForcedFieldType) -> JsonNode? = { node, _ -> node }
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
