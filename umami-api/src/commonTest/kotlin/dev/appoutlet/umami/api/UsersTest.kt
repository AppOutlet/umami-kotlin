package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.respondOk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class UsersTest {
    @Test
    fun `create should return the created user`() = runTest {
        val expectedResponse = User(
            id = "user-123",
            username = "testuser",
            role = "user",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/users" to {
                val body = it.body<CreateUserRequest>()
                body.username shouldBe "testuser"
                body.password shouldBe "password"
                body.role shouldBe "user"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.users().create("testuser", "password", "user")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `get should return a user`() = runTest {
        val expectedResponse = User(
            id = "user-123",
            username = "testuser",
            role = "user",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/users/user-123" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.users().get("user-123")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `update should return the updated user`() = runTest {
        val expectedResponse = User(
            id = "user-123",
            username = "updateduser",
            role = "admin",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/users/user-123" to {
                val body = it.body<UpdateUserRequest>()
                body.username shouldBe "updateduser"
                body.role shouldBe "admin"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.users().update(
            userId = "user-123",
            username = "updateduser",
            role = "admin"
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `delete should not throw an exception on success`() = runTest {
        val umami = getUmamiInstance(
            "/api/users/user-123" to {
                respondOk()
            }
        )

        umami.users().delete("user-123")
    }

    @Test
    fun `getWebsites should return a list of websites`() = runTest {
        val expectedResponse = SearchResponse(
            data = listOf(
                Website(
                    id = "website-123",
                    name = "Test Website",
                    domain = "test.com",
                    shareId = null,
                    createdAt = Instant.parse("2022-01-01T00:00:00Z"),
                    userId = "user-123",
                    createdBy = "user-123"
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/users/user-123/websites" to {
                it.url.parameters["includeTeams"] shouldBe "false"
                it.url.parameters["search"] shouldBe "test"
                it.url.parameters["page"] shouldBe "1"
                it.url.parameters["pageSize"] shouldBe "10"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.users().getWebsites(
            userId = "user-123",
            includeTeams = false,
            search = "test",
            page = 1,
            pageSize = 10
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `getTeams should return a list of teams`() = runTest {
        val expectedResponse = SearchResponse(
            data = listOf(
                Team(
                    id = "team-123",
                    name = "Test Team",
                    accessCode = "code",
                    createdAt = Instant.parse("2022-01-01T00:00:00Z")
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/users/user-123/teams" to {
                it.url.parameters["page"] shouldBe "1"
                it.url.parameters["pageSize"] shouldBe "10"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.users().getTeams(
            userId = "user-123",
            page = 1,
            pageSize = 10
        )
        actualResponse shouldBe expectedResponse
    }
}
