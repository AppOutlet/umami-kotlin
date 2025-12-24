package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * A team in Umami.
 * @see <a href="https://umami.is/docs/api/teams#get-apiteamsteamid">Umami API Docs</a>
 */
@Serializable
data class Team(
    /**
     * The unique identifier of the team.
     */
    @SerialName("id")
    val id: String,

    /**
     * The name of the team.
     */
    @SerialName("name")
    val name: String,

    /**
     * The access code for the team.
     */
    @SerialName("accessCode")
    val accessCode: String,

    /**
     * The URL of the team's logo.
     */
    @SerialName("logoUrl")
    val logoUrl: String? = null,

    /**
     * The date and time the team was created.
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The date and time the team was last updated.
     */
    @SerialName("updatedAt")
    val updatedAt: Instant? = null,

    /**
     * The date and time the team was deleted.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant? = null,

    /**
     * The members of the team.
     */
    @SerialName("members")
    val members: List<User> = emptyList(),

    /**
     * The number of websites and members in the team.
     */
    @SerialName("_count")
    val count: Count? = null
)
