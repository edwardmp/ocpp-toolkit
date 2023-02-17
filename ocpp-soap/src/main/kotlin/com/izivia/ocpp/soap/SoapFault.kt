package com.izivia.ocpp.soap

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText
import com.izivia.ocpp.soap.FaultCodeValue.RECEIVER
import com.izivia.ocpp.soap.FaultCodeValue.SENDER
import com.izivia.ocpp.soap.FaultSubCodeValue.*

@JsonRootName("Fault")
data class SoapFault(
    @JacksonXmlProperty(localName = "Code")
    val code: FaultCode,
    @JacksonXmlProperty(localName = "Reason")
    val reason: FaultReason,
    @JacksonXmlProperty(localName = "Value")
    val value: FaultValue? = null
) {
    companion object {
        fun securityError() = create(SENDER, SECURITY_ERROR, FaultReasonTextValue.SECURITY_ERROR)
        fun identityMismatch() = create(SENDER, IDENTITY_MISMATCH, FaultReasonTextValue.IDENTITY_MISMATCH)
        fun protocolError() = create(SENDER, PROTOCOL_ERROR, FaultReasonTextValue.PROTOCOL_ERROR)
        fun internalError() = create(RECEIVER, INTERNAL_ERROR, FaultReasonTextValue.INTERNAL_ERROR)
        fun notSupported() = create(RECEIVER, NOT_SUPPORTED, FaultReasonTextValue.NOT_SUPPORTED)

        private fun create(codeValue: FaultCodeValue, subCode: FaultSubCodeValue, reason: FaultReasonTextValue) =
            SoapFault(
                code = FaultCode(
                    value = codeValue,
                    subCode = FaultSubCode(
                        value = subCode
                    )
                ),
                reason = FaultReason(
                    text = FaultReasonText(
                        value = reason
                    )
                )
            )
    }
}

data class FaultValue(
    @JacksonXmlProperty(localName = "errorDescription")
    val errorDescription: ValueText,
    @JacksonXmlProperty(localName = "errorDetails")
    val errorDetails: Map<ValueText, ValueText>
)

data class FaultCode(
    @JacksonXmlProperty(localName = "Value")
    val value: FaultCodeValue,
    @JacksonXmlProperty(localName = "Subcode")
    val subCode: FaultSubCode
)

enum class FaultCodeValue(@JsonValue val value: String) {
    SENDER("Sender"),
    RECEIVER("Receiver")
}

data class FaultSubCode(
    @JacksonXmlProperty(localName = "Value")
    val value: FaultSubCodeValue
)

enum class FaultSubCodeValue(
    @JsonValue
    @JacksonXmlProperty(localName = "Value")
    val value: String
) {
    SECURITY_ERROR("SecurityError"),
    IDENTITY_MISMATCH("IdentityMismatch"),
    PROTOCOL_ERROR("ProtocolError"),
    INTERNAL_ERROR("InternalError"),
    NOT_SUPPORTED("NotSupported")
}

data class FaultReason(
    @JacksonXmlProperty(localName = "Text")
    val text: FaultReasonText
)

data class FaultReasonText(
    @JsonProperty("text")
    @JacksonXmlText
    val value: FaultReasonTextValue,
    @JacksonXmlProperty(localName = "lang", isAttribute = true)
    val lang: String = "en"
)

enum class FaultReasonTextValue(
    @JsonValue
    @JacksonXmlProperty(localName = "Value")
    val value: String
) {
    SECURITY_ERROR("Sender failed authentication or is not authorized to use the requested operation."),
    IDENTITY_MISMATCH("Sender sent the wrong identity value."),
    PROTOCOL_ERROR("Sender's message does not comply with protocol specification."),
    INTERNAL_ERROR("An internal error occurred and the receiver is not able to complete the operation."),
    NOT_SUPPORTED("The receiver does not support the requested operation."),
}
