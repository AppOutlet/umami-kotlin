package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.time.Clock
import kotlinx.coroutines.test.runTest

class VerifyTest {
    @Test
    fun `should verify logged user`() = runTest {
        val fixtureUser = User(
            id = "123",
            username = "testuser",
            role = "admin",
            createdAt = Clock.System.now()
        )

        val umami = getUmamiInstance(
            "/api/auth/verify" to { _ ->
                respond(fixtureUser)
            }
        )

        val user = umami.verify()

        user shouldBe fixtureUser
    }
}
