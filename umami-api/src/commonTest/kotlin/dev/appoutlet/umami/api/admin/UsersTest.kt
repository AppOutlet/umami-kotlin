package dev.appoutlet.umami.api.admin

import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.UserCount
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class UsersTest {

    @Test
    fun `should retrieve a list of users successfully`() = runTest {
        val fixtureUser = User(
            id = "user-id-1",
            username = "testuser",
            role = "admin",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            logoUrl = null,
            displayName = "Test User",
            isAdmin = true,
            teams = listOf(
                Team(
                    id = "team-id-1",
                    name = "Test Team",
                    accessCode = "12345",
                    logoUrl = null,
                    createdAt = Instant.parse("2023-01-01T00:00:00Z"),
                    updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
                    deletedAt = null,
                    members = emptyList()
                )
            ),
            count = UserCount(websites = 5)
        )

        val fixtureUsersResponse = Users.Response(
            data = listOf(fixtureUser),
            count = 1,
            page = 1,
            pageSize = 10,
            orderBy = "username"
        )

        val umami = getUmamiInstance(
            "/api/admin/users" to { request ->
                request.url.encodedPath shouldBe "/api/admin/users"
                request.url.parameters.contains("search") shouldBe false
                request.url.parameters.contains("page") shouldBe false
                request.url.parameters.contains("pageSize") shouldBe false
                respond(fixtureUsersResponse)
            }
        )

        val result = umami.getUsers()

        result shouldBe fixtureUsersResponse
    }

    @Test
    fun `should append search query parameter when provided`() = runTest {
        val fixtureSearchTerm = "some_search_term"
        val fixtureUser = User(
            id = "user-id-2",
            username = "searchuser",
            role = "admin",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            logoUrl = null,
            displayName = "Search User",
            isAdmin = true,
            teams = emptyList(),
            count = UserCount(websites = 1)
        )

        val fixtureUsersResponse = Users.Response(
            data = listOf(fixtureUser),
            count = 1,
            page = 1,
            pageSize = 10,
            orderBy = "username"
        )

        val umami = getUmamiInstance(
            "/api/admin/users" to { request ->
                request.url.encodedPath shouldBe "/api/admin/users"
                request.url.parameters["search"] shouldBe fixtureSearchTerm
                request.url.parameters.contains("page") shouldBe false
                request.url.parameters.contains("pageSize") shouldBe false
                respond(fixtureUsersResponse)
            }
        )

        val result = umami.getUsers(search = fixtureSearchTerm)

        result shouldBe fixtureUsersResponse
    }

    @Test
    fun `should append page and pageSize query parameters when provided`() = runTest {
        val fixturePage = 2
        val fixturePageSize = 5
        val fixtureUser = User(
            id = "user-id-3",
            username = "pageduser",
            role = "admin",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            logoUrl = null,
            displayName = "Paged User",
            isAdmin = true,
            teams = emptyList(),
            count = UserCount(websites = 1)
        )

        val fixtureUsersResponse = Users.Response(
            data = listOf(fixtureUser),
            count = 1,
            page = fixturePage,
            pageSize = fixturePageSize,
            orderBy = "username"
        )

        val umami = getUmamiInstance(
            "/api/admin/users" to { request ->
                request.url.encodedPath shouldBe "/api/admin/users"
                request.url.parameters.contains("search") shouldBe false
                request.url.parameters["page"] shouldBe fixturePage.toString()
                request.url.parameters["pageSize"] shouldBe fixturePageSize.toString()
                respond(fixtureUsersResponse)
            }
        )

        val result = umami.getUsers(page = fixturePage, pageSize = fixturePageSize)

        result shouldBe fixtureUsersResponse
    }
}
