package dev.appoutlet.umami.api.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePixelRequest(
    val name: String? = null,
    val slug: String? = null
)
