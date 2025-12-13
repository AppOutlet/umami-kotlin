package dev.appoutlet.umami.api.admin

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.Api
import dev.appoutlet.umami.domain.Website
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Retrieves a paginated and searchable list of all websites.
 *
 * @param request The request object containing search, page, and pageSize parameters.
 * @return A [Websites.Response] object containing the list of websites and pagination metadata.
 */
suspend fun Umami.getWebsites(request: Websites.Request): Websites.Response {
    return httpClient.get(Api.Admin.Websites()) {
        request.search?.let {
            parameter("search", it)
        }
        request.page?.let {
            parameter("page", it)
        }
        request.pageSize?.let {
            parameter("pageSize", it)
        }
    }.body()
}

/**
 * Retrieves a paginated and searchable list of all websites.
 * This is a convenience function that creates a [Websites.Request] object.
 *
 * @param search Optional search query to filter websites by name or other attributes.
 * @param page Optional page number for pagination (1-indexed).
 * @param pageSize Optional number of websites per page.
 * @return A [Websites.Response] object containing the list of websites and pagination metadata.
 */
suspend fun Umami.getWebsites(
    search: String? = null,
    page: Int? = null,
    pageSize: Int? = null,
): Websites.Response {
    val request = Websites.Request(
        search = search,
        page = page,
        pageSize = pageSize,
    )

    return getWebsites(request)
}

/**
 * Namespace for the websites API request and response data classes.
 */
interface Websites {
    /**
     * Represents the request parameters for the getWebsites API call.
     * @property search Optional search query to filter websites.
     * @property page Optional page number for pagination (1-indexed).
     * @property pageSize Optional number of websites per page.
     */
    @Serializable
    data class Request(
        @SerialName("search") val search: String? = null,
        @SerialName("page") val page: Int? = null,
        @SerialName("pageSize") val pageSize: Int? = null,
    )

    /**
     * Represents the response from a successful getWebsites API call.
     * @property data The list of websites.
     * @property count The total count of websites.
     * @property page The current page number.
     * @property pageSize The number of websites per page.
     * @property orderBy The field by which the results are ordered.
     */
    @Serializable
    data class Response(
        @SerialName("data") val data: List<Website>,
        @SerialName("count") val count: Long,
        @SerialName("page") val page: Int,
        @SerialName("pageSize") val pageSize: Int,
        @SerialName("orderBy") val orderBy: String,
    )
}
