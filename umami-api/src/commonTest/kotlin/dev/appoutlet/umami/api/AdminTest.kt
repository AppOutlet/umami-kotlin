package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class AdminTest {

    @Test
    fun `getUsers returns search response`() = runTest {
        val mockUser = User(
            id = "user-id",
            username = "username",
            role = "admin",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            isAdmin = true
        )
        val mockResponse = SearchResponse(
            data = listOf(mockUser),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/admin/users" to { request ->
                request.url.encodedPath shouldBe "/api/admin/users"
                respond(mockResponse)
            }
        )

        val response = umami.admin().getUsers()
        response shouldBe mockResponse
    }

    @Test
    fun `getWebsites returns search response`() = runTest {
        val mockWebsite = Website(
            id = "website-id",
            name = "website",
            domain = "example.com",
            userId = "user-id",
            createdBy = "user-id",
            createdAt = Instant.parse("2023-01-01T00:00:00Z")
        )
        val mockResponse = SearchResponse(
            data = listOf(mockWebsite),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                respond(mockResponse)
            }
        )

        val response = umami.admin().getWebsites()
        response shouldBe mockResponse
    }

    @Test
    fun `getUsers passes parameters correctly`() = runTest {
        val mockResponse = SearchResponse<User>(
            data = emptyList(),
            count = 0,
            page = 2,
            pageSize = 20
        )

        val umami = getUmamiInstance(
            "/api/admin/users" to { request ->
                request.url.encodedPath shouldBe "/api/admin/users"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(mockResponse)
            }
        )

        umami.admin().getUsers(search = "test", page = 2, pageSize = 20)
    }

    @Test
    fun `getWebsites passes parameters correctly`() = runTest {
        val mockResponse = SearchResponse<Website>(
            data = emptyList(),
            count = 0,
            page = 2,
            pageSize = 20
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(mockResponse)
            }
        )

        umami.admin().getWebsites(search = "test", page = 2, pageSize = 20)
    }
}
