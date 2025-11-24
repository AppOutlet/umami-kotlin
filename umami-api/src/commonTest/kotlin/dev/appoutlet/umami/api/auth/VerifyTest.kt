package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Clock

class VerifyTest {
    @Test
    fun `should verify logged user`() = runTest {
        val fixtureUser = User(
            id = "123",
            username = "testuser",
            role = "admin",
            createdAt = Clock.System.now(),
            isAdmin = true,
            teams = listOf(
                Team(
                    id = "123",
                    name = "Test Team",
                    accessCode = "access-code",
                    logoUrl = "https://example.com/logo.png",
                    createdAt = Clock.System.now(),
                    updatedAt = Clock.System.now(),
                    deletedAt = Clock.System.now(),
                    members = emptyList()
                )
            )
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
