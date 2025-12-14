package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Link
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
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
}
