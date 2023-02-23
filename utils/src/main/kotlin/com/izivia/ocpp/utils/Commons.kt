package com.izivia.ocpp.utils

inline fun <reified T : Any> Any.isA(function: (T) -> Any = { it -> it }): Any =
    if (this is T) function(this) else this

