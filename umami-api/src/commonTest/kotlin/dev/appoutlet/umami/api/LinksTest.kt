package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Link
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class LinksTest {

    @Test
    fun `getLinks returns search response`() = runTest {
        val mockLink = Link(
            id = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            name = "umami",
            url = "https://www.umami.is",
            slug = "xxxxxxxx",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            deletedAt = null
        )
        val mockResponse = SearchResponse(
            data = listOf(mockLink),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/links" to { request ->
                request.url.encodedPath shouldBe "/api/links"
                respond(mockResponse)
            }
        )

        val response = umami.links().getLinks()
        response shouldBe mockResponse
    }

    @Test
    fun `getLinks passes parameters correctly`() = runTest {
        val mockResponse = SearchResponse<Link>(
            data = emptyList(),
            count = 0,
            page = 2,
            pageSize = 20
        )

        val umami = getUmamiInstance(
            "/api/links" to { request ->
                request.url.encodedPath shouldBe "/api/links"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(mockResponse)
            }
        )

        umami.links().getLinks(search = "test", page = 2, pageSize = 20)
    }

    @Test
    fun `getLink returns link`() = runTest {
        val linkId = "link-id"
        val mockLink = Link(
            id = linkId,
            name = "umami",
            url = "https://www.umami.is",
            slug = "xxxxxxxx",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/links/$linkId" to { request ->
                request.url.encodedPath shouldBe "/api/links/$linkId"
                respond(mockLink)
            }
        )

        val response = umami.links().getLink(linkId)
        response shouldBe mockLink
    }

    @Test
    fun `updateLink updates link`() = runTest {
        val linkId = "link-id"
        val mockLink = Link(
            id = linkId,
            name = "umami-updated",
            url = "https://www.umami.is/updated",
            slug = "updated-slug",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/links/$linkId" to { request ->
                request.url.encodedPath shouldBe "/api/links/$linkId"
                respond(mockLink)
            }
        )

        val response = umami.links().updateLink(
            linkId = linkId,
            name = "umami-updated",
            url = "https://www.umami.is/updated",
            slug = "updated-slug"
        )
        response shouldBe mockLink
    }

    @Test
    fun `createLink creates link`() = runTest {
        val mockLink = Link(
            id = "new-link-id",
            name = "umami-new",
            url = "https://www.umami.is/new",
            slug = "new-slug",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            updatedAt = Instant.parse("2025-10-27T18:49:39.383Z"),
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/links" to { request ->
                request.url.encodedPath shouldBe "/api/links"
                respond(mockLink)
            }
        )

        val response = umami.links().createLink(
            name = "umami-new",
            url = "https://www.umami.is/new",
            slug = "new-slug"
        )
        response shouldBe mockLink
    }

    @Test
    fun `createLink throws IllegalArgumentException for invalid URL`() = runTest {
        val umami = getUmamiInstance() // No specific mock needed for client-side validation

        val invalidUrl = "not-a-valid-url"
        val exception = shouldThrow<IllegalArgumentException> {
            umami.links().createLink(
                name = "test-link",
                url = invalidUrl,
                slug = "test-slug"
            )
        }
        exception.message shouldBe "Invalid URL format: $invalidUrl"
    }

    @Test
    fun `updateLink throws IllegalArgumentException for invalid URL`() = runTest {
        val umami = getUmamiInstance() // No specific mock needed for client-side validation

        val linkId = "some-link-id"
        val invalidUrl = "not-a-valid-url"
        val exception = shouldThrow<IllegalArgumentException> {
            umami.links().updateLink(
                linkId = linkId,
                name = "test-link",
                url = invalidUrl,
                slug = "test-slug"
            )
        }
        exception.message shouldBe "Invalid URL format: $invalidUrl"
    }

    @Test
    fun `deleteLink deletes link`() = runTest {
        val linkId = "link-id"

        val umami = getUmamiInstance(
            "/api/links/$linkId" to { request ->
                request.url.encodedPath shouldBe "/api/links/$linkId"
                respond(Unit)
            }
        )

        umami.links().deleteLink(linkId)
    }
}
