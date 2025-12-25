package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a pixel in the Umami API.
 */
@Serializable
data class Pixel(
    /**
     * The unique identifier of the pixel.
     */
    @SerialName("id")
    val id: String,
    /**
     * The name of the pixel.
     */
    @SerialName("name")
    val name: String,
    /**
     * The URL slug for the pixel.
     */
    @SerialName("slug")
    val slug: String,
    /**
     * The ID of the user who owns the pixel.
     */
    @SerialName("userId")
    val userId: String,
    /**
     * The ID of the team the pixel belongs to, if any.
     */
    @SerialName("teamId")
    val teamId: String?,
    /**
     * The timestamp when the pixel was created.
     */
    @SerialName("createdAt")
    val createdAt: Instant,
    /**
     * The timestamp when the pixel was last updated.
     */
    @SerialName("updatedAt")
    val updatedAt: Instant,
    /**
     * The timestamp when the pixel was deleted, if it has been.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant?,
)
