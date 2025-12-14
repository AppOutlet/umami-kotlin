package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Link(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String,
    @SerialName("slug")
    val slug: String,
    @SerialName("userId")
    val userId: String,
    @SerialName("teamId")
    val teamId: String? = null,
    @SerialName("createdAt")
    val createdAt: Instant,
    @SerialName("updatedAt")
    val updatedAt: Instant,
    @SerialName("deletedAt")
    val deletedAt: Instant? = null
)
