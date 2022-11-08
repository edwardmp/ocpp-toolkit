package com.izivia.ocpp.soap16

import com.izivia.ocpp.core16.model.authorize.AuthorizeReq
import com.izivia.ocpp.core16.model.authorize.AuthorizeResp
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationReq
import com.izivia.ocpp.core16.model.bootnotification.BootNotificationResp
import com.izivia.ocpp.core16.model.bootnotification.enumeration.RegistrationStatus
import com.izivia.ocpp.core16.model.common.IdTagInfo
import com.izivia.ocpp.core16.model.common.MeterValue
import com.izivia.ocpp.core16.model.common.SampledValue
import com.izivia.ocpp.core16.model.common.enumeration.*
import com.izivia.ocpp.core16.model.datatransfer.DataTransferReq
import com.izivia.ocpp.core16.model.datatransfer.DataTransferResp
import com.izivia.ocpp.core16.model.datatransfer.enumeration.DataTransferStatus
import com.izivia.ocpp.core16.model.diagnosticsstatusnotification.DiagnosticsStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationReq
import com.izivia.ocpp.core16.model.firmwarestatusnotification.FirmwareStatusNotificationResp
import com.izivia.ocpp.core16.model.firmwarestatusnotification.enumeration.FirmwareStatus
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatReq
import com.izivia.ocpp.core16.model.heartbeat.HeartbeatResp
import com.izivia.ocpp.core16.model.metervalues.MeterValuesReq
import com.izivia.ocpp.core16.model.metervalues.MeterValuesResp
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionReq
import com.izivia.ocpp.core16.model.starttransaction.StartTransactionResp
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationReq
import com.izivia.ocpp.core16.model.statusnotification.StatusNotificationResp
import com.izivia.ocpp.core16.model.statusnotification.enumeration.ChargePointErrorCode
import com.izivia.ocpp.core16.model.statusnotification.enumeration.ChargePointStatus
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionReq
import com.izivia.ocpp.core16.model.stoptransaction.StopTransactionResp
import com.izivia.ocpp.soap.*
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.*
import java.util.UUID

class ocpp16SoapParserTest {

    companion object {
        private val ocpp16SoapParser = Ocpp16SoapParser()
    }

