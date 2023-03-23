package fr.izivia.websocketproxy.wamp

import com.izivia.ocpp.utils.MessageErrorCode
import com.izivia.ocpp.wamp.messages.WampMessageParser
import com.izivia.ocpp.wamp.messages.WampMessageParser.removeQuotesBeforePayloadAndTrim
import com.izivia.ocpp.wamp.messages.WampMessageType
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.io.File
import java.util.*

class WampParserTest {

    @Test
    fun `TreeMap should make connections insensitive`() {
        // GIVEN
        val connections = TreeMap<String, String> { a, b ->
            a.lowercase().compareTo(b.lowercase())
        }

        // WHEN
        connections["key"] = "val"
        connections["keY"] = "val"

        // THEN
        expectThat(connections.size).isEqualTo(1)
        expectThat(connections["key"]).isNotNull()
        expectThat(connections["key"]).isEqualTo("val")
        expectThat(connections["keY"]).isEqualTo("val")
        expectThat(connections["Key"]).isEqualTo("val")
        expectThat(connections["notExistingKey"]).isNull()
    }

    @Test
    fun `should RemoveQuotesBeforePayloadAndTrim`() {
        var s = """[3,"ffd94ceeA8c5bA4b9dA9a1bA21c64276d254",{"status":"Accepted"}]"""
        s = s.removeQuotesBeforePayloadAndTrim()
        expectThat(s).equals("""[3,ffd94ceeA8c5bA4b9dA9a1bA21c64276d254,{"status":"Accepted"}]""")

        s = """[3,"ffd94ceeA8c5bA4b9dA9a1bA21c64276d254","status":"Accepted"]"""
        s = s.removeQuotesBeforePayloadAndTrim()
        expectThat(s).equals("""[3,ffd94ceeA8c5bA4b9dA9a1bA21c64276d254,status:Accepted]""")
    }

    @Test
    fun `should parse wrong typed Msg and return null`() {
        val callTypeMsg =
            """[25,"37e834e6-0698-46da-b6d0-f2b3b3de11d9","StatusNotification",{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}]"""
        var wampMessage = WampMessageParser.parse(callTypeMsg)
        expectThat(wampMessage).isNull()
    }

    @Test
    fun `should parse wrong Msg and return null`() {
        var callTypeMsg = """["2", "*****"]"""
        var wampMessage = WampMessageParser.parse(callTypeMsg)
        expectThat(wampMessage).isNull()

        callTypeMsg = ""
        wampMessage = WampMessageParser.parse(callTypeMsg)
        expectThat(wampMessage).isNull()
    }

    @Test
    fun `should parse call Msg`() {
        val callTypeMsg =
            """[2,"37e834e6-0698-46da-b6d0-f2b3b3de11d9","StatusNotification",{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}]"""
        val callTypeMsgWithoutQuote =
            """[2,37e834e6-0698-46da-b6d0-f2b3b3de11d9,StatusNotification,{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}]"""

        val callTypeMsgWithoutQuoteAndBracet =
            """2,37e834e6-0698-46da-b6d0-f2b3b3de11d9,StatusNotification,{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}"""

        var wampMessage = WampMessageParser.parse(callTypeMsg)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("37e834e6-0698-46da-b6d0-f2b3b3de11d9")
            get { action }.isEqualTo("StatusNotification")
            get {
                payload
            }.isEqualTo(
                """{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}"""
            )
        }

        wampMessage = WampMessageParser.parse(callTypeMsgWithoutQuote)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("37e834e6-0698-46da-b6d0-f2b3b3de11d9")
            get { action }.isEqualTo("StatusNotification")
            get {
                payload
            }.isEqualTo(
                """{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}"""
            )
        }

        wampMessage = WampMessageParser.parse(callTypeMsgWithoutQuoteAndBracet)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("37e834e6-0698-46da-b6d0-f2b3b3de11d9")
            get { action }.isEqualTo("StatusNotification")
            get {
                payload
            }.isEqualTo(
                """{"connectorId":1,"errorCode":"NoError","info":"","status":"Available","timestamp":"2023-03-03T11:38:43.522Z"}"""
            )
        }
    }

    @Test
    fun `should parse call result Msg`() {
        val callResultTypeMsg = """[3,"ffd94ceeA8c5bA4b9dA9a1bA21c64276d254",{"status":"Accepted"}]"""
        val callResultTypeMsgWithoutQuote = """[3,ffd94ceeA8c5bA4b9dA9a1bA21c64276d254,{"status":"Accepted"}]"""

        var wampMessage = WampMessageParser.parse(callResultTypeMsg)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("ffd94ceeA8c5bA4b9dA9a1bA21c64276d254")
            get { msgType }.isEqualTo(WampMessageType.CALL_RESULT)
            get { payload }.isEqualTo("""{"status":"Accepted"}""")
        }

        wampMessage = WampMessageParser.parse(callResultTypeMsgWithoutQuote)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("ffd94ceeA8c5bA4b9dA9a1bA21c64276d254")
            get { msgType }.isEqualTo(WampMessageType.CALL_RESULT)
            get { payload }.isEqualTo("""{"status":"Accepted"}""")
        }
    }

    @Test
    fun `should parse call error Msg`() {
        val callErrorTypeMsg =
            """[4,"11d95d88-5228-4a95-832a-b9e9ff6841c5","GenericError","Generic description",{"status", "ERROR"}]"""
        val callErrorTypeMsgWithoutQuote =
            """[4,11d95d88-5228-4a95-832a-b9e9ff6841c5,GenericError,Generic description,{"status", "ERROR"}]"""

        var wampMessage = WampMessageParser.parse(callErrorTypeMsg)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("11d95d88-5228-4a95-832a-b9e9ff6841c5")
            get { msgType }.isEqualTo(WampMessageType.CALL_ERROR)
            get { errorCode }.isEqualTo(MessageErrorCode.GENERIC_ERROR)
            get { errorDescription }.isEqualTo("Generic description")
            get { payload }.isEqualTo("""{"status", "ERROR"}""")
        }

        wampMessage = WampMessageParser.parse(callErrorTypeMsgWithoutQuote)
        expectThat(wampMessage).isNotNull()
        expectThat(wampMessage!!) {
            get { msgId }.isEqualTo("11d95d88-5228-4a95-832a-b9e9ff6841c5")
            get { msgType }.isEqualTo(WampMessageType.CALL_ERROR)
            get { errorCode }.isEqualTo(MessageErrorCode.GENERIC_ERROR)
            get { errorDescription }.isEqualTo("Generic description")
            get { payload }.isEqualTo("""{"status", "ERROR"}""")
        }
    }

    @Test
    fun `should parse all without error`() {
        val path = "src/test/resources/listeWampMsgTest.txt"
        val file = File(path)
        file.readLines().forEach {
            val wampMessage = WampMessageParser.parse(it)
            expectThat(wampMessage).isNotNull()
        }
    }
}
