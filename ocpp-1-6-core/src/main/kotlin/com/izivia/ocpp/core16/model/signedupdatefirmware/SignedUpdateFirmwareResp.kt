package com.izivia.ocpp.core16.model.signedupdatefirmware

import com.izivia.ocpp.core16.model.Response
import com.izivia.ocpp.core16.model.signedupdatefirmware.enumeration.UpdateFirmwareStatusEnumType

data class SignedUpdateFirmwareResp(
    val status: UpdateFirmwareStatusEnumType
) : Response
