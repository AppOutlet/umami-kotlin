package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a Link entity in the Umami analytics platform.
 *
 * This data class maps to the link object returned by various Umami API endpoints.
 *
 * @see <a href="https://docs.umami.is/docs/api/links">Umami API Docs</a>
 */
@Serializable
data class Link(
    /**
     * The unique identifier of the link (UUID format).
     */
    @SerialName("id")
    val id: String,

    /**
     * The name of the link.
     */
    @SerialName("name")
    val name: String,

    /**
     * The URL that the link points to.
     */
    @SerialName("url")
    val url: String,

    /**
     * The unique slug identifier for the link.
     */
    @SerialName("slug")
    val slug: String,

    /**
     * The unique identifier of the user who created the link (UUID format).
     */
    @SerialName("userId")
    val userId: String,

    /**
     * The unique identifier of the team the link belongs to (UUID format). Can be null.
     */
    @SerialName("teamId")
    val teamId: String? = null,

    /**
     * The timestamp when the link was created (ISO 8601 format).
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The timestamp when the link was last updated (ISO 8601 format).
     */
    @SerialName("updatedAt")
    val updatedAt: Instant,

    /**
     * The timestamp when the link was deleted (ISO 8601 format). Can be null.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant? = null
)
