package dev.appoutlet.umami.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the request body for sending an event to the Umami API.
 *
 * @property type The type of the event (e.g., "identify", "event").
 * @property payload The actual event data.
 */
@Serializable
internal data class EventRequest(
    @SerialName("type") val type: String,
    @SerialName("payload") val payload: EventPayload,
)

/**
 * Represents the payload of an event.
 *
 * @property website The ID of the website.
 * @property data Additional data for the event.
 * @property hostname The hostname of the website.
 * @property language The language of the browser.
 * @property referrer The referrer URL.
 * @property screen The screen resolution.
 * @property title The title of the page.
 * @property url The URL of the page.
 * @property name The name of the event.
 * @property tag A tag for the event.
 * @property ip The IP address of the user.
 * @property userAgent The user agent string.
 * @property timestamp The timestamp of the event.
 * @property id The ID of the event.
 */
@Serializable
internal data class EventPayload(
    @SerialName("website") val website: String,
    @SerialName("data") val data: Map<String, String?>?,
    @SerialName("hostname") val hostname: String?,
    @SerialName("language") val language: String?,
    @SerialName("referrer") val referrer: String?,
    @SerialName("screen") val screen: String?,
    @SerialName("title") val title: String?,
    @SerialName("url") val url: String?,
    @SerialName("name") val name: String?,
    @SerialName("tag") val tag: String?,
    @SerialName("ip") val ip: String?,
    @SerialName("userAgent") val userAgent: String?,
    @SerialName("timestamp") val timestamp: Long?,
    @SerialName("id") val id: String?,
)
