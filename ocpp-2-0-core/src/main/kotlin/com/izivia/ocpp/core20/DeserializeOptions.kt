package com.izivia.ocpp.core20

import com.izivia.ocpp.core20.model.common.enumeration.Actions
import com.izivia.ocpp.utils.AbstractIgnoredNullRestriction

data class Ocpp20IgnoredNullRestriction(
    val action: Actions,
    override val isRequest: Boolean,
    override val fieldPath: String,
    override val defaultNullValue: String
) : AbstractIgnoredNullRestriction(isRequest = isRequest, fieldPath = fieldPath, defaultNullValue = defaultNullValue) {
    override fun getBodyAction() =
        if (isRequest) "${action.value}Request" else "${action.value}Response"
}
