package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Session
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiApiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class AuthTest {

    private val testUser = User(
        id = "user-id-123",
        username = "testuser",
        role = "admin",
        createdAt = Instant.parse("2023-01-01T00:00:00Z"),
        isAdmin = true
    )
    private val testToken = "test-jwt-token"

    @Test
    fun `login with username and password should return success response and set auth header`() = runTest {
        val fixtureUsername = "testuser"
        val fixturePassword = "testpassword"
        val mockLoginResponse = Session(
            token = testToken,
            user = testUser
        )

        val api = getUmamiApiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<Auth.Login.Request>()
                loginRequest.username shouldBe fixtureUsername
                loginRequest.password shouldBe fixturePassword
                respond(mockLoginResponse)
            }
        )

        val response = api.auth().login(fixtureUsername, fixturePassword)

        response shouldBe mockLoginResponse
        api.headers.get(HttpHeaders.Authorization) shouldBe "Bearer $testToken"
    }

    @Test
    fun `login with request object should return success response and set auth header`() = runTest {
        val fixtureLoginRequest = Auth.Login.Request(
            username = "testuser",
            password = "testpassword"
        )
        val mockLoginResponse = Session(
            token = testToken,
            user = testUser
        )

        val api = getUmamiApiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<Auth.Login.Request>()
                loginRequest shouldBe fixtureLoginRequest
                respond(mockLoginResponse)
            }
        )

        val response = api.auth().login(fixtureLoginRequest)

        response shouldBe mockLoginResponse
        api.headers.get(HttpHeaders.Authorization) shouldBe "Bearer $testToken"
    }

    @Test
    fun `login with API key should return success response and set x-umami-api-key header`() = runTest {
        val fixtureApiKey = "test-api-key"
        val mockLoginResponse = Session(
            token = "unused-token",
            user = testUser
        )

        val api = getUmamiApiInstance(
            "/api/me" to { request ->
                request.url.encodedPath shouldBe "/api/me"
                request.headers[UMAMI_API_KEY_HEADER] shouldBe fixtureApiKey
                respond(mockLoginResponse)
            }
        )

        val response = api.auth().login(fixtureApiKey)

        response shouldBe mockLoginResponse
        api.headers.get(UMAMI_API_KEY_HEADER) shouldBe fixtureApiKey
    }

    @Test
    fun `login with invalid API key should remove header and throw exception`() = runTest {
        val fixtureApiKey = "invalid-api-key"

        val api = getUmamiApiInstance(
            "/api/me" to { _ ->
                respond(status = HttpStatusCode.Unauthorized, content = "")
            }
        )

        assertFailsWith<Exception> {
            api.auth().login(fixtureApiKey)
        }

        api.headers.get(UMAMI_API_KEY_HEADER) shouldBe null
    }

    @Test
    fun `logout with authorization header should call logout endpoint and remove headers`() = runTest {
        var logoutCalled = false
        val api = getUmamiApiInstance(
            "/api/auth/logout" to { request ->
                request.url.encodedPath shouldBe "/api/auth/logout"
                logoutCalled = true
                respond(status = HttpStatusCode.OK, content = "")
            }
        )
        // Set dummy headers
        api.headers.put(HttpHeaders.Authorization, "Bearer $testToken")
        api.headers.put(UMAMI_API_KEY_HEADER, "some-key")

        api.auth().logout()

        logoutCalled shouldBe true
        api.headers.get(HttpHeaders.Authorization) shouldBe null
        api.headers.get(UMAMI_API_KEY_HEADER) shouldBe null
    }

    @Test
    fun `login with API key should remove existing authorization header`() = runTest {
        val fixtureApiKey = "test-api-key"
        val mockLoginResponse = Session(
            token = "unused-token",
            user = testUser
        )

        val api = getUmamiApiInstance(
            "/api/me" to { _ ->
                respond(mockLoginResponse)
            }
        )
        api.headers.put(HttpHeaders.Authorization, "Bearer $testToken")

        api.auth().login(fixtureApiKey)

        api.headers.get(HttpHeaders.Authorization) shouldBe null
        api.headers.get(UMAMI_API_KEY_HEADER) shouldBe fixtureApiKey
    }

    @Test
    fun `logout without authorization header should only remove x-umami-api-key header`() = runTest {
        var logoutCalled = false
        val api = getUmamiApiInstance(
            "/api/auth/logout" to { _ ->
                logoutCalled = true
                respond(status = HttpStatusCode.OK, content = "")
            }
        )
        // Set dummy API key header, but no Authorization header
        api.headers.put(UMAMI_API_KEY_HEADER, "some-key")

        api.auth().logout()

        logoutCalled shouldBe false
        api.headers.get(UMAMI_API_KEY_HEADER) shouldBe null
    }

    @Test
    fun `verify should return user details`() = runTest {
        val api = getUmamiApiInstance(
            "/api/auth/verify" to { request ->
                request.url.encodedPath shouldBe "/api/auth/verify"
                respond(testUser)
            }
        )
        // Ensure Authorization header is present for verification (optional, but good practice for testing)
        api.headers.put(HttpHeaders.Authorization, "Bearer $testToken")

        val response = api.auth().verify()

        response shouldBe testUser
    }
}