    @Test
    fun `should parse message to AuthorizeReq`() {
        val message =
            """
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2012/06/"
                                   xmlns:wsa5="http://www.w3.org/2005/08/addressing"
                >
                    <SOAP-ENV:Header>
                        <cs:chargeBoxIdentity>CS1</cs:chargeBoxIdentity>
                        <wsa5:MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</wsa5:MessageID>
                        <wsa5:From>
                            <wsa5:Address>http://:8082/</wsa5:Address>
                        </wsa5:From>
                        <wsa5:ReplyTo>
                            <wsa5:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa5:Address>
                        </wsa5:ReplyTo>
                        <wsa5:To SOAP-ENV:mustUnderstand="true">http://example.fr:80/ocpp/v15s/</wsa5:To>
                        <wsa5:Action SOAP-ENV:mustUnderstand="true">/Authorize</wsa5:Action>
                    </SOAP-ENV:Header>
                    <SOAP-ENV:Body>
                        <cs:authorizeRequest>
                            <cs:idTag>AAAAAAAA</cs:idTag>
                        </cs:authorizeRequest>
                    </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<AuthorizeReq>(message)
        ).and {
            get { messageId }.isEqualTo("urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49")
            get { chargingStationId }.isEqualTo("CS1")
            get { action }.isEqualTo("Authorize")
            get { from }.isEqualTo("http://:8082/")
            get { to }.isEqualTo("http://example.fr:80/ocpp/v15s/")
            get { payload }.and {
                isA<AuthorizeReq>()
                get { idTag }.isEqualTo("AAAAAAAA")
            }
        }
    }

    @Test
    fun `should parse message to BootNotificationReq`() {
        val message =
            """
                <ns0:Envelope xmlns:ns0="http://www.w3.org/2003/05/soap-envelope" xmlns:ns1="http://www.w3.org/2005/08/addressing"
                              xmlns:ns2="urn://Ocpp/Cs/2015/10/">
                    <ns0:Header>
                        <ns1:To>http://example.fr/ocpp/v16s/</ns1:To>
                        <ns1:From>
                            <ns1:Address>http://172.40.94.5:8084/chargePointService/ocpp16soap/</ns1:Address>
                        </ns1:From>
                        <ns2:chargeBoxIdentity>00:90:0B:9C:79:42</ns2:chargeBoxIdentity>
                        <ns1:MessageID>7230</ns1:MessageID>
                        <ns1:Action>/BootNotification</ns1:Action>
                    </ns0:Header>
                    <ns0:Body>
                        <ns2:bootNotificationRequest>
                            <ns2:chargePointVendor>Izivia</ns2:chargePointVendor>
                            <ns2:chargePointModel>LEC-3030A-SPX1</ns2:chargePointModel>
                            <ns2:chargePointSerialNumber>5aa469fd41344fe5a575368cd</ns2:chargePointSerialNumber>
                            <ns2:firmwareVersion>1.5.1.d723fd5</ns2:firmwareVersion>
                        </ns2:bootNotificationRequest>
                    </ns0:Body>
                </ns0:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<BootNotificationReq>(message).payload
        ).and {
            get { chargePointModel }.isEqualTo("LEC-3030A-SPX1")
            get { chargePointVendor }.isEqualTo("Izivia")
            get { chargePointSerialNumber }.isEqualTo("5aa469fd41344fe5a575368cd")
            get { chargeBoxSerialNumber }.isNull()
            get { firmwareVersion }.isEqualTo("1.5.1.d723fd5")
            get { iccid }.isNull()
            get { imsi }.isNull()
            get { meterSerialNumber }.isNull()
            get { meterType }.isNull()
        }
    }

    @Test
    fun `should parse message to DataTransferReq`() {
        val message =
            """
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2012/06/"
                                   xmlns:wsa5="http://www.w3.org/2005/08/addressing"
                >
                    <SOAP-ENV:Header>
                        <cs:chargeBoxIdentity>00:80:F4:42:77:AB</cs:chargeBoxIdentity>
                        <wsa5:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</wsa5:MessageID>
                        <wsa5:From>
                            <wsa5:Address>http://:8082/</wsa5:Address>
                        </wsa5:From>
                        <wsa5:ReplyTo>
                            <wsa5:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa5:Address>
                        </wsa5:ReplyTo>
                        <wsa5:To SOAP-ENV:mustUnderstand="true">http://example.fr:80/ocpp/v15s/</wsa5:To>
                        <wsa5:Action SOAP-ENV:mustUnderstand="true">/DataTransfer</wsa5:Action>
                    </SOAP-ENV:Header>
                    <SOAP-ENV:Body>
                        <cs:dataTransferRequest>
                            <cs:vendorId>Schneider Electric</cs:vendorId>
                            <cs:messageId>Detection loop</cs:messageId>
                            <cs:data>{"connectorId":10,"name":"Vehicle","state":"1","timestamp":"2022-05-17T15:42:03Z:"}</cs:data>
                        </cs:dataTransferRequest>
                    </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<DataTransferReq>(message).payload
        ).and {
            get { vendorId }.isEqualTo("Schneider Electric")
            get { messageId }.isEqualTo("Detection loop")
            get { data }.isEqualTo(
                "{\"connectorId\":10,\"name\":\"Vehicle\",\"state\":\"1\",\"timestamp\":\"2022-05-17T15:42:03Z:\"}"
            )
        }
    }

    @Test
    @Disabled
    fun `should parse message to DiagnosticsStatusNotificationReq`() {
        // TODO
    }

    @Test
    fun `should parse message to FirmwareStatusNotificationReq`() {
        val message =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/"
                               xmlns:wsa5="http://www.w3.org/2005/08/addressing"
                >
                    <soap:Header>
                        <cs:chargeBoxIdentity soap:mustUnderstand="true">IES20210511714911</cs:chargeBoxIdentity>
                        <wsa5:From>
                            <wsa5:Address>http://10.66.9.194:8085</wsa5:Address>
                        </wsa5:From>
                        <wsa5:MessageID>urn:uuid:8fe7ef88-0f2e-47f3-911d-dbd36b32f366</wsa5:MessageID>
                        <wsa5:ReplyTo soap:mustUnderstand="true">
                            <wsa5:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa5:Address>
                        </wsa5:ReplyTo>
                        <wsa5:To soap:mustUnderstand="true">http://example.fr/ocpp/v16s/</wsa5:To>
                        <wsa5:Action soap:mustUnderstand="true">/FirmwareStatusNotification</wsa5:Action>
                    </soap:Header>
                    <soap:Body>
                        <cs:firmwareStatusNotificationRequest>
                            <cs:status>Installed</cs:status>
                        </cs:firmwareStatusNotificationRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<FirmwareStatusNotificationReq>(message).payload
        ).and {
            get { status }.isEqualTo(FirmwareStatus.Installed)
        }
    }

    @Test
    fun `should parse message to HeartbeatReq`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<HeartbeatReq>(message).payload
        )
    }

    @Test
    fun `should parse message to MeterValuesReq`() {
        val message =
            """
                <v:Envelope xmlns:v="http://www.w3.org/2003/05/soap-envelope" xmlns:a="http://www.w3.org/2005/08/addressing"
                            xmlns:ocpp="urn://Ocpp/Cs/2015/10/">
                    <v:Header>
                        <ocpp:chargeBoxIdentity>FR-S35-E35238-003-1</ocpp:chargeBoxIdentity>
                        <a:Action v:mustUnderstand="true">/MeterValues</a:Action>
                        <a:MessageID>urn:uuid:f7523b7f-ccf3-425b-8177-8ab60db19d45</a:MessageID>
                        <a:From>
                            <a:Address>http://10.93.252.243/ocpp</a:Address>
                        </a:From>
                        <a:To v:mustUnderstand="true">http://example.fr/ocpp/v16s</a:To>
                        <a:ReplyTo v:mustUnderstand="true">
                            <a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>
                        </a:ReplyTo>
                    </v:Header>
                    <v:Body>
                        <ocpp:meterValuesRequest>
                            <ocpp:connectorId>1</ocpp:connectorId>
                            <ocpp:transactionId>15917</ocpp:transactionId>
                            <ocpp:meterValue>
                                <ocpp:timestamp>2022-05-17T15:41:19.912Z</ocpp:timestamp>
                                <ocpp:sampledValue>
                                    <ocpp:context>Sample.Periodic</ocpp:context>
                                    <ocpp:location>Inlet</ocpp:location>
                                    <ocpp:measurand>Energy.Active.Import.Register</ocpp:measurand>
                                    <ocpp:unit>Wh</ocpp:unit>
                                    <ocpp:value>15213716</ocpp:value>
                                </ocpp:sampledValue>
                            </ocpp:meterValue>
                        </ocpp:meterValuesRequest>
                    </v:Body>
                </v:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<MeterValuesReq>(message).payload
        ).and {
            get { connectorId }.isEqualTo(1)
            get { transactionId }.isEqualTo(15917)
            get { meterValue }.hasSize(1).first().and {
                get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:19.912Z"))
                get { sampledValue }.hasSize(1).first().and {
                    get { value }.isEqualTo("15213716")
                    get { context }.isEqualTo(ReadingContext.SamplePeriodic)
                    get { format }.isEqualTo(ValueFormat.Raw)
                    get { location }.isEqualTo(Location.Inlet)
                    get { measurand }.isEqualTo(Measurand.EnergyActiveImportRegister)
                    get { phase }.isNull()
                    get { unit }.isEqualTo(UnitOfMeasure.Wh)
                }
            }
        }
    }

    @Test
    fun `should parse message to StartTransactionReq`() {
        val message =
            """
                <v:Envelope xmlns:v="http://www.w3.org/2003/05/soap-envelope" xmlns:a="http://www.w3.org/2005/08/addressing"
                            xmlns:ocpp="urn://Ocpp/Cs/2015/10/">
                    <v:Header>
                        <ocpp:chargeBoxIdentity>CU-35251-001-1</ocpp:chargeBoxIdentity>
                        <a:Action v:mustUnderstand="true">/StartTransaction</a:Action>
                        <a:MessageID>urn:uuid:0aa92653-5cd8-465f-89d0-eca833aec4c2</a:MessageID>
                        <a:From>
                            <a:Address>http://10.93.252.224/ocpp</a:Address>
                        </a:From>
                        <a:To v:mustUnderstand="true">http://example.fr/ocpp/v16s/</a:To>
                        <a:ReplyTo v:mustUnderstand="true">
                            <a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address>
                        </a:ReplyTo>
                    </v:Header>
                    <v:Body>
                        <ocpp:startTransactionRequest>
                            <ocpp:connectorId>1</ocpp:connectorId>
                            <ocpp:idTag>AAAAAAAA</ocpp:idTag>
                            <ocpp:meterStart>18804500</ocpp:meterStart>
                            <ocpp:timestamp>2022-05-17T15:41:58.351Z</ocpp:timestamp>
                        </ocpp:startTransactionRequest>
                    </v:Body>
                </v:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<StartTransactionReq>(message).payload
        ).and {
            get { connectorId }.isEqualTo(1)
            get { idTag }.isEqualTo("AAAAAAAA")
            get { meterStart }.isEqualTo(18804500)
            get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:58.351Z"))
            get { reservationId }.isNull()
        }
    }

    @Test
    fun `should parse message to StatusNotificationReq`() {
        val message =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/"
                               xmlns:wsa5="http://www.w3.org/2005/08/addressing"
                >
                    <soap:Header>
                        <cs:chargeBoxIdentity soap:mustUnderstand="true">IES20210510592612</cs:chargeBoxIdentity>
                        <wsa5:From>
                            <wsa5:Address>http://10.66.5.160:8085</wsa5:Address>
                        </wsa5:From>
                        <wsa5:MessageID>urn:uuid:70c76024-1281-471f-8578-ad51a2a88134</wsa5:MessageID>
                        <wsa5:ReplyTo soap:mustUnderstand="true">
                            <wsa5:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa5:Address>
                        </wsa5:ReplyTo>
                        <wsa5:To soap:mustUnderstand="true">http://example.fr/ocpp/v16s</wsa5:To>
                        <wsa5:Action soap:mustUnderstand="true">/StatusNotification</wsa5:Action>
                    </soap:Header>
                    <soap:Body>
                        <cs:statusNotificationRequest>
                            <cs:connectorId>1</cs:connectorId>
                            <cs:status>Available</cs:status>
                            <cs:errorCode>NoError</cs:errorCode>
                            <cs:info>No error.</cs:info>
                            <cs:timestamp>2022-05-17T15:41:59.486Z</cs:timestamp>
                            <cs:vendorErrorCode>0x0</cs:vendorErrorCode>
                        </cs:statusNotificationRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<StatusNotificationReq>(message).payload
        ).and {
            get { connectorId }.isEqualTo(1)
            get { errorCode }.isEqualTo(ChargePointErrorCode.NoError)
            get { status }.isEqualTo(ChargePointStatus.Available)
            get { info }.isEqualTo("No error.")
            get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:59.486Z"))
            get { vendorErrorCode }.isEqualTo("0x0")
            get { vendorId }.isNull()
        }
    }

    @Test
    fun `should parse message to StopTransactionReq`() {
        val message =
            """
                <SOAP-ENV:Envelope xmlns:SOAP-ENV="http://www.w3.org/2003/05/soap-envelope">
                    <SOAP-ENV:Header>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/StopTransaction</Action>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">urn:uuid:3b30544e-eafe-4fba-af6e-5ae00612f788
                        </MessageID>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v15s/</To>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <From xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://192.168.0.102:8080</Address>
                        </From>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2012/06/">CS1</chargeBoxIdentity>
                    </SOAP-ENV:Header>
                    <SOAP-ENV:Body>
                        <stopTransactionRequest xmlns="urn://Ocpp/Cs/2012/06/">
                            <transactionId>16696</transactionId>
                            <idTag>AAAAAAAA</idTag>
                            <timestamp>2022-05-05T04:37:15Z</timestamp>
                            <meterStop>19224</meterStop>
                        </stopTransactionRequest>
                    </SOAP-ENV:Body>
                </SOAP-ENV:Envelope>
            """.trimSoap()

        expectThat(
            ocpp16SoapParser
                .parseRequestFromSoap<StopTransactionReq>(message).payload
        ).and {
            get { meterStop }.isEqualTo(19224)
            get { timestamp }.isEqualTo(Instant.parse("2022-05-05T04:37:15Z"))
            get { transactionId }.isEqualTo(16696)
            get { idTag }.isEqualTo("AAAAAAAA")
            get { reason }.isNull()
            get { transactionData }.isNull()
        }
    }

    @Test
    fun `should not parse message to AuthorizeReq because it is not an Authorize`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<AuthorizeReq>(message)
        }
    }

    @Test
    fun `should not parse message to BootNotificationReq because it is not a BootNotification`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<BootNotificationReq>(message)
        }
    }

    @Test
    fun `should not parse message to DataTransferReq because it is not a DataTransfer`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<DataTransferReq>(message)
        }
    }

    @Test
    fun `should not parse message to DiagnosticsStatusNotificationReq because it is not a DiagnosticsStatusNotification`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<DiagnosticsStatusNotificationReq>(message)
        }
    }

    @Test
    fun `should not parse message to FirmwareStatusNotificationReq because it is not a FirmwareStatusNotification`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<FirmwareStatusNotificationReq>(message)
        }
    }

    @Test
    fun `should not parse message to HeartbeatReq because it is not an Heartbeat`() {
        val message =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/"
                   xmlns:wsa5="http://www.w3.org/2005/08/addressing"
                >
                    <soap:Header>
                        <cs:chargeBoxIdentity soap:mustUnderstand="true">IES20210511714911</cs:chargeBoxIdentity>
                        <wsa5:From>
                            <wsa5:Address>http://10.66.9.194:8085</wsa5:Address>
                        </wsa5:From>
                        <wsa5:MessageID>urn:uuid:8fe7ef88-0f2e-47f3-911d-dbd36b32f366</wsa5:MessageID>
                        <wsa5:ReplyTo soap:mustUnderstand="true">
                            <wsa5:Address>http://www.w3.org/2005/08/addressing/anonymous</wsa5:Address>
                        </wsa5:ReplyTo>
                        <wsa5:To soap:mustUnderstand="true">http://example.fr/ocpp/v16s/</wsa5:To>
                        <wsa5:Action soap:mustUnderstand="true">/FirmwareStatusNotification</wsa5:Action>
                    </soap:Header>
                    <soap:Body>
                        <cs:firmwareStatusNotificationRequest>
                            <cs:status>Installed</cs:status>
                        </cs:firmwareStatusNotificationRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<HeartbeatReq>(message)
        }
    }

    @Test
    fun `should not parse message to MeterValuesReq because it is not a MeterValues`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<MeterValuesReq>(message)
        }
    }

    @Test
    fun `should not parse message to StartTransactionReq because it is not a StartTransaction`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<StartTransactionReq>(message)
        }
    }

    @Test
    fun `should not parse message to StatusNotificationReq because it is not a StatusNotification`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<StatusNotificationReq>(message)
        }
    }

    @Test
    fun `should not parse message to StopTransactionReq because it is not a StopTransaction`() {
        val message =
            """
                <S:Envelope xmlns:S="http://www.w3.org/2003/05/soap-envelope">
                    <S:Header>
                        <chargeBoxIdentity xmlns="urn://Ocpp/Cs/2015/10/">CS1</chargeBoxIdentity>
                        <wsa:From xmlns:wsa="http://www.w3.org/2005/08/addressing">
                            <wsa:Address>http://192.168.0.3:12800/ws</wsa:Address>
                        </wsa:From>
                        <To xmlns="http://www.w3.org/2005/08/addressing">http://example.fr/ocpp/v16s/</To>
                        <Action xmlns="http://www.w3.org/2005/08/addressing">/Heartbeat</Action>
                        <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </FaultTo>
                        <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:b70e2eae-bd09-4546-b049-8b2be7ccf578</MessageID>
                    </S:Header>
                    <S:Body>
                        <heartbeatRequest xmlns="urn://Ocpp/Cs/2015/10/"/>
                    </S:Body>
                </S:Envelope>
            """.trimSoap()

        expectThrows<IllegalArgumentException> {
            ocpp16SoapParser
                .parseRequestFromSoap<StopTransactionReq>(message)
        }
    }

    @Test
    fun `should map AuthorizeReq to soap`() {
        val msgId = UUID.randomUUID().toString()
        val request = AuthorizeReq(
            idTag = "AAAAAAAA"
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = msgId,
                    chargingStationId = "CS1",
                    action = "Authorize",
                    from = "source",
                    to = "destination",
                    payload = request
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>$msgId</a:MessageID>
<a:Action>/Authorize</a:Action><o:chargeBoxIdentity>CS1</o:chargeBoxIdentity>
<a:From><a:Address>source</a:Address></a:From><a:To>destination</a:To></s:Header>
<s:Body><o:authorizeRequest><o:idTag>AAAAAAAA</o:idTag></o:authorizeRequest></s:Body></s:Envelope>""".trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }
                .isA<AuthorizeReq>()
                .and {
                    get { idTag }.isEqualTo("AAAAAAAA")
                }
        }
    }

    @Test
    fun `should map AuthorizeResp to soap`() {
        val response = AuthorizeResp(
            idTagInfo = IdTagInfo(
                status = AuthorizationStatus.Accepted,
                expiryDate = Instant.parse("2022-05-16T15:42:05.128Z")
            )
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "Authorize",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>
<a:Action>/AuthorizeResponse</a:Action>
<a:From><a:Address>source</a:Address></a:From><a:To>destination</a:To>
<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>
</s:Header><s:Body><o:authorizeResponse><o:idTagInfo><status>Accepted</status>
<expiryDate>2022-05-16T15:42:05.128Z</expiryDate></o:idTagInfo></o:authorizeResponse></s:Body></s:Envelope>"""
                .trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<AuthorizeResp>().and {
                get { idTagInfo }.and {
                    get { status }.isEqualTo(AuthorizationStatus.Accepted)
                    get { expiryDate }.isEqualTo(Instant.parse("2022-05-16T15:42:05.128Z"))
                }
            }
        }
    }

    @Test
    fun `should map BootNotificationReq to soap`() {
        val request = BootNotificationReq(
            chargePointVendor = "Izivia",
            chargePointModel = "LEC-3030A-SPX1",
            chargePointSerialNumber = "5aa469fd41344fe5a575368cd",
            firmwareVersion = "1.5.1.d723fd5"
        )

        val messageSoap = ocpp16SoapParser.mapRequestToSoap(
            RequestSoapMessage(
                messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                chargingStationId = "CS1",
                action = "BootNotification",
                from = "source",
                to = "destination",
                payload = request
            )
        )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                 xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:MessageID>
<a:Action>/BootNotification</a:Action><o:chargeBoxIdentity>CS1</o:chargeBoxIdentity>
<a:From><a:Address>source</a:Address></a:From><a:To>destination</a:To></s:Header>
<s:Body><o:bootNotificationRequest>
<o:chargeBoxSerialNumber>LEC-3030A-SPX1</o:chargeBoxSerialNumber>
<o:chargePointModel>Izivia</o:chargePointModel>
<o:chargePointSerialNumber>5aa469fd41344fe5a575368cd</o:chargePointSerialNumber>
<o:firmwareVersion>1.5.1.d723fd5</o:firmwareVersion>
<o:chargePointVendor>Izivia</o:chargePointVendor>
</o:bootNotificationRequest></s:Body></s:Envelope>""".trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<BootNotificationReq>().and {
                get { chargePointVendor }.isEqualTo("Izivia")
                get { chargePointModel }.isEqualTo("LEC-3030A-SPX1")
                get { chargePointSerialNumber }.isEqualTo("5aa469fd41344fe5a575368cd")
                get { firmwareVersion }.isEqualTo("1.5.1.d723fd5")
            }
        }
    }

    @Test
    fun `should map BootNotificationResp to soap`() {
        val response = BootNotificationResp(
            status = RegistrationStatus.Rejected,
            currentTime = Instant.parse("2022-05-17T15:43:08.025Z"),
            interval = 1800
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "BootNotification",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains("<a:Action>/BootNotificationResponse</a:Action>")
            get { this }.contains("<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>")
            get { this }.contains("<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>")
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<BootNotificationResp>().and {
                get { status }.isEqualTo(RegistrationStatus.Rejected)
                get { currentTime }.isEqualTo(Instant.parse("2022-05-17T15:43:08.025Z"))
                get { interval }.isEqualTo(1800)
            }
        }
    }

    @Test
    fun `should map DataTransferReq to soap`() {
        val request = DataTransferReq(
            vendorId = "Schneider Electric",
            messageId = "Detection loop",
            data = "{\"connectorId\":10,\"name\":\"Vehicle\",\"state\":\"1\",\"timestamp\":\"2022-05-17T15:42:03Z:\"}"
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "DataTransfer",
                    from = "source",
                    to = "destination",
                    payload = request
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                 xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:MessageID><a:Action>/DataTransfer</a:Action>
<o:chargeBoxIdentity>CS1</o:chargeBoxIdentity><a:From><a:Address>source</a:Address></a:From>
<a:To>destination</a:To></s:Header><s:Body><o:dataTransferRequest><o:vendorId>Schneider Electric</o:vendorId>
<o:messageId>Detection loop</o:messageId>
<o:data>{"connectorId":10,"name":"Vehicle","state":"1","timestamp":"2022-05-17T15:42:03Z:"}</o:data>
</o:dataTransferRequest></s:Body></s:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }
                .isA<DataTransferReq>().and {
                    get { vendorId }.isEqualTo("Schneider Electric")
                    get { messageId }.isEqualTo("Detection loop")
                    get { data }
                        .isEqualTo(
                            "{\"connectorId\":10,\"name\":\"Vehicle\",\"state\":\"1\"," +
                                "\"timestamp\":\"2022-05-17T15:42:03Z:\"}"
                        )
                }
        }
    }

    @Test
    fun `should map DataTransferResp to soap`() {
        val response = DataTransferResp(
            status = DataTransferStatus.Accepted
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "DataTransfer",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/DataTransferResponse</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<DataTransferResp>().and {
                get { status }.isEqualTo(DataTransferStatus.Accepted)
            }
        }
    }

    @Disabled
    @Test
    fun `should map DiagnosticsStatusNotificationReq to soap`() {
        // TODO
    }

    @Disabled
    @Test
    fun `should map DiagnosticsStatusNotificationResp to soap`() {
        // TODO
    }

    @Test
    fun `should map FirmwareStatusNotificationReq to soap`() {
        val response = FirmwareStatusNotificationReq(
            status = FirmwareStatus.Installed
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "FirmwareStatusNotification",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:MessageID>
<a:Action>/FirmwareStatusNotification</a:Action><o:chargeBoxIdentity>CS1</o:chargeBoxIdentity>
<a:From><a:Address>source</a:Address></a:From><a:To>destination</a:To></s:Header>
<s:Body><o:firmwareStatusNotificationRequest><o:status>Installed</o:status></o:firmwareStatusNotificationRequest>
</s:Body></s:Envelope>""".trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<FirmwareStatusNotificationReq>().and {
                get { status }.isEqualTo(FirmwareStatus.Installed)
            }
        }
    }

    @Test
    fun `should map FirmwareStatusNotificationResp to soap`() {
        val response = FirmwareStatusNotificationResp()

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "FirmwareStatusNotification",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                 xmlns:a="http://www.w3.org/2005/08/addressing"
                 xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>
<a:Action>/FirmwareStatusNotificationResponse</a:Action>
<a:From><a:Address>source</a:Address></a:From><a:To>destination</a:To>
<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>
</s:Header><s:Body><o:firmwareStatusNotificationResponse/></s:Body></s:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<FirmwareStatusNotificationResp>()
        }
    }

    @Test
    fun `should map HeartbeatReq to soap`() {
        val response = HeartbeatReq()

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "Heartbeat",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """<s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope"
                xmlns:a="http://www.w3.org/2005/08/addressing"
                xmlns:o="urn://Ocpp/Cp/2015/10/"><s:Header>
<a:MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:MessageID>
<a:Action>/Heartbeat</a:Action><o:chargeBoxIdentity>CS1</o:chargeBoxIdentity><a:From>
<a:Address>source</a:Address></a:From><a:To>destination</a:To></s:Header><s:Body><o:heartbeatRequest/>
</s:Body></s:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<HeartbeatReq>()
        }
    }

    @Test
    fun `should map HeartbeatResp to soap`() {
        val response = HeartbeatResp(
            currentTime = Instant.parse("2022-05-17T15:42:00.503Z")
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "Heartbeat",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains("<a:Action>/HeartbeatResponse</a:Action>")
            get { this }.contains(
                "<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<HeartbeatResp>().and {
                get { currentTime }.isEqualTo(Instant.parse("2022-05-17T15:42:00.503Z"))
            }
        }
    }

    @Test
    fun `should map MeterValuesReq to soap`() {
        val response = MeterValuesReq(
            connectorId = 1,
            transactionId = 15917,
            meterValue = listOf(
                MeterValue(
                    timestamp = Instant.parse("2022-05-17T15:41:19.912Z"),
                    sampledValue = listOf(
                        SampledValue(
                            context = ReadingContext.SamplePeriodic,
                            location = Location.Inlet,
                            measurand = Measurand.EnergyActiveImportRegister,
                            unit = UnitOfMeasure.Wh,
                            value = "15213716"
                        )
                    )
                )
            )
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "MeterValues",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/" xmlns:wsa5="http://www.w3.org/2005/08/addressing">
                    <soap:Header>
                        <chargeBoxIdentity soap:mustUnderstand="true">CS1</chargeBoxIdentity>
                        <MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</MessageID>
                        <From>
                            <Address>source</Address>
                        </From>
                        <ReplyTo soap:mustUnderstand="true">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <To soap:mustUnderstand="true">destination</To>
                        <Action soap:mustUnderstand="true">/MeterValues</Action>
                    </soap:Header>
                    <soap:Body>
                        <meterValuesRequest>
                            <connectorId>1</connectorId>
                            <meterValue>
                                <sampledValue>
                                    <value>15213716</value>
                                    <context>Sample.Periodic</context>
                                    <format>Raw</format>
                                    <location>Inlet</location>
                                    <measurand>Energy.Active.Import.Register</measurand>
                                    <unit>Wh</unit>
                                </sampledValue>
                                <timestamp>2022-05-17T15:41:19.912Z</timestamp>
                            </meterValue>
                            <transactionId>15917</transactionId>
                        </meterValuesRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<MeterValuesReq>().and {
                get { connectorId }.isEqualTo(1)
                get { transactionId }.isEqualTo(15917)
                get { meterValue }.hasSize(1).first().and {
                    get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:19.912Z"))
                    get { sampledValue }.hasSize(1).first().and {
                        get { context }.isEqualTo(ReadingContext.SamplePeriodic)
                        get { location }.isEqualTo(Location.Inlet)
                        get { measurand }.isEqualTo(Measurand.EnergyActiveImportRegister)
                        get { unit }.isEqualTo(UnitOfMeasure.Wh)
                        get { value }.isEqualTo("15213716")
                    }
                }
            }
        }
    }

    @Test
    fun `should map MeterValuesResp to soap`() {
        val response = MeterValuesResp()

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "MeterValues",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/MeterValuesResponse</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<MeterValuesResp>()
        }
    }

    @Test
    fun `should map StartTransactionReq to soap`() {
        val response = StartTransactionReq(
            connectorId = 1,
            idTag = "AAAAAAAA",
            meterStart = 18804500,
            timestamp = Instant.parse("2022-05-17T15:41:58.351Z")
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "StartTransaction",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/" xmlns:wsa5="http://www.w3.org/2005/08/addressing">
                    <soap:Header>
                        <chargeBoxIdentity soap:mustUnderstand="true">CS1</chargeBoxIdentity>
                        <MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</MessageID>
                        <From>
                            <Address>source</Address>
                        </From>
                        <ReplyTo soap:mustUnderstand="true">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <To soap:mustUnderstand="true">destination</To>
                        <Action soap:mustUnderstand="true">/StartTransaction</Action>
                    </soap:Header>
                    <soap:Body>
                        <startTransactionRequest>
                            <connectorId>1</connectorId>
                            <idTag>AAAAAAAA</idTag>
                            <meterStart>18804500</meterStart>
                            <timestamp>2022-05-17T15:41:58.351Z</timestamp>
                        </startTransactionRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<StartTransactionReq>().and {
                get { connectorId }.isEqualTo(1)
                get { idTag }.isEqualTo("AAAAAAAA")
                get { meterStart }.isEqualTo(18804500)
                get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:58.351Z"))
            }
        }
    }

    @Test
    fun `should map StartTransactionResp to soap`() {
        val response = StartTransactionResp(
            transactionId = 15671,
            idTagInfo = IdTagInfo(
                status = AuthorizationStatus.Accepted,
                expiryDate = Instant.parse("2022-05-16T15:42:02.617Z")
            )
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = "urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b",
                    relatesTo = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    action = "StartTransaction",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/StartTransactionResponse</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>urn:uuid:739faeb1-da7c-4a50-8b61-2f631057fc2b</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<StartTransactionResp>().and {
                get { transactionId }.isEqualTo(15671)
                get { idTagInfo }.and {
                    get { status }.isEqualTo(AuthorizationStatus.Accepted)
                    get { expiryDate }.isEqualTo(Instant.parse("2022-05-16T15:42:02.617Z"))
                }
            }
        }
    }

    @Test
    fun `should map StatusNotificationReq to soap`() {
        val response = StatusNotificationReq(
            connectorId = 1,
            status = ChargePointStatus.Available,
            errorCode = ChargePointErrorCode.NoError,
            info = "No error.",
            timestamp = Instant.parse("2022-05-17T15:41:59.486Z"),
            vendorErrorCode = "0x0"
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "StatusNotification",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/" xmlns:wsa5="http://www.w3.org/2005/08/addressing">
                    <soap:Header>
                        <chargeBoxIdentity soap:mustUnderstand="true">CS1</chargeBoxIdentity>
                        <MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</MessageID>
                        <From>
                            <Address>source</Address>
                        </From>
                        <ReplyTo soap:mustUnderstand="true">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <To soap:mustUnderstand="true">destination</To>
                        <Action soap:mustUnderstand="true">/StatusNotification</Action>
                    </soap:Header>
                    <soap:Body>
                        <statusNotificationRequest>
                            <connectorId>1</connectorId>
                            <errorCode>NoError</errorCode>
                            <status>Available</status>
                            <info>No error.</info>
                            <timestamp>2022-05-17T15:41:59.486Z</timestamp>
                            <vendorErrorCode>0x0</vendorErrorCode>
                        </statusNotificationRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<StatusNotificationReq>().and {
                get { connectorId }.isEqualTo(1)
                get { status }.isEqualTo(ChargePointStatus.Available)
                get { errorCode }.isEqualTo(ChargePointErrorCode.NoError)
                get { info }.isEqualTo("No error.")
                get { timestamp }.isEqualTo(Instant.parse("2022-05-17T15:41:59.486Z"))
                get { vendorErrorCode }.isEqualTo("0x0")
            }
        }
    }

    @Test
    fun `should map StatusNotificationResp to soap`() {
        val response = StatusNotificationResp()
        val messageId = UUID.randomUUID().toString()
        val relatesTo = UUID.randomUUID().toString()
        val action = "StatusNotification"

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = messageId,
                    relatesTo = relatesTo,
                    action = action,
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/${action}Response</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>$messageId</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>$relatesTo</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<StatusNotificationResp>()
        }
    }

    @Test
    fun `should map StopTransactionReq to soap`() {
        val response = StopTransactionReq(
            transactionId = 16696,
            idTag = "AAAAAAAA",
            timestamp = Instant.parse("2022-05-05T04:37:15Z"),
            meterStop = 19224
        )

        val messageSoap = ocpp16SoapParser
            .mapRequestToSoap(
                RequestSoapMessage(
                    messageId = "urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49",
                    chargingStationId = "CS1",
                    action = "StopTransaction",
                    from = "source",
                    to = "destination",
                    payload = response
                )
            )

        val expectedEnvelope =
            """
                <soap:Envelope xmlns:soap="http://www.w3.org/2003/05/soap-envelope" xmlns:cs="urn://Ocpp/Cs/2015/10/" xmlns:wsa5="http://www.w3.org/2005/08/addressing">
                    <soap:Header>
                        <chargeBoxIdentity soap:mustUnderstand="true">CS1</chargeBoxIdentity>
                        <MessageID>urn:uuid:a7ef37c1-2ac6-4247-a3ad-8ed5905a5b49</MessageID>
                        <From>
                            <Address>source</Address>
                        </From>
                        <ReplyTo soap:mustUnderstand="true">
                            <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
                        </ReplyTo>
                        <To soap:mustUnderstand="true">destination</To>
                        <Action soap:mustUnderstand="true">/StopTransaction</Action>
                    </soap:Header>
                    <soap:Body>
                        <stopTransactionRequest>
                            <meterStop>19224</meterStop>
                            <timestamp>2022-05-05T04:37:15Z</timestamp>
                            <transactionId>16696</transactionId>
                            <idTag>AAAAAAAA</idTag>
                        </stopTransactionRequest>
                    </soap:Body>
                </soap:Envelope>
            """.trimSoap()

        expectThat(messageSoap).isEqualTo(expectedEnvelope)
        expectThat(ocpp16SoapParser.parseAnyRequestFromSoap(messageSoap)) {
            get { payload }.isA<StopTransactionReq>().and {
                get { transactionId }.isEqualTo(16696)
                get { idTag }.isEqualTo("AAAAAAAA")
                get { timestamp }.isEqualTo(Instant.parse("2022-05-05T04:37:15Z"))
                get { meterStop }.isEqualTo(19224)
            }
        }
    }

    @Test
    fun `should map StopTransactionResp to soap`() {
        val messageId = UUID.randomUUID().toString()
        val relatesTo = UUID.randomUUID().toString()
        val action = "StopTransaction"

        val response = StopTransactionResp(
            idTagInfo = IdTagInfo(
                status = AuthorizationStatus.Accepted,
                expiryDate = Instant.parse("2022-05-16T15:42:05.128Z")
            )
        )

        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = messageId,
                    relatesTo = relatesTo,
                    action = "StopTransaction",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/${action}Response</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>$messageId</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>$relatesTo</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<StopTransactionResp>().and {
                get { idTagInfo }.isNotNull().and {
                    get { status }.isEqualTo(AuthorizationStatus.Accepted)
                    get { expiryDate }.isEqualTo(Instant.parse("2022-05-16T15:42:05.128Z"))
                }
            }
        }
    }

    @Test
    fun `should map SoapFault to soap`() {
        val messageId = UUID.randomUUID().toString()
        val relatesTo = UUID.randomUUID().toString()
        val action = "StopTransaction"

        val response = SoapFault.internalError()
        val messageSoap = ocpp16SoapParser
            .mapResponseToSoap(
                ResponseSoapMessage(
                    messageId = messageId,
                    relatesTo = relatesTo,
                    action = "StopTransaction",
                    payload = response,
                    from = "source",
                    to = "destination"
                )
            )

        expectThat(messageSoap) {
            get { this }.contains(
                "<a:Action>/${action}Response</a:Action>"
            )
            get { this }.contains(
                "<a:MessageID>$messageId</a:MessageID>"
            )
            get { this }.contains(
                "<a:RelatesTo>$relatesTo</a:RelatesTo>"
            )
        }
        expectThat(ocpp16SoapParser.parseAnyResponseFromSoap(messageSoap)) {
            get { payload }.isA<SoapFault>().and {
                get { code }.and {
                    get { value.value }.isEqualTo("Receiver")
                    get { subCode.value.value }.isEqualTo("InternalError")
                }
                get { reason.text.value.value }.isEqualTo(
                    "An internal error occurred and the receiver is not able to complete the operation."
                )
            }
        }
    }
}

private fun String.trimSoap(): String =
    this.trimIndent()
        .replace("\n", "")
        .replace("\\s+".toRegex(), " ")
