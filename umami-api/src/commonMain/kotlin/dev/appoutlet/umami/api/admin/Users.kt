package dev.appoutlet.umami.api.admin

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.Api
import dev.appoutlet.umami.domain.User
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Retrieves a paginated and searchable list of all users.
 *
 * @param request The request object containing search, page, and pageSize parameters.
 * @return A [Users.Response] object containing the list of users and pagination metadata.
 */
suspend fun Umami.getAllUsers(request: Users.Request): Users.Response {
    return httpClient.get(Api.Admin.Users()) {
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
 * Retrieves a paginated and searchable list of all users.
 * This is a convenience function that creates a [Users.Request] object.
 *
 * @param search Optional search query to filter users by name or other attributes.
 * @param page Optional page number for pagination (1-indexed).
 * @param pageSize Optional number of users per page.
 * @return A [Users.Response] object containing the list of users and pagination metadata.
 */
suspend fun Umami.getAllUsers(
    search: String? = null,
    page: Int? = null,
    pageSize: Int? = null,
): Users.Response {
    val request = Users.Request(
        search = search,
        page = page,
        pageSize = pageSize,
    )

    return getAllUsers(request)
}

/**
 * Namespace for the users API request and response data classes.
 */
interface Users {
    /**
     * Represents the request parameters for the getAllUsers API call.
     * @property search Optional search query to filter users.
     * @property page Optional page number for pagination (1-indexed).
     * @property pageSize Optional number of users per page.
     */
    @Serializable
    data class Request(
        @SerialName("search") val search: String? = null,
        @SerialName("page") val page: Int? = null,
        @SerialName("pageSize") val pageSize: Int? = null,
    )

    /**
     * Represents the response from a successful getAllUsers API call.
     * @property data The list of users.
     * @property count The total count of users.
     * @property page The current page number.
     * @property pageSize The number of users per page.
     * @property orderBy The field by which the results are ordered.
     */
    @Serializable
    data class Response(
        @SerialName("data") val data: List<User>,
        @SerialName("count") val count: Long,
        @SerialName("page") val page: Int,
        @SerialName("pageSize") val pageSize: Int,
        @SerialName("orderBy") val orderBy: String,
    )
}
