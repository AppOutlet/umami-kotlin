package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Pixel
import dev.appoutlet.umami.domain.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.serialization.Serializable

/**
 * Provides functionalities for interacting with Pixels in the Umami API.
 *
 * @param api The [UmamiApi] instance used for making HTTP requests.
 */
class Pixels(private val api: UmamiApi) {

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
        return api.httpClient.get("pixels") {
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
        return api.httpClient.get("pixels/$pixelId").body()
    }

    /**
     * Updates an existing pixel.
     *
     * @param pixelId The unique identifier of the pixel to update.
     * @param name The new name for the pixel.
     * @param slug The new slug for the pixel.
     * @return The updated [Pixel] object.
     */
    suspend fun updatePixel(
        pixelId: String,
        name: String? = null,
        slug: String? = null,
    ): Pixel {
        val request = UpdatePixelRequest(
            name = name,
            slug = slug,
        )
        return api.httpClient.post("pixels/$pixelId") {
            setBody(request)
        }.body()
    }

    /**
     * Deletes a pixel from the Umami API.
     *
     * @param pixelId The unique identifier of the pixel to delete.
     */
    suspend fun deletePixel(pixelId: String) {
        api.httpClient.delete("pixels/$pixelId")
    }

    @Serializable
    private data class UpdatePixelRequest(
        val name: String? = null,
        val slug: String? = null
    )
}

/**
 * Extension function to provide easy access to [Pixels] functionalities from an [UmamiApi] instance.
 *
 * @return An instance of [Pixels].
 */
fun UmamiApi.pixels(): Pixels = Pixels(this)
