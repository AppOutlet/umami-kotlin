package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.Website
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.parameter

/**
 * Provides administrative functionalities for interacting with the Umami API.
 * This class allows fetching lists of users and websites with optional search and pagination.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Admin(private val umami: Umami) {
    /**
     * Retrieves a paginated list of users from the Umami API.
     *
     * @param search Optional search string to filter users by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [User] objects.
     */
    suspend fun getUsers(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<User> {
        return umami.httpClient.get(Api.Admin.Users()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Retrieves a paginated list of websites from the Umami API.
     *
     * @param search Optional search string to filter websites by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [Website] objects.
     */
    suspend fun getWebsites(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Website> {
        return umami.httpClient.get(Api.Admin.Websites()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Retrieves a paginated list of teams from the Umami API.
     *
     * @param search Optional search string to filter teams by.
     * @param page Optional page number for pagination.
     * @param pageSize Optional number of results per page.
     * @return A [SearchResponse] containing a list of [Team] objects.
     */
    suspend fun getTeams(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Team> {
        return umami.httpClient.get(Api.Admin.Teams()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }
}

/**
 * Extension function to provide easy access to [Admin] functionalities from an [Umami] instance.
 *
 * @return An instance of [Admin].
 */
fun Umami.admin(): Admin = Admin(this)
