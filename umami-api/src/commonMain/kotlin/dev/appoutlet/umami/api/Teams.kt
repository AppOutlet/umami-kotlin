package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.TeamMember
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
 * Provides functionalities for interacting with Teams in the Umami API.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
@Suppress("TooManyFunctions")
class Teams(private val umami: Umami) {
    /**
     * Retrieves a paginated list of teams.
     *
     * @param page The page number to retrieve.
     * @param pageSize The number of teams to retrieve per page.
     * @return A [SearchResponse] containing a list of [Team] objects.
     */
    suspend fun find(page: Int? = null, pageSize: Int? = null): SearchResponse<Team> {
        return umami.httpClient.get(Api.Teams()) {
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Creates a new team.
     *
     * @param name The name of the team.
     * @return The newly created [Team].
     */
    suspend fun create(name: String): Team {
        return umami.httpClient.post(Api.Teams()) {
            setBody(TeamRequest(name = name))
        }.body()
    }

    /**
     * Joins a team.
     *
     * @param accessCode The access code of the team to join.
     * @return The [TeamMember] object representing the user's membership in the team.
     */
    suspend fun join(accessCode: String): TeamMember {
        return umami.httpClient.post(Api.Teams.Join()) {
            setBody(JoinTeamRequest(accessCode = accessCode))
        }.body()
    }

    /**
     * Retrieves a team by its ID.
     *
     * @param teamId The ID of the team to retrieve.
     * @return The [Team] object.
     */
    suspend fun get(teamId: String): Team {
        return umami.httpClient.get(Api.Teams.Id(teamId = teamId)).body()
    }

    /**
     * Updates a team.
     *
     * @param teamId The ID of the team to update.
     * @param name The new name of the team.
     * @param accessCode The new access code of the team.
     * @return The updated [Team].
     */
    suspend fun update(teamId: String, name: String? = null, accessCode: String? = null): Team {
        return umami.httpClient.post(Api.Teams.Id(teamId = teamId)) {
            setBody(TeamRequest(name = name, accessCode = accessCode))
        }.body()
    }

    /**
     * Deletes a team.
     *
     * @param teamId The ID of the team to delete.
     */
    suspend fun delete(teamId: String) {
        umami.httpClient.delete(Api.Teams.Id(teamId = teamId))
    }

    /**
     * Retrieves the users of a team.
     *
     * @param teamId The ID of the team.
     * @param search An optional search string to filter users by.
     * @param page An optional page number for pagination.
     * @param pageSize An optional number of results per page.
     * @return A list of [TeamMember] objects.
     */
    suspend fun getUsers(
        teamId: String,
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ): SearchResponse<TeamMember> {
        return umami.httpClient.get(Api.Teams.Id.Users(parent = Api.Teams.Id(teamId = teamId))) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    /**
     * Adds a user to a team.
     *
     * @param teamId The ID of the team.
     * @param userId The ID of the user to add.
     * @param role The role of the user in the team.
     * @return The [TeamMember] object representing the new membership.
     */
    suspend fun addUser(teamId: String, userId: String, role: String): TeamMember {
        return umami.httpClient.post(Api.Teams.Id.Users(parent = Api.Teams.Id(teamId = teamId))) {
            setBody(AddUserRequest(userId = userId, role = role))
        }.body()
    }

    /**
     * Retrieves a user from a team.
     *
     * @param teamId The ID of the team.
     * @param userId The ID of the user.
     * @return The [TeamMember] object.
     */
    suspend fun getUser(teamId: String, userId: String): TeamMember {
        return umami.httpClient.get(
            Api.Teams.Id.Users.UserId(
                parent = Api.Teams.Id.Users(parent = Api.Teams.Id(teamId = teamId)),
                userId = userId
            )
        ).body()
    }

    /**
     * Updates a user's role in a team.
     *
     * @param teamId The ID of the team.
     * @param userId The ID of the user.
     * @param role The new role of the user.
     * @return The updated [TeamMember] object.
     */
    suspend fun updateUserRole(teamId: String, userId: String, role: String): TeamMember {
        return umami.httpClient.post(
            Api.Teams.Id.Users.UserId(
                parent = Api.Teams.Id.Users(parent = Api.Teams.Id(teamId = teamId)),
                userId = userId
            )
        ) {
            setBody(UpdateUserRoleRequest(role = role))
        }.body()
    }

    /**
     * Removes a user from a team.
     *
     * @param teamId The ID of the team.
     * @param userId The ID of the user to remove.
     */
    suspend fun removeUser(teamId: String, userId: String) {
        umami.httpClient.delete(
            Api.Teams.Id.Users.UserId(
                parent = Api.Teams.Id.Users(parent = Api.Teams.Id(teamId = teamId)),
                userId = userId
            )
        )
    }

    /**
     * Retrieves the websites of a team.
     *
     * @param teamId The ID of the team.
     * @param search An optional search string to filter websites by.
     * @param page An optional page number for pagination.
     * @param pageSize An optional number of results per page.
     * @return A list of [Website] objects.
     */
    suspend fun getWebsites(
        teamId: String,
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null
    ): SearchResponse<Website> {
        return umami.httpClient.get(Api.Teams.Id.Websites(parent = Api.Teams.Id(teamId = teamId))) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }
}

@Serializable
internal data class TeamRequest(
    @SerialName("name")
    val name: String? = null,
    @SerialName("accessCode")
    val accessCode: String? = null
)

@Serializable
internal data class JoinTeamRequest(
    @SerialName("accessCode")
    val accessCode: String
)

@Serializable
internal data class AddUserRequest(
    @SerialName("userId")
    val userId: String,
    @SerialName("role")
    val role: String
)

@Serializable
internal data class UpdateUserRoleRequest(
    @SerialName("role")
    val role: String
)

/**
 * Extension function to provide easy access to [Teams] functionalities from an [Umami] instance.
 *
 * @return An instance of [Teams].
 */
fun Umami.teams(): Teams = Teams(this)
