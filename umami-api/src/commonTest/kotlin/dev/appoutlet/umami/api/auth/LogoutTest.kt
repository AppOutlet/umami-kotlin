package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LogoutTest {
    @Test
    fun `should logout successfully`() = runTest {
        val umami = getUmamiInstance(
            "/api/auth/logout" to { _ ->
                respond(Unit)
            }
        )

        umami.headers[HttpHeaders.Authorization] = "Bearer fasdf5a7s65fa6s7d5f8as7d65f"

        umami.logout()

        umami.headers[HttpHeaders.Authorization] shouldBe null
    }
}
