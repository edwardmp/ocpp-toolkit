package com.izivia.ocpp.utils

import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.datetime.Instant
import org.junit.jupiter.api.Test
import strikt.api.DescribeableBuilder
import strikt.api.expectThat
import strikt.assertions.isEqualTo

class KotlinxInstantModuleTest {
    val mapper = ObjectMapper().registerModule(KotlinxInstantModule())

    @Test
    fun `should truncation timestamps to millis`() {
        expectThat("2023-10-06T12:33Z").serializesWithMapperTo("2023-10-06T12:33:00Z")
        expectThat("2023-10-06T12:33:34Z").serializesWithMapperTo("2023-10-06T12:33:34Z")
        expectThat("2023-10-06T12:33:34.123Z").serializesWithMapperTo("2023-10-06T12:33:34.123Z")
        expectThat("2023-10-06T12:33:34.12Z").serializesWithMapperTo("2023-10-06T12:33:34.120Z")
        expectThat("2023-10-06T12:33:34.123456Z").serializesWithMapperTo("2023-10-06T12:33:34.123Z")
        expectThat("2023-10-06T12:33:34.123456789Z").serializesWithMapperTo("2023-10-06T12:33:34.123Z")
        expectThat("2023-10-06T12:33:34.123000Z").serializesWithMapperTo("2023-10-06T12:33:34.123Z")
        expectThat("2023-10-06T12:33:34.123000123Z").serializesWithMapperTo("2023-10-06T12:33:34.123Z")
    }

    private fun DescribeableBuilder<String>.serializesWithMapperTo(expected: String) {
        expectThat(mapper.writeValueAsString(Instant.parse(this.subject))).isEqualTo('"' + expected + '"')
    }
}
