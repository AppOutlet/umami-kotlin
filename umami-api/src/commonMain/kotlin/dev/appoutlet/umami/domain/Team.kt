package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class Team(
    @SerialName("id")
    val id: String,

    @SerialName("name")
    val name: String,

    @SerialName("accessCode")
    val accessCode: String,

    @SerialName("logoUrl")
    val logoUrl: String? = null,

    @SerialName("createdAt")
    val createdAt: Instant,

    @SerialName("updatedAt")
    val updatedAt: Instant,

    @SerialName("deletedAt")
    val deletedAt: Instant? = null,

    @SerialName("members")
    val members: List<User> = emptyList()
)
