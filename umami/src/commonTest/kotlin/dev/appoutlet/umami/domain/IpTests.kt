package dev.appoutlet.umami.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class IpTests {
    @Test
    fun `should create valid IPv4 address`() {
        val ip = Ip("192.168.2.3")
        ip.value shouldBe "192.168.2.3"
    }

    @Test
    fun `should create valid IPv6 address`() {
        val ip = Ip("2001:0db8:85a3:0000:0000:8a2e:0370:7334")
        ip.value shouldBe "2001:0db8:85a3:0000:0000:8a2e:0370:7334"
    }

    @Test
    fun `should throw exception for invalid IP address`() {
        shouldThrow<IllegalArgumentException> {
            Ip("999.999.999.999")
        }
    }
}
