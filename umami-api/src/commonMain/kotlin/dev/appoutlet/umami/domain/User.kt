package dev.appoutlet.umami.domain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

/**
 * Represents a user in the Umami analytics platform.
 *
 * This data class maps to the user object returned by various Umami API endpoints
 * including GET /api/users/:userId and GET /api/users.
 *
 * @see <a href="https://umami.is/docs/api/users">Umami API Docs</a>
 */
@Serializable
data class User(
    /**
     * The unique identifier of the user (UUID format).
     */
    @SerialName("id")
    val id: String,

    /**
     * The username of the user (maximum 255 characters).
     */
    @SerialName("username")
    val username: String,

    /**
     * The role of the user.
     *
     * Possible values:
     * - `admin`: Full administrative access
     * - `user`: Standard user access
     * - `view-only`: Read-only access
     */
    @SerialName("role")
    val role: String,

    /**
     * The timestamp when the user was created (ISO 8601 format).
     */
    @SerialName("createdAt")
    val createdAt: Instant,

    /**
     * The timestamp when the user was last updated (ISO 8601 format).
     */
    @SerialName("updatedAt")
    val updatedAt: Instant? = null,

    /**
     * The timestamp when the user was deleted (ISO 8601 format).
     */
    @SerialName("deletedAt")
    val deletedAt: Instant? = null,

    /**
     * The URL of the user's logo.
     */
    @SerialName("logoUrl")
    val logoUrl: String? = null,

    /**
     * The display name of the user.
     */
    @SerialName("displayName")
    val displayName: String? = null,

    /**
     * Indicates whether the user has administrative privileges.
     *
     * This is typically a derived property based on the role field.
     */
    @SerialName("isAdmin")
    val isAdmin: Boolean = false,

    /**
     * The list of teams the user belongs to.
     *
     * This field is populated when the user data includes team memberships,
     * such as in responses from GET /api/users/:userId/teams.
     */
    @SerialName("teams")
    val teams: List<Team> = emptyList(),

    /**
     * Additional count-related information for the user,
     * such as the number of websites they manage.
     */
    @SerialName("_count")
    val count: UserCount? = null,
)
