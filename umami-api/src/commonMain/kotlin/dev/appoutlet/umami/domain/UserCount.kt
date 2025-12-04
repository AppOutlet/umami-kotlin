package dev.appoutlet.umami.domain

import kotlinx.serialization.Serializable

/**
 * A nested data class to represent the _count object in the User model.
 */
@Serializable
data class UserCount(
    /**
     * The number of websites the user has access to.
     */
    val websites: Int
)
