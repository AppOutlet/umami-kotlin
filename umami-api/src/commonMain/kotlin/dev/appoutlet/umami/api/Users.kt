package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
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
 * Provides functionalities for interacting with Users in the Umami API.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Users(private val umami: Umami) {

    /**
     * Creates a new user.
     *
     * @param username The user's username.
     * @param password The user's password.
     * @param role The user's role. Check [UserRole] for available roles.
     * @param id The user's ID (optional).
     * @return The created [User].
     */
    suspend fun create(
        username: String,
        password: String,
        role: String,
        id: String? = null,
    ): User {
        return umami.httpClient.post(Api.Users()) {
            setBody(
                CreateUserRequest(
                    username = username,
                    password = password,
                    role = role,
                    id = id,
                )
            )
        }.body()
    }

    /**
     * Gets a user by ID.
     *
     * @param userId The unique identifier of the user.
     * @return The [User] object.
     */
    suspend fun get(userId: String): User {
        return umami.httpClient.get(Api.Users.UserId(userId = userId)).body()
    }

    /**
     * Updates a user.
     *
     * @param userId The unique identifier of the user.
     * @param username The new username.
     * @param password The new password.
     * @param role The new role.
     * @return The updated [User].
     */
    suspend fun update(
        userId: String,
        username: String? = null,
        password: String? = null,
        role: String? = null,
    ): User {
        return umami.httpClient.post(Api.Users.UserId(userId = userId)) {
            setBody(
                UpdateUserRequest(
                    username = username,
                    password = password,
                    role = role,
                )
            )
        }.body()
    }

    /**
     * Deletes a user.
     *
     * @param userId The unique identifier of the user.
     */
    suspend fun delete(userId: String) {
        umami.httpClient.delete(Api.Users.UserId(userId = userId))
    }

    /**
     * Gets all websites that belong to a user.
     *
     * @param userId The unique identifier of the user.
     * @param includeTeams Set to true if you want to include websites where you are the team owner.
     * @param search Search text.
     * @param page Determines page.
     * @param pageSize Determines how many results to return.
     * @return A [SearchResponse] containing a list of [Website] objects.
     */
    suspend fun getWebsites(
        userId: String,
        includeTeams: Boolean = false,
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Website> {
        return umami.httpClient.get(Api.Users.UserId(userId = userId).Websites()) {
            parameter("includeTeams", includeTeams)
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Gets all teams that belong to a user.
     *
     * @param userId The unique identifier of the user.
     * @param page Determines page.
     * @param pageSize Determines how many results to return.
     * @return A [SearchResponse] containing a list of [Team] objects.
     */
    suspend fun getTeams(
        userId: String,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Team> {
        return umami.httpClient.get(Api.Users.UserId(userId = userId).Teams()) {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }
}

/**
 * Extension function to provide easy access to [Users] functionalities from an [Umami] instance.
 *
 * @return An instance of [Users].
 */
fun Umami.users(): Users = Users(this)

@Serializable
internal data class CreateUserRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("role")
    val role: String,
    @SerialName("id")
    val id: String?,
)

@Serializable
internal data class UpdateUserRequest(
    @SerialName("username")
    val username: String?,
    @SerialName("password")
    val password: String?,
    @SerialName("role")
    val role: String?,
)
