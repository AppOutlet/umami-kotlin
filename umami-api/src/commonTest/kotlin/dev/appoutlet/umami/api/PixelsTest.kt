package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Pixel
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.fixture
import dev.appoutlet.umami.testing.getUmamiApiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.content.OutgoingContent
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test

class PixelsTest {
    @Test
    fun `getPixels returns search response`() = runTest {
        val fixturePixel = Pixel.fixture()

        val mockResponse = SearchResponse(
            data = listOf(fixturePixel),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val api = getUmamiApiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                respond(mockResponse)
            }
        )

        val response = api.pixels().getPixels()
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

        val api = getUmamiApiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                request.url.parameters["search"] shouldBe "test"
                request.url.parameters["page"] shouldBe "2"
                request.url.parameters["pageSize"] shouldBe "20"
                respond(mockResponse)
            }
        )

        api.pixels().getPixels(search = "test", page = 2, pageSize = 20)
    }

    @Test
    fun `getPixel returns pixel`() = runTest {
        val pixelId = "pixel-id"

        val fixturePixel = Pixel.fixture()

        val api = getUmamiApiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"

                respond(fixturePixel)
            }
        )

        val response = api.pixels().getPixel(pixelId)

        response shouldBe fixturePixel
    }

    @Test
    fun `createPixel creates pixel`() = runTest {
        val requestName = "Umami Pixel"
        val requestSlug = "pixel-slug"
        val requestTeamId = "team-id"

        val fixturePixel = Pixel.fixture()

        val api = getUmamiApiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                val bodyText = (request.body as OutgoingContent.ByteArrayContent).bytes().decodeToString()
                val createRequest = Json.decodeFromString<Pixels.CreatePixelRequest>(bodyText)
                createRequest.name shouldBe requestName
                createRequest.slug shouldBe requestSlug
                createRequest.teamId shouldBe requestTeamId
                respond(fixturePixel)
            }
        )

        val response = api.pixels().createPixel(
            name = requestName,
            slug = requestSlug,
            teamId = requestTeamId,
        )

        response shouldBe fixturePixel
    }

    @Test
    fun `createPixel sends null teamId by default`() = runTest {
        val fixturePixel = Pixel.fixture()

        val api = getUmamiApiInstance(
            "/api/pixels" to { request ->
                request.url.encodedPath shouldBe "/api/pixels"
                val bodyText = (request.body as OutgoingContent.ByteArrayContent).bytes().decodeToString()
                val createRequest = Json.decodeFromString<Pixels.CreatePixelRequest>(bodyText)
                createRequest.name shouldBe "Umami Pixel"
                createRequest.slug shouldBe "pixel-slug"
                createRequest.teamId shouldBe null
                respond(fixturePixel)
            }
        )

        val response = api.pixels().createPixel(
            name = "Umami Pixel",
            slug = "pixel-slug",
        )

        response shouldBe fixturePixel
    }

    @Test
    fun `updatePixel updates pixel`() = runTest {
        val pixelId = "pixel-id"
        val requestName = "Umami Pixel Updated"
        val requestSlug = "updated-slug"

        val fixturePixel = Pixel.fixture()

        val api = getUmamiApiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"
                val bodyText = (request.body as OutgoingContent.ByteArrayContent).bytes().decodeToString()
                val updateRequest = Json.decodeFromString<Pixels.UpdatePixelRequest>(bodyText)
                updateRequest.name shouldBe requestName
                updateRequest.slug shouldBe requestSlug
                respond(fixturePixel)
            }
        )

        val response = api.pixels().updatePixel(
            pixelId,
            name = requestName,
            slug = requestSlug
        )

        response shouldBe fixturePixel
    }

    @Test
    fun `deletePixel deletes pixel`() = runTest {
        val pixelId = "pixel-id"

        val api = getUmamiApiInstance(
            "/api/pixels/$pixelId" to { request ->
                request.url.encodedPath shouldBe "/api/pixels/$pixelId"
                respond(Unit)
            }
        )

        api.pixels().deletePixel(pixelId)
    }
}
