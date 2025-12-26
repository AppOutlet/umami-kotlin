package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A member of a team.
 * @see <a href="https://umami.is/docs/api/teams#get-apiteams">Umami API Docs</a>
 */
@Serializable
data class TeamMember(
    /**
     * The unique identifier of the team membership.
     */
    @SerialName("id")
    val id: String,

    /**
     * The ID of the team.
     */
    @SerialName("teamId")
    val teamId: String,

    /**
     * The ID of the user.
     */
    @SerialName("userId")
    val userId: String,

    /**
     * The role of the user in the team.
     */
    @SerialName("role")
    val role: String,

    /**
     * The date and time the user joined the team.
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The date and time the membership was last updated.
     */
    @SerialName("updatedAt")
    val updatedAt: Instant,

    /**
     * The user associated with this membership. This is not always present.
     */
    @SerialName("user")
    val user: TeamMemberUser? = null
)

/**
 * A simplified user object nested within a TeamMember.
 */
@Serializable
data class TeamMemberUser(
    /**
     * The unique identifier of the user.
     */
    @SerialName("id")
    val id: String,

    /**
     * The username of the user.
     */
    @SerialName("username")
    val username: String
)
