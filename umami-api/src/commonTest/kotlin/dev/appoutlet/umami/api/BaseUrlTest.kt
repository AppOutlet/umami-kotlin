package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Session
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.getUmamiApiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class BaseUrlTest {

    private val mockSession = Session(
        token = "test-token",
        user = User(
            id = "user-id",
            username = "testuser",
            role = "admin",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
        )
    )

    @Test
    fun `should use Cloud host and v1 prefix`() = runTest {
        var capturedHost: String? = null
        var capturedPath: String? = null

        val api = getUmamiApiInstance(
            "/v1/me" to { request ->
                capturedHost = request.url.host
                capturedPath = request.url.encodedPath
                respond(mockSession)
            },
            baseUrl = BaseUrl.Cloud
        )

        api.me().getSession()

        capturedHost shouldBe "api.umami.is"
        capturedPath shouldBe "/v1/me"
    }

    @Test
    fun `should use default host and api prefix for SelfHosted`() = runTest {
        var capturedHost: String? = null
        var capturedPath: String? = null

        val api = getUmamiApiInstance(
            "/api/me" to { request ->
                capturedHost = request.url.host
                capturedPath = request.url.encodedPath
                respond(mockSession)
            }
        )

        api.me().getSession()

        capturedHost shouldBe "localhost"
        capturedPath shouldBe "/api/me"
    }

    @Test
    fun `should use custom host and custom prefix for SelfHosted`() = runTest {
        var capturedHost: String? = null
        var capturedPath: String? = null

        val api = getUmamiApiInstance(
            "/custom/prefix/me" to { request ->
                capturedHost = request.url.host
                capturedPath = request.url.encodedPath
                respond(mockSession)
            },
            baseUrl = BaseUrl.SelfHosted("https://umami.my-domain.com", "custom/prefix")
        )

        api.me().getSession()

        capturedHost shouldBe "umami.my-domain.com"
        capturedPath shouldBe "/custom/prefix/me"
    }
}
