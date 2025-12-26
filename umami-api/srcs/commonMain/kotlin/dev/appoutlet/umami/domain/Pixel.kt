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
    val createdAt: Instant
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
