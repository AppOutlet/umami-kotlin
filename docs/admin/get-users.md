# Get Users

The `umami-kotlin` library provides a convenient way to retrieve a paginated and searchable list of all users from your Umami instance. This guide will walk you through using the `getUsers` function to manage and query user data.

!!! info "Prerequisites"
    Before proceeding, ensure you have initialized and authenticated the `Umami` client with appropriate admin privileges. Check out our [authentication guide](../auth.md) and getting started guides for [Kotlin Multiplatform](../getstarted/kmp.md) and [Android](../getstarted/android.md).

The `getUsers` function is a `suspend` function, which means it must be called from within a coroutine or another `suspend` function.

!!! info "Authentication Required"
    This function requires you to be authenticated with admin privileges. If you haven't authenticated yet, please refer to the [Authentication guide](../auth.md) to learn how to log in.

## Overview

The `getUsers` function allows you to:

- Retrieve a list of all users in your Umami instance
- Search for specific users by name
- Paginate through large result sets
- Control the number of results per page

## Basic Usage

There are two ways to call the `getUsers` function: by passing individual parameters directly, or by using a `Users.Request` object.

### Using Individual Parameters

The simplest way to retrieve users is by calling the `getUsers` function with optional parameters.

**Example: Get all users with default pagination**
```kotlin
// Assuming 'umami' is an initialized and authenticated Umami client
val response = umami.getUsers()

println("Total users: ${response.count}")
response.data.forEach { user ->
    println("User: ${user.username} (${user.role})")
}
```

### Using a Request Object

For more control, you can create a `Users.Request` object and pass it to the `getUsers` function.

**Example:**
```kotlin
import dev.appoutlet.umami.api.admin.Users

// Assuming 'umami' is an initialized and authenticated Umami client
val request = Users.Request(
    search = "john",
    page = 1,
    pageSize = 10
)

val response = umami.getUsers(request)
println("Found ${response.data.size} users matching 'john'")
```

## Parameters

The `getUsers` function accepts the following optional parameters:

- **`search`** (String?): A search query to filter users. Defaults to `null` (no filtering).
- **`page`** (Int?): The page number for pagination (1-indexed). Defaults to `null` (uses server default: 1).
- **`pageSize`** (Int?): The number of users to return per page. Defaults to `null` (uses server default).

!!! tip "Search Functionality"
    The `search` parameter is case-insensitive and searches across user attributes like username and display name.

## Response

A successful call to `getUsers` returns a `Users.Response` object with the following properties:

- **`data`**: A list of `User` objects representing the users on the current page.
- **`count`**: The total number of users matching the search criteria (across all pages).
- **`page`**: The current page number.
- **`pageSize`**: The number of users per page.
- **`orderBy`**: The field by which the results are ordered (as determined by the server).

### User Object

Each `User` object in the `data` list contains detailed information about a user:

- **`id`**: The unique identifier (UUID) of the user.
- **`username`**: The username of the user.
- **`role`**: The role of the user (`admin`, `user`, or `view-only`).
- **`createdAt`**: The timestamp when the user was created.
- **`updatedAt`**: The timestamp when the user was last updated (nullable).
- **`deletedAt`**: The timestamp when the user was deleted (nullable).
- **`logoUrl`**: The URL of the user's logo (nullable).
- **`displayName`**: The display name of the user (nullable).
- **`isAdmin`**: A boolean indicating whether the user has administrative privileges.
- **`teams`**: A list of teams the user belongs to.
- **`count`**: Additional count-related information, such as the number of websites managed.

## Usage Examples

### Example 1: List All Users

Retrieve all users without any filters:

```kotlin
val response = umami.getUsers()

println("Total users in system: ${response.count}")
println("Showing page ${response.page} with ${response.pageSize} users per page")

response.data.forEach { user ->
    println("${user.username} - ${user.role} - Created: ${user.createdAt}")
}
```

### Example 2: Search for Specific Users

Search for users whose names match a specific query:

```kotlin
val response = umami.getUsers(search = "admin")

println("Found ${response.count} users matching 'admin'")
response.data.forEach { user ->
    println("- ${user.username} (${user.displayName ?: "No display name"})")
}
```

### Example 3: Display User Details with Team Information

Retrieve users and display their team memberships:

```kotlin
val response = umami.getUsers()

response.data.forEach { user ->
    println("User: ${user.username}")
    println("  Role: ${user.role}")
    println("  Teams: ${user.teams.size}")
    user.teams.forEach { team ->
        println("    - ${team.name}")
    }
}
```

## Error Handling

Like all API calls, `getUsers` can throw exceptions if the request fails. It's recommended to wrap calls in a try-catch block:

```kotlin
try {
    val response = umami.getUsers(search = "john")
    println("Found ${response.count} users")
} catch (e: ClientRequestException) {
    println("Failed to retrieve users: ${e.message}")
} catch (e: Exception) {
    println("An unexpected error occurred: ${e.message}")
}
```

!!! warning "Authentication Required"
    The `getUsers` function requires admin-level authentication. Attempting to call this function without proper authentication or with insufficient privileges will result in a `ClientRequestException` with a 401 or 403 status code.

## Full API Reference

For complete details on the `getUsers` function and related types, refer to the [API documentation](../reference/umami-api/dev.appoutlet.umami.api.admin/get-users.html).
