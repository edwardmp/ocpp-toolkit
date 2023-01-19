package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DatabindContext
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase

class BaseResolver : TypeIdResolverBase() {
    override fun init(baseType: JavaType?) {
    }

    override fun idFromValue(value: Any): String =
        value::class.simpleName!!
            .let { name ->
                when {
                    name.endsWith("Resp") -> "${name.replaceFirstChar { it.lowercase() }}onse"
                    name.endsWith("Req") -> "${name.replaceFirstChar { it.lowercase() }}uest"
                    name == "SoapFault" -> return "s:Fault"
                    else -> name
                }
            }
            .let { "o:$it" }

    override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String {
        return idFromValue(value = value)
    }

    override fun typeFromId(context: DatabindContext?, id: String?): JavaType? {
        TODO("Get context " + context + " id " + id)
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        TODO("Not yet implemented")
    }
}
