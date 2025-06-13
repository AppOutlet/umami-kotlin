package dev.appoutlet.umami.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class EventRequest(
    @SerialName("type") val type: String,
    @SerialName("payload") val payload: EventPayload,
)

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
