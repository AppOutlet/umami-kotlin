package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a website in the Umami analytics platform.
 *
 * This data class maps to the website object returned by various Umami API endpoints
 * including GET /api/websites/:websiteId and GET /api/websites.
 *
 * @see <a href="https://umami.is/docs/api/websites">Umami API Docs</a>
 */
@Serializable
data class Website(
    /**
     * The unique identifier of the website (UUID format).
     */
    @SerialName("id")
    val id: String,

    /**
     * The name of the website.
     */
    @SerialName("name")
    val name: String,

    /**
     * The domain of the website.
     */
    @SerialName("domain")
    val domain: String,

    /**
     * The share ID of the website, if it is shared publicly.
     */
    @SerialName("shareId")
    val shareId: String? = null,

    /**
     * The timestamp when the website's statistics were reset (ISO 8601 format).
     */
    @SerialName("resetAt")
    val resetAt: Instant? = null,

    /**
     * The unique identifier of the user who owns the website (UUID format).
     */
    @SerialName("userId")
    val userId: String,

    /**
     * The unique identifier of the team that owns the website (UUID format).
     */
    @SerialName("teamId")
    val teamId: String? = null,

    /**
     * The unique identifier of the user who created the website (UUID format).
     */
    @SerialName("createdBy")
    val createdBy: String,

    /**
     * The timestamp when the website was created (ISO 8601 format).
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The timestamp when the website was last updated (ISO 8601 format).
     */
    @SerialName("updatedAt")
    val updatedAt: Instant? = null,

    /**
     * The timestamp when the website was deleted (ISO 8601 format).
     */
    @SerialName("deletedAt")
    val deletedAt: Instant? = null,

    /**
     * The user who owns the website.
     */
    @SerialName("user")
    val user: User? = null,

    /**
     * The team that owns the website.
     */
    @SerialName("team")
    val team: Team? = null,
)
