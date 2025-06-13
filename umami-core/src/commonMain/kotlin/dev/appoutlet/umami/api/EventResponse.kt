package dev.appoutlet.umami.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventResponse(
    @SerialName("cache") val cache: String?,
    @SerialName("sessionId") val sessionId: String?,
    @SerialName("visitId") val visitId: String?,
    @SerialName("beep") val beep: String?,
)
