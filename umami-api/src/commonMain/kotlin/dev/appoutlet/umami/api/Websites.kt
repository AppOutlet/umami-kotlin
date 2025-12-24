package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Website
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Provides functionalities for interacting with Websites in the Umami API.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Websites(private val umami: Umami) {

    /**
     * Retrieves a paginated list of websites from the Umami API.
     *
     * @param includeTeams Optional flag to include websites where the user is a team owner.
     * @param search Optional search string to filter websites by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [Website] objects.
     */
    suspend fun getWebsites(
        includeTeams: Boolean? = null,
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Website> {
        return umami.httpClient.get(Api.Websites()) {
            parameter("includeTeams", includeTeams)
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Creates a new website.
     *
     * @param name The name for the website.
     * @param domain The domain for the website.
     * @param shareId The share ID for the website.
     * @param teamId The team ID for the website.
     * @param id The ID for the website.
     * @return The created [Website] object.
     */
    suspend fun createWebsite(
        name: String,
        domain: String,
        shareId: String? = null,
        teamId: String? = null,
        id: String? = null,
    ): Website {
        val request = WebsiteRequest(
            name = name,
            domain = domain,
            shareId = shareId,
            teamId = teamId,
            id = id,
        )
        return umami.httpClient.post(Api.Websites()) {
            setBody(request)
        }.body()
    }

    /**
     * Retrieves a single website from the Umami API by its ID.
     *
     * @param websiteId The unique identifier of the website.
     * @return The [Website] object matching the provided ID.
     */
    suspend fun getWebsite(websiteId: String): Website {
        return umami.httpClient.get(Api.Websites.Id(id = websiteId)).body()
    }

    /**
     * Updates an existing website.
     *
     * @param websiteId The unique identifier of the website to update.
     * @param name The new name for the website.
     * @param domain The new domain for the website.
     * @param shareId The new share ID for the website.
     * @return The updated [Website] object.
     */
    suspend fun updateWebsite(
        websiteId: String,
        name: String? = null,
        domain: String? = null,
        shareId: String? = null,
    ): Website {
        val request = WebsiteRequest(
            name = name,
            domain = domain,
            shareId = shareId,
        )
        return umami.httpClient.post(Api.Websites.Id(id = websiteId)) {
            setBody(request)
        }.body()
    }

    /**
     * Deletes a website from the Umami API.
     *
     * @param websiteId The unique identifier of the website to delete.
     */
    suspend fun deleteWebsite(websiteId: String) {
        umami.httpClient.delete(Api.Websites.Id(id = websiteId))
    }

    /**
     * Resets a website by removing all data related to the website.
     *
     * @param websiteId The unique identifier of the website to reset.
     */
    suspend fun resetWebsite(websiteId: String) {
        umami.httpClient.post(Api.Websites.Id.Reset(parent = Api.Websites.Id(id = websiteId)))
    }
}

/**
 * Extension function to provide easy access to [Websites] functionalities from an [Umami] instance.
 *
 * @return An instance of [Websites].
 */
fun Umami.websites(): Websites = Websites(this)

@Serializable
internal data class WebsiteRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("domain")
    val domain: String? = null,
    @SerialName("shareId")
    val shareId: String? = null,
    @SerialName("teamId")
    val teamId: String? = null,
    @SerialName("id")
    val id: String? = null,
)
