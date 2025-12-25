package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Pixel
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.content.OutgoingContent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.time.Instant

class PixelsTest {
    @Test
    fun `getPixels returns search response`() = runTest {
        val mockPixel = Pixel(
            id = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            name = "Umami Pixel",
            slug = "xxxxxxxx",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            updatedAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            deletedAt = null
        )

        val mockResponse = SearchResponse(
            data = listOf(mockPixel),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                respond(mockResponse)
            }
        )

        val response = umami.pixels().getPixels()
        response shouldBe mockResponse
    }

    @Test
    fun `getPixels passes parameters correctly`() = runTest {
        val mockResponse = SearchResponse<Pixel>(
            data = emptyList(),
            count = 0,
            page = 2,
            pageSize = 20
        )

        val umami = getUmamiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(mockResponse)
            }
        )

        umami.pixels().getPixels(search = "test", page = 2, pageSize = 20)
    }

    @Test
    fun `getPixel returns pixel`() = runTest {
        val pixelId = "pixel-id"
        val mockPixel = Pixel(
            id = pixelId,
            name = "Umami Pixel",
            slug = "xxxxxxxx",
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            updatedAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"
                respond(mockPixel)
            }
        )

        val response = umami.pixels().getPixel(pixelId)
        response shouldBe mockPixel
    }

    @Test
    fun `updatePixel updates pixel`() = runTest {
        val pixelId = "pixel-id"
        val requestName = "Umami Pixel Updated"
        val requestSlug = "updated-slug"

        val mockPixel = Pixel(
            id = pixelId,
            name = requestName,
            slug = requestSlug,
            userId = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
            teamId = null,
            createdAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            updatedAt = Instant.parse("2025-10-27T18:50:54.079Z"),
            deletedAt = null
        )

        val umami = getUmamiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"
                val bodyText = (request.body as OutgoingContent.ByteArrayContent).bytes().decodeToString()
                val updateRequest = Json.decodeFromString<Pixels.UpdatePixelRequest>(bodyText)
                updateRequest.name shouldBe requestName
                updateRequest.slug shouldBe requestSlug
                respond(mockPixel)
            }
        )

        val response = umami.pixels().updatePixel(
            pixelId,
            Pixels.UpdatePixelRequest(
                name = requestName,
                slug = requestSlug
            )
        )
        response shouldBe mockPixel
    }

    @Test
    fun `deletePixel deletes pixel`() = runTest {
        val pixelId = "pixel-id"

        val umami = getUmamiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"
                respond(Unit)
            }
        )

        umami.pixels().deletePixel(pixelId)
    }
}
