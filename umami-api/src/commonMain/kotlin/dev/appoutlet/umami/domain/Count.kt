package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A simple data class to represent a count of items.
 *
 * @property websites The number of websites.
 * @property members The number of members.
 */
@Serializable
data class Count(
    @SerialName("websites")
    val websites: Int,
    @SerialName("members")
    val members: Int,
)
