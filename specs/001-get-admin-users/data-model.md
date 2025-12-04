# Data Model: Get All Users

This document outlines the data structures required for the "Get All Users" feature.

## New Data Classes

File: `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/api/admin/GetAllUsers.kt`

### `GetAllUsers.Request`

Represents the query parameters for the `GET /api/admin/users` endpoint.

```kotlin
@Serializable
data class GetAllUsers.Request(
    val search: String? = null,
    val page: Int? = null,
    val pageSize: Int? = null,
)
```

### `GetAllUsers.Response`

Represents the JSON response from the API.

```kotlin
@Serializable
data class GetAllUsers.Response(
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

```kotlin
@Serializable
data class User(
    val id: String,
    val username: String,
    val role: String,
    val createdAt: String,
    val updatedAt: String?,
    val deletedAt: String?,
    val logoUrl: String?,
    val displayName: String?,
    @SerialName("_count")
    val count: UserCount,
)
```
