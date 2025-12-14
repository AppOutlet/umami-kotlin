package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.Link
import dev.appoutlet.umami.domain.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter

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
}

/**
 * Extension function to provide easy access to [Links] functionalities from an [Umami] instance.
 *
 * @return An instance of [Links].
 */
fun Umami.links(): Links = Links(this)
