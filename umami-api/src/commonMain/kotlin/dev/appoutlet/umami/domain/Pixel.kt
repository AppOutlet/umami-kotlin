package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a tracking pixel in the Umami analytics platform. A tracking pixel is a 1x1
 * transparent image used to track website visits or other events.
 *
 * This data class maps to the pixel object returned by the Umami API.
 *
 * @see <a href="https://docs.umami.is/docs/api/pixels">Umami API Docs</a>
 */
@Serializable
data class Pixel(
    /**
     * The unique identifier for the tracking pixel, in UUID format.
     * This ID is used to reference
     * the pixel in API calls.
     */
    @SerialName("id")
    val id: String,
    /**
     * The descriptive name assigned to the tracking pixel for easy identification.
     */
    @SerialName("name")
    val name: String,
    /**
     * The unique URL slug for the tracking pixel. This slug is part of the URL used to trigger
     * the tracking event.
     */
    @SerialName("slug")
    val slug: String,
    /**
     * The unique identifier of the user who owns this tracking pixel, in UUID format.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * The unique identifier of the team that the tracking pixel belongs to, in UUID format.
     * This
     * can be null if the pixel is not associated with a team.
     */
    @SerialName("teamId")
    val teamId: String?,
    /**
     * The timestamp indicating when the tracking pixel was created, in ISO 8601 format.
     */
    @SerialName("createdAt")
    val createdAt: Instant,
    /**
     * The timestamp indicating when the tracking pixel was last updated, in ISO 8601 format.
     */
    @SerialName("updatedAt")
    val updatedAt: Instant,
    /**
     * The timestamp indicating when the tracking pixel was deleted, in ISO 8601 format.
     * This will
     * be null if the pixel has not been deleted.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant?,
)
