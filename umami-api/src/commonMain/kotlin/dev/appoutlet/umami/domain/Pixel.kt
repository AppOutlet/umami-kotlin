package dev.appoutlet.umami.domain

import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a pixel in the Umami API.
 *
 * @property id The unique identifier of the pixel.
 * @property name The name of the pixel.
 * @property slug The URL slug for the pixel.
 * @property userId The ID of the user who owns the pixel.
 * @property teamId The ID of the team the pixel belongs to, if any.
 * @property createdAt The timestamp when the pixel was created.
 * @property updatedAt The timestamp when the pixel was last updated.
 * @property deletedAt The timestamp when the pixel was deleted, if it has been.
 */
@Serializable
data class Pixel(
    val id: String,
    val name: String,
    val slug: String,
    val userId: String,
    val teamId: String?,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Instant?,
)
