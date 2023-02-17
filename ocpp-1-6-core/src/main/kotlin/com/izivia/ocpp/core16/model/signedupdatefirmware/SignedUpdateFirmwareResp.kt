package com.izivia.ocpp.core16.model.updatefirmware

import com.izivia.ocpp.core16.model.signedupdatefirmware.enumeration.UpdateFirmwareStatusEnumType

data class SignedUpdateFirmwareResp(
    val status: UpdateFirmwareStatusEnumType
)
