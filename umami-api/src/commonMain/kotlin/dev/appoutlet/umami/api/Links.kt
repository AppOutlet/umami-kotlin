package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.Link
import dev.appoutlet.umami.domain.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import dev.appoutlet.umami.api.Api // Explicit import for Api

/**
 * Provides functionalities for interacting with Links in the Umami API.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Links(private val umami: Umami) {

    /**
     * Retrieves a paginated list of links from the Umami API.
     *
     * @param search Optional search string to filter links by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [Link] objects.
     */
    suspend fun getLinks(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Link> {
        return umami.httpClient.get(Api.Links()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Retrieves a single link from the Umami API by its ID.
     *
     * @param linkId The unique identifier of the link.
     * @return The [Link] object matching the provided ID.
     */
    suspend fun getLink(linkId: String): Link {
        return umami.httpClient.get(Api.Links.Id(id = linkId)).body()
    }

    /**
     * Creates a new link.
     *
     * @param name The name for the link.
     * @param url The destination URL for the link.
     * @param slug The slug for the link (minimum 8 characters).
     * @return The created [Link] object.
     */
    suspend fun createLink(
        name: String,
        url: String,
        slug: String,
    ): Link {
        val request = LinkRequest(
            name = name,
            url = url.validate(),
            slug = slug,
        )
        return umami.httpClient.post(Api.Links()) {
            setBody(request)
        }.body()
    }

    /**
     * Updates an existing link.
     *
     * @param linkId The unique identifier of the link to update.
     * @param name The new name for the link. Optional.
     * @param url The new destination URL for the link. Optional.
     * @param slug The new slug for the link (minimum 8 characters). Optional.
     * @return The updated [Link] object.
     */
    suspend fun updateLink(
        linkId: String,
        name: String? = null,
        url: String? = null,
        slug: String? = null,
    ): Link {
        val request = LinkRequest(
            name = name,
            url = url?.validate(),
            slug = slug,
        )
        return umami.httpClient.post(Api.Links.Id(id = linkId)) {
            setBody(request)
        }.body()
    }

    @Serializable
    data class LinkRequest(
        @SerialName("name") val name: String? = null,
        @SerialName("url") val url: String? = null,
        @SerialName("slug") val slug: String? = null,
    )
}

/**
 * Extension function to provide easy access to [Links] functionalities from an [Umami] instance.
 *
 * @return An instance of [Links].
 */
fun Umami.links(): Links = Links(this)

private val urlRegex = "^https?://(www)?([-a-zA-Z0-9@:%._+~#=]{2,256}.[a-z]{2,6}\b)*(/[/dw.-]*)*[?]*(.+)*$".toRegex()
private fun String.validate(): String {
    require(urlRegex.matches(this)) { "Invalid URL format: $this" }
    return this
}
