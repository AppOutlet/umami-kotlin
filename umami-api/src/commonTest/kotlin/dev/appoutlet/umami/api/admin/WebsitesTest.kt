package dev.appoutlet.umami.api.admin

import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class WebsitesTest {

    @Test
    fun `should retrieve a list of websites successfully`() = runTest {
        val fixtureWebsite = Website(
            id = "website-id-1",
            name = "My Website",
            domain = "mywebsite.com",
            shareId = null,
            resetAt = null,
            userId = "user-id-1",
            teamId = null,
            createdBy = "user-id-1",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            user = User(
                id = "user-id-1",
                username = "admin",
                role = "admin",
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            ),
            team = null
        )

        val fixtureWebsitesResponse = Websites.Response(
            data = listOf(fixtureWebsite),
            count = 1,
            page = 1,
            pageSize = 10,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                request.url.parameters.contains("search") shouldBe false
                request.url.parameters.contains("page") shouldBe false
                request.url.parameters.contains("pageSize") shouldBe false
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites()

        result shouldBe fixtureWebsitesResponse
    }

    @Test
    fun `should append search query parameter when provided`() = runTest {
        val fixtureSearchTerm = "example.com"
        val fixtureWebsite = Website(
            id = "website-id-2",
            name = "Example Website",
            domain = "example.com",
            shareId = null,
            resetAt = null,
            userId = "user-id-2",
            teamId = null,
            createdBy = "user-id-2",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            user = User(
                id = "user-id-2",
                username = "testuser",
                role = "user",
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            ),
            team = null
        )

        val fixtureWebsitesResponse = Websites.Response(
            data = listOf(fixtureWebsite),
            count = 1,
            page = 1,
            pageSize = 10,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                request.url.parameters["search"] shouldBe fixtureSearchTerm
                request.url.parameters.contains("page") shouldBe false
                request.url.parameters.contains("pageSize") shouldBe false
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites(search = fixtureSearchTerm)

        result shouldBe fixtureWebsitesResponse
    }

    @Test
    fun `should append page and pageSize query parameters when provided`() = runTest {
        val fixturePage = 2
        val fixturePageSize = 5
        val fixtureWebsite = Website(
            id = "website-id-3",
            name = "Paged Website",
            domain = "paged.com",
            shareId = null,
            resetAt = null,
            userId = "user-id-3",
            teamId = null,
            createdBy = "user-id-3",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            user = User(
                id = "user-id-3",
                username = "pageduser",
                role = "user",
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            ),
            team = null
        )

        val fixtureWebsitesResponse = Websites.Response(
            data = listOf(fixtureWebsite),
            count = 1,
            page = fixturePage,
            pageSize = fixturePageSize,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                request.url.parameters.contains("search") shouldBe false
                request.url.parameters["page"] shouldBe fixturePage.toString()
                request.url.parameters["pageSize"] shouldBe fixturePageSize.toString()
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites(page = fixturePage, pageSize = fixturePageSize)

        result shouldBe fixtureWebsitesResponse
    }

    @Test
    fun `should handle websites with team ownership`() = runTest {
        val fixtureWebsite = Website(
            id = "website-id-4",
            name = "Team Website",
            domain = "team.com",
            shareId = "share-123",
            resetAt = Instant.parse("2023-06-01T00:00:00Z"),
            userId = "user-id-4",
            teamId = "team-id-1",
            createdBy = "user-id-4",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            user = User(
                id = "user-id-4",
                username = "teamadmin",
                role = "admin",
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            ),
            team = Team(
                id = "team-id-1",
                name = "Test Team",
                accessCode = "12345",
                logoUrl = null,
                createdAt = Instant.parse("2023-01-01T00:00:00Z"),
                updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
                deletedAt = null,
                members = emptyList()
            )
        )

        val fixtureWebsitesResponse = Websites.Response(
            data = listOf(fixtureWebsite),
            count = 1,
            page = 1,
            pageSize = 10,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites()

        result shouldBe fixtureWebsitesResponse
        result.data.first().team shouldBe fixtureWebsite.team
        result.data.first().teamId shouldBe "team-id-1"
    }

    @Test
    fun `should handle empty websites list`() = runTest {
        val fixtureWebsitesResponse = Websites.Response(
            data = emptyList(),
            count = 0,
            page = 1,
            pageSize = 10,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites()

        result shouldBe fixtureWebsitesResponse
        result.data.size shouldBe 0
    }

    @Test
    fun `should accept Request object`() = runTest {
        val fixtureRequest = Websites.Request(
            search = "test",
            page = 3,
            pageSize = 20
        )

        val fixtureWebsite = Website(
            id = "website-id-5",
            name = "Test Website",
            domain = "test.com",
            shareId = null,
            resetAt = null,
            userId = "user-id-5",
            teamId = null,
            createdBy = "user-id-5",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2023-01-01T00:00:00Z"),
            deletedAt = null,
            user = null,
            team = null
        )

        val fixtureWebsitesResponse = Websites.Response(
            data = listOf(fixtureWebsite),
            count = 1,
            page = 3,
            pageSize = 20,
            orderBy = "name"
        )

        val umami = getUmamiInstance(
            "/api/admin/websites" to { request ->
                request.url.encodedPath shouldBe "/api/admin/websites"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "3"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(fixtureWebsitesResponse)
            }
        )

        val result = umami.getWebsites(fixtureRequest)

        result shouldBe fixtureWebsitesResponse
    }
}
