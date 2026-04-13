package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Session
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.Website
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Provides functionalities for interacting with the Me API in Umami.
 *
 * @param umami The [UmamiApi] instance used for making HTTP requests.
 */
class Me(private val api: UmamiApi) {

    /**
     * Retrieves the current session information for the authenticated user.
     * @return A [Session] object containing the user's session details.
     */
    suspend fun getSession(): Session {
        return api.httpClient.get("me").body()
    }

    /**
     * Retrieves a list of teams for the authenticated user.
     * @return A [SearchResponse] containing a list of [Team] objects.
     */
    suspend fun getTeams(): SearchResponse<Team> {
        return api.httpClient.get("me/teams").body()
    }

    /**
     * Retrieves a list of websites for the authenticated user.
     * @param includeTeams Optional flag to include websites from teams.
     * @return A [SearchResponse] containing a list of [Website] objects.
     */
    suspend fun getWebsites(
        includeTeams: Boolean? = null,
    ): SearchResponse<Website> {
        return api.httpClient.get("me/websites") {
            parameter("includeTeams", includeTeams)
        }.body()
    }
}

/**
 * Extension function to provide easy access to [Me] functionalities from an [UmamiApi] instance.
 *
 * @return An instance of [Me].
 */
fun UmamiApi.me(): Me = Me(this)
