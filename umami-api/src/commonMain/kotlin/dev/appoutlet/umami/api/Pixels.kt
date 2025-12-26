package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.Pixel
import dev.appoutlet.umami.domain.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

/**
 * Provides functionalities for interacting with Pixels in the Umami API.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Pixels(private val umami: Umami) {

    /**
     * Retrieves a paginated list of pixels from the Umami API.
     *
     * @param search Optional search string to filter pixels by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [Pixel] objects.
     */
    suspend fun getPixels(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Pixel> {
        return umami.httpClient.get(Api.Pixels()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Retrieves a single pixel from the Umami API by its ID.
     *
     * @param pixelId The unique identifier of the pixel.
     * @return The [Pixel] object matching the provided ID.
     */
    suspend fun getPixel(pixelId: String): Pixel {
        return umami.httpClient.get(Api.Pixels.Id(id = pixelId)).body()
    }

    /**
     * Updates an existing pixel.
     *
     * @param pixelId The unique identifier of the pixel to update.
     * @param request The request object containing the new pixel data.
     * @return The updated [Pixel] object.
     */
    suspend fun updatePixel(pixelId: String, request: UpdatePixelRequest): Pixel {
        return umami.httpClient.post(Api.Pixels.Id(id = pixelId)) {
            setBody(request)
        }.body()
    }

    /**
     * Deletes a pixel from the Umami API.
     *
     * @param pixelId The unique identifier of the pixel to delete.
     */
    suspend fun deletePixel(pixelId: String) {
        umami.httpClient.delete(Api.Pixels.Id(id = pixelId))
    }

    @Serializable
    data class UpdatePixelRequest(
        val name: String? = null,
        val slug: String? = null
    )
}

/**
 * Extension function to provide easy access to [Pixels] functionalities from an [Umami] instance.
 *
 * @return An instance of [Pixels].
 */
fun Umami.pixels(): Pixels = Pixels(this)
