package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpHeaders
import kotlin.test.Test
import kotlin.time.Clock
import kotlinx.coroutines.test.runTest

class LoginTest {
    @Test
    fun `should login using username and password`() = runTest {
        val fixtureUsername = "testuser"
        val fixturePassword = "testpassword"
        val fixtureToken = "sdfa6sdf8asdf76a5sdf654asd7f3as6df"
        val fixtureResponse = UmamiLogin.Response(
            token = fixtureToken,
            user = User(
                id = "123",
                username = fixtureUsername,
                role = "admin",
                createdAt = Clock.System.now(),
            )
        )

        val umami = getUmamiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<UmamiLogin.Request>()
                loginRequest.username shouldBe fixtureUsername
                loginRequest.password shouldBe fixturePassword
                respond(fixtureResponse)
            }
        )

        val response = umami.login(
            username = fixtureUsername,
            password = fixturePassword
        )

        response shouldBe fixtureResponse
        umami.headers[HttpHeaders.Authorization] shouldBe "Bearer $fixtureToken"
    }

    @Test
    fun `should login using request object`() = runTest {
        val fixtureUsername = "testuser"
        val fixturePassword = "testpassword"
        val fixtureRequest = UmamiLogin.Request(
            username = fixtureUsername,
            password = fixturePassword
        )
        val fixtureToken = "sdfa6sdf8asdf76a5sdf654asd7f3as6df"
        val fixtureResponse = UmamiLogin.Response(
            token = fixtureToken,
            user = User(
                id = "123",
                username = fixtureUsername,
                role = "admin",
                createdAt = Clock.System.now(),
            )
        )

        val umami = getUmamiInstance(
            "/api/auth/login" to { request ->
                val loginRequest = request.body<UmamiLogin.Request>()
                loginRequest.username shouldBe fixtureUsername
                loginRequest.password shouldBe fixturePassword
                respond(fixtureResponse)
            }
        )

        val response = umami.login(fixtureRequest)

        response shouldBe fixtureResponse
    }
}
