# Quickstart: Get All Users

This guide demonstrates how to use the new `getUsers` function.

## Prerequisites

- An initialized `Umami` client instance.
- Valid authentication credentials with administrator privileges.

## Usage

The `getUsers` function is an extension on the `Umami` object and accepts optional parameters for searching and pagination.

### Basic Usage

To fetch the first page of users without any filters:

```kotlin
// Assumes 'umami' is an initialized Umami client
val response = umami.getUsers()

println("Total users: ${response.count}")
response.data.forEach { user ->
    println("- ${user.username} (${user.role})")
}
```

### Searching and Pagination

To search for a specific user and retrieve the second page of results:

```kotlin
val response = umami.getUsers(
    search = "admin",
    page = 2,
    pageSize = 10
)

println("Found ${response.count} users matching 'admin'.")
println("Showing page ${response.page} of results.")
response.data.forEach { user ->
    println("- ${user.username}")
}
```
