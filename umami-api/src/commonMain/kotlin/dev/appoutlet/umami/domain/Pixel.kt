package dev.appoutlet.umami.domain

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

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