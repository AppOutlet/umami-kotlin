package dev.appoutlet.umami.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents the response from the Umami API after sending an event.
 *
 * @property cache A cache token that can be used to speed up subsequent requests.
 * @property sessionId The ID of the session.
 * @property visitId The ID of the visit.
 * @property beep An optional field that may contain an error message if the server considered the event invalid.
 */
@Serializable
data class EventResponse(
    @SerialName("cache") val cache: String?,
    @SerialName("sessionId") val sessionId: String?,
    @SerialName("visitId") val visitId: String?,
    @SerialName("beep") val beep: String?,
)
