package com.izivia.ocpp.wamp.server.impl

import com.izivia.ocpp.CSOcppId
import com.izivia.ocpp.wamp.messages.WampMessageMetaHeaders

class EventsListeners(
    val onWsConnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    val onWsReconnectHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> },
    val onWsCloseHandler: (CSOcppId, WampMessageMetaHeaders) -> Unit = { _, _ -> }
)
