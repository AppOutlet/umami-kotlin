package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class User(
    @SerialName("id")
    val id: String,

    @SerialName("username")
    val username: String,

    @SerialName("role")
    val role: String,

    @SerialName("createdAt")
    val createdAt: Instant,

    @SerialName("isAdmin")
    val isAdmin: Boolean = false,

    @SerialName("teams")
    val teams: List<Team> = emptyList(),
)
