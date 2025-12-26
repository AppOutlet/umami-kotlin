package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a pixel in the Umami API.
 * @see <a href="https://umami.is/docs/api/pixels">Umami API Docs</a>
 */
@Serializable
data class Pixel(
    /**
     * The unique identifier of the pixel.
     */
    @SerialName("id")
    val id: String,

    /**
     * The ID of the website the pixel belongs to.
     */
    @SerialName("websiteId")
    val websiteId: String,

    /**
     * The slug of the pixel.
     */
    @SerialName("slug")
    val slug: String,

    /**
     * The ID of the user who created the pixel.
     */
    @SerialName("userId")
    val userId: String,

    /**
     * The ID of the team the pixel belongs to.
     */
    @SerialName("teamId")
    val teamId: String?,

    /**
     * The name of the pixel.
     */
    @SerialName("name")
    val name: String,

    /**
     * The payload of the pixel.
     */
    @SerialName("payload")
    val payload: PixelPayload,

    /**
     * The timestamp when the pixel was created.
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The timestamp when the pixel was last updated.
     */
    @SerialName("updatedAt")
    val updatedAt: Instant?,

    /**
     * The timestamp when the pixel was deleted.
     */
    @SerialName("deletedAt")
    val deletedAt: Instant?
)

/**
 * Represents the payload of a pixel.
 */
@Serializable
data class PixelPayload(
    /**
     * The type of the pixel.
     */
    @SerialName("type")
    val type: String,

    /**
     * The value of the pixel.
     */
    @SerialName("value")
    val value: String
)
