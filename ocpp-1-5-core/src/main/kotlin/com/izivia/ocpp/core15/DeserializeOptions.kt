package com.izivia.ocpp.core15

import com.izivia.ocpp.core15.model.common.enumeration.Actions
import com.izivia.ocpp.utils.AbstractForceConvertField
import com.izivia.ocpp.utils.AbstractIgnoredNullRestriction
import com.izivia.ocpp.utils.TypeConvertEnum

data class Ocpp15IgnoredNullRestriction(
    val action: Actions,
    override val isRequest: Boolean,
    override val fieldPath: String,
    override val defaultNullValue: String
) : AbstractIgnoredNullRestriction(isRequest = isRequest, fieldPath = fieldPath, defaultNullValue = defaultNullValue) {
    override fun getBodyAction() =
        if (isRequest) "${action.value}Request" else "${action.value}Response"
}

data class Ocpp15ForceConvertField(
    val action: Actions,
    override val isRequest: Boolean,
    override val fieldPath: String,
    override val typeRequested: TypeConvertEnum
) : AbstractForceConvertField(isRequest = isRequest, fieldPath = fieldPath, typeRequested = typeRequested) {
    override fun getBodyAction() =
        if (isRequest) "${action.value}Request" else "${action.value}Response"
}
