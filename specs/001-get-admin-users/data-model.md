# Data Model: Get All Users

This document outlines the data structures required for the "Get All Users" feature.

## New Data Classes

File: `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/api/admin/Users.kt`

### `Users.Request`

Represents the query parameters for the `GET /api/admin/users` endpoint.

```kotlin
@Serializable
data class Users.Request(
    val search: String? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
)
```

### `Users.Response`

Represents the JSON response from the API.

```kotlin
@Serializable
data class Users.Response(
    val data: List<User>,
    val count: Long,
    val page: Int,
    val pageSize: Int,
    val orderBy: String,
)
```

### `UserCount`

A nested data class to represent the `_count` object in the `User` model.

```kotlin
@Serializable
data class UserCount(
    val websites: Int
)
```

## Modified Data Classes

File: `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/domain/User.kt`

The `User` class will be updated to match the API response.

### `User`

The `User` class will be extended with new fields to align with the API response for admin users, while preserving existing fields for backward compatibility. All date/time fields will use `kotlin.time.Instant`.

```kotlin
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

    @SerialName("updatedAt")
    val updatedAt: Instant? = null,

    @SerialName("deletedAt")
    val deletedAt: Instant? = null,

    @SerialName("logoUrl")
    val logoUrl: String? = null,

    @SerialName("displayName")
    val displayName: String? = null,

    @SerialName("isAdmin")
    val isAdmin: Boolean = false,

    @SerialName("teams")
    val teams: List<Team> = emptyList(),

    @SerialName("_count")
    val count: UserCount? = null, // Making it nullable as it might not be present in all User API responses
)
```
