package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a Pixel entity in the Umami analytics platform.
 *
 * This data class maps to the pixel object returned by various Umami API endpoints.
 *
 * @see <a href="https://docs.umami.is/docs/api/pixels">Umami API Docs</a>
 */
@Serializable
data class Pixel(
    /**
     * The unique identifier of the pixel (UUID format).
     */
    @SerialName("id")
    val id: String,
    /**
     * The name of the pixel.
     */
    @SerialName("name")
    val name: String,
    /**
     * The unique slug identifier for the pixel.
     */
    @SerialName("slug")
    val slug: String,
    /**
     * The unique identifier of the user who created the pixel (UUID format).
     */
    @SerialName("userId")
    val userId: String,
    /**
     * The unique identifier of the team the pixel belongs to (UUID format). Can be null.
     */
    @SerialName("teamId")
    val teamId: String?,
    /**
     * The timestamp when the pixel was created (ISO 8601 format).
     */
    @SerialName("createdAt")
    val createdAt: Instant,
    /**
     * The timestamp when the pixel was last updated (ISO 8601 format).
     */
    @SerialName("updatedAt")
    val updatedAt: Instant,
    /**
     * The timestamp when the pixel was deleted (ISO 8601 format). Can be null.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant?,
)
