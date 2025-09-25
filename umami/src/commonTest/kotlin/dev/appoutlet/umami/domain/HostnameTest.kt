package dev.appoutlet.umami.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class HostnameTest {
    @Test
    fun `should create an instance with valid hostname`() {
        Hostname("example.com")
    }

    @Test
    fun `should throw exception for hostname longer than 100 characters`() {
        shouldThrow<IllegalArgumentException> {
            val longHostname = "a".repeat(101) + ".com"
            Hostname(longHostname)
        }
    }

    @Test
    fun `toString should return the hostname value`() {
        val hostname = Hostname("example.com")
        hostname.toString() shouldBe "example.com"
    }
}
