package com.izivia.ocpp.core15

import com.izivia.ocpp.core15.model.common.enumeration.Actions
import com.izivia.ocpp.utils.AbstractIgnoredNullRestriction

data class Ocpp15IgnoredNullRestriction(
    val action: Actions,
    override val isRequest: Boolean,
    override val fieldPath: String,
    override val defaultNullValue: String
) : AbstractIgnoredNullRestriction(isRequest = isRequest, fieldPath = fieldPath, defaultNullValue = defaultNullValue) {
    override fun getBodyAction() =
        if (isRequest) "${action.value}Request" else "${action.value}Response"
}
