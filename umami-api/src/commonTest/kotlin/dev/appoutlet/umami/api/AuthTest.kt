package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import dev.appoutlet.umami.util.headers
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
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
        val mockLoginResponse = Auth.Login.Response(
            token = testToken,
            user = testUser
        )

        val umami = getUmamiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<Auth.Login.Request>()
                loginRequest.username shouldBe fixtureUsername
                loginRequest.password shouldBe fixturePassword
                respond(mockLoginResponse)
            }
        )

        val response = umami.auth().login(fixtureUsername, fixturePassword)

        response shouldBe mockLoginResponse
        umami.headers.get(HttpHeaders.Authorization) shouldBe "Bearer $testToken"
    }

    @Test
    fun `login with request object should return success response and set auth header`() = runTest {
        val fixtureLoginRequest = Auth.Login.Request(
            username = "testuser",
            password = "testpassword"
        )
        val mockLoginResponse = Auth.Login.Response(
            token = testToken,
            user = testUser
        )

        val umami = getUmamiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<Auth.Login.Request>()
                loginRequest shouldBe fixtureLoginRequest
                respond(mockLoginResponse)
            }
        )

        val response = umami.auth().login(fixtureLoginRequest)

        response shouldBe mockLoginResponse
        umami.headers.get(HttpHeaders.Authorization) shouldBe "Bearer $testToken"
    }

    @Test
    fun `logout should remove auth header`() = runTest {
        val umami = getUmamiInstance(
            "/api/auth/logout" to { request ->
                request.url.encodedPath shouldBe "/api/auth/logout"
                respond(status = HttpStatusCode.OK, content = "")
            }
        )
        // Set a dummy token to ensure it gets removed
        umami.headers.put(HttpHeaders.Authorization, "Bearer $testToken")

        umami.auth().logout()

        umami.headers.get(HttpHeaders.Authorization) shouldBe null
    }

    @Test
    fun `verify should return user details`() = runTest {
        val umami = getUmamiInstance(
            "/api/auth/verify" to { request ->
                request.url.encodedPath shouldBe "/api/auth/verify"
                respond(testUser)
            }
        )
        // Ensure Authorization header is present for verification (optional, but good practice for testing)
        umami.headers.put(HttpHeaders.Authorization, "Bearer $testToken")

        val response = umami.auth().verify()

        response shouldBe testUser
    }
}
