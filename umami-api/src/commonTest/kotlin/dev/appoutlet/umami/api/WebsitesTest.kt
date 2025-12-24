package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.content.TextContent
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class WebsitesTest {

    @Test
    fun `getWebsites returns search response`() = runTest {
        val mockWebsite = Website(
            id = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            name = "umami",
            domain = "umami.is",
            shareId = null,
            resetAt = null,
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdBy = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = null,
            deletedAt = null
        )
        val mockResponse = SearchResponse(
            data = listOf(mockWebsite),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/websites" to { request ->
                request.url.encodedPath shouldBe "/api/websites"
                respond(mockResponse)
            }
        )

        val response = umami.websites().getWebsites()
        response shouldBe mockResponse
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
            "/api/websites" to { request ->
                request.url.encodedPath shouldBe "/api/websites"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                request.url.parameters["includeTeams"] shouldBe "true"
                respond(mockResponse)
            }
        )

        umami.websites().getWebsites(search = "test", page = 2, pageSize = 20, includeTeams = true)
    }

    @Test
    fun `getWebsite returns website`() = runTest {
        val websiteId = "website-id"
        val mockWebsite = Website(
            id = websiteId,
            name = "umami",
            domain = "umami.is",
            shareId = null,
            resetAt = null,
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdBy = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = null,
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/websites/$websiteId" to { request ->
                request.url.encodedPath shouldBe "/api/websites/$websiteId"
                respond(mockWebsite)
            }
        )

        val response = umami.websites().getWebsite(websiteId)
        response shouldBe mockWebsite
    }

    @Test
    fun `createWebsite creates website`() = runTest {
        val mockWebsite = Website(
            id = "new-website-id",
            name = "umami-new",
            domain = "new.umami.is",
            shareId = null,
            resetAt = null,
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdBy = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = null,
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/websites" to { request ->
                request.url.encodedPath shouldBe "/api/websites"
                val body = (request.body as TextContent).text
                body shouldBe """{"name":"umami-new","domain":"new.umami.is"}"""
                respond(mockWebsite)
            }
        )

        val response = umami.websites().createWebsite(
            name = "umami-new",
            domain = "new.umami.is",
        )
        response shouldBe mockWebsite
    }

    @Test
    fun `updateWebsite updates website`() = runTest {
        val websiteId = "website-id"
        val mockWebsite = Website(
            id = websiteId,
            name = "umami-updated",
            domain = "updated.umami.is",
            shareId = "updated-share-id",
            resetAt = null,
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdBy = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = null,
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/websites/$websiteId" to { request ->
                request.url.encodedPath shouldBe "/api/websites/$websiteId"
                val body = (request.body as TextContent).text
                body shouldBe """{"name":"umami-updated","domain":"updated.umami.is","shareId":"updated-share-id"}"""
                respond(mockWebsite)
            }
        )

        val response = umami.websites().updateWebsite(
            websiteId = websiteId,
            name = "umami-updated",
            domain = "updated.umami.is",
            shareId = "updated-share-id"
        )
        response shouldBe mockWebsite
    }

    @Test
    fun `deleteWebsite deletes website`() = runTest {
        val websiteId = "website-id"

        val umami = getUmamiInstance(
            "/api/websites/$websiteId" to { request ->
                request.url.encodedPath shouldBe "/api/websites/$websiteId"
                respond(Unit)
            }
        )

        umami.websites().deleteWebsite(websiteId)
    }

    @Test
    fun `resetWebsite resets website`() = runTest {
        val websiteId = "website-id"

        val umami = getUmamiInstance(
            "/api/websites/$websiteId/reset" to { request ->
                request.url.encodedPath shouldBe "/api/websites/$websiteId/reset"
                respond(Unit)
            }
        )

        umami.websites().resetWebsite(websiteId)
    }
}
