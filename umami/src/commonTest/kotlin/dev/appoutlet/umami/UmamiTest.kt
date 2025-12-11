package dev.appoutlet.umami

import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.logger.UmamiLogger
import dev.mokkery.mock
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.Url
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UmamiTest {
    @Test
    fun `should create instance - primary constructor`() = runTest {
        val fixtureWebsite = Uuid.random()
        val fixtureUrl = Url("https://appoutlet.dev")
        val fixtureHostName = Hostname("umami-kotlin.appoutlet.dev")
        val fixtureLanguage = Language("pt-BR")
        val fixtureScreenSize = ScreenSize(1920, 1080)
        val fixtureIp = Ip("192.168.1.1")
        val fixtureUserAgent = "custom-user-agent"
        val fixtureEventQueueCapacity = 123
        val fixtureClientEngine = mock<HttpClientEngine>()
        val fixtureLogger = mock<UmamiLogger>()

        val umami = Umami(website = fixtureWebsite) {
            baseUrl = fixtureUrl
            hostname = fixtureHostName
            language = fixtureLanguage
            screenSize = fixtureScreenSize
            ip = fixtureIp
            userAgent = fixtureUserAgent
            eventQueueCapacity = fixtureEventQueueCapacity
            httpClientEngine = fixtureClientEngine
            coroutineScope = this@runTest
            logger = fixtureLogger
        }

        with(umami.options) {
            website shouldBe fixtureWebsite
            baseUrl shouldBe fixtureUrl
            hostname shouldBe fixtureHostName
            language shouldBe fixtureLanguage
            screenSize shouldBe fixtureScreenSize
            ip shouldBe fixtureIp
            userAgent shouldBe fixtureUserAgent
            eventQueueCapacity shouldBe fixtureEventQueueCapacity
            httpClientEngine shouldBe fixtureClientEngine
            coroutineScope shouldBe this@runTest
            logger shouldBe fixtureLogger
        }

        umami.eventQueueJob.cancel()
    }

    @Test
    fun `should create instance - string constructor`() = runTest {
        val fixtureWebsite = "6ac9affa-c62a-4b45-b232-0516ea55029c"
        val fixtureUrl = "https://appoutlet.dev"
        val fixtureHostName = "umami-kotlin.appoutlet.dev"
        val fixtureLanguage = "pt-BR"
        val fixtureScreenSize = "1920x1080"
        val fixtureIp = "192.168.1.1"

        val umami = Umami(website = fixtureWebsite) {
            baseUrl(fixtureUrl)
            hostname(fixtureHostName)
            language(fixtureLanguage)
            screenSize(fixtureScreenSize)
            ip(fixtureIp)
        }

        with(umami.options) {
            website.toString() shouldBe fixtureWebsite
            baseUrl.toString() shouldBe fixtureUrl
            hostname?.value shouldBe fixtureHostName
            language?.value shouldBe fixtureLanguage
            screenSize?.value shouldBe fixtureScreenSize
            ip?.value shouldBe fixtureIp
        }

        umami.eventQueueJob.cancel()
    }
}
