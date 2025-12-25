package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a session in the Umami analytics platform.
 *
 * This data class maps to the session object returned by the `GET /api/me` endpoint.
 *
 * @see <a href="https://docs.umami.is/docs/api/me">Umami API Docs</a>
 */
@Serializable
data class Session(
    /**
     * The authentication token for the session.
     */
    @SerialName("token")
    val token: String,

    /**
     * The authentication key for the session.
     */
    @SerialName("authKey")
    val authKey: String,

    /**
     * The share token for the session, if it exists.
     */
    @SerialName("shareToken")
    val shareToken: String? = null,

    /**
     * The user associated with the session.
     */
    @SerialName("user")
    val user: User,
)
