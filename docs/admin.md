# Admin API

The `umami-kotlin` library provides a dedicated `Admin` API for performing administrative tasks within your Umami instance. This includes fetching paginated lists of users and websites with various filtering options.

!!! info "New to Umami Kotlin?"
    Before using the Admin API, ensure you have initialized the `Umami` client. Refer to our getting started guides for [Kotlin Multiplatform](../getstarted/kmp.md) and [Android](../getstarted/android.md) for setup instructions.

All functions within the `Admin` API are `suspend` functions, meaning they must be called from within a coroutine or another `suspend` function.

## Obtaining an Admin Instance

You can access the `Admin` API functionalities through an extension function on your `Umami` client instance:

```kotlin
// Assuming 'umami' is an initialized Umami client
val adminApi = umami.admin()
```

## Retrieving Users

The `getUsers` function allows you to retrieve a paginated list of registered users.

### Function Signature

```kotlin
suspend fun getUsers(
    search: String? = null,
    page: Int? = null,
    pageSize: Int? = null,
): SearchResponse<User>
```

### Parameters

*   `search` (Optional `String`): A search string to filter users by their username or other relevant fields.
*   `page` (Optional `Int`): The page number of the results to retrieve. Useful for pagination.
*   `pageSize` (Optional `Int`): The maximum number of user records to return per page.

### Return Type

Returns a `SearchResponse<User>` object, which encapsulates the list of `User` objects for the current page, along with pagination metadata.

### Example Usage

```kotlin
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.SearchResponse

// Assuming 'adminApi' is an instance of Admin
suspend fun fetchUsers() {
    try {
        // Fetch all users
        val allUsers: SearchResponse<User> = adminApi.getUsers()
        println("Total users: ${allUsers.count}")
        allUsers.data.forEach { user ->
            println("User ID: ${user.id}, Username: ${user.username}, Role: ${user.role}")
        }

        // Fetch users matching a search term on page 2 with 10 items per page
        val filteredUsers: SearchResponse<User> = adminApi.getUsers(
            search = "admin",
            page = 2,
            pageSize = 10
        )
        println("Filtered users on page 2: ${filteredUsers.data.size}")

    } catch (e: Exception) {
        println("Error fetching users: ${e.message}")
    }
}
```

## Retrieving Websites

The `getWebsites` function enables you to retrieve a paginated list of registered websites.

### Function Signature

```kotlin
suspend fun getWebsites(
    search: String? = null,
    page: Int? = null,
    pageSize: Int? = null,
): SearchResponse<Website>
```

### Parameters

*   `search` (Optional `String`): A search string to filter websites by their name, domain, or other relevant fields.
*   `page` (Optional `Int`): The page number of the results to retrieve. Useful for pagination.
*   `pageSize` (Optional `Int`): The maximum number of website records to return per page.

### Return Type

Returns a `SearchResponse<Website>` object, which encapsulates the list of `Website` objects for the current page, along with pagination metadata.

### Example Usage

```kotlin
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.domain.SearchResponse

// Assuming 'adminApi' is an instance of Admin
suspend fun fetchWebsites() {
    try {
        // Fetch all websites
        val allWebsites: SearchResponse<Website> = adminApi.getWebsites()
        println("Total websites: ${allWebsites.count}")
        allWebsites.data.forEach { website ->
            println("Website ID: ${website.id}, Name: ${website.name}, Domain: ${website.domain}")
        }

        // Fetch websites matching a search term on page 1 with 5 items per page
        val filteredWebsites: SearchResponse<Website> = adminApi.getWebsites(
            search = "myblog",
            page = 1,
            pageSize = 5
        )
        println("Filtered websites on page 1: ${filteredWebsites.data.size}")

    } catch (e: Exception) {
        println("Error fetching websites: ${e.message}")
    }
}
```

## Understanding `SearchResponse<T>`

Both `getUsers` and `getWebsites` return a `SearchResponse<T>`, which is a generic data class designed to handle paginated API results.

It contains the following properties:

*   `data: List<T>`: A list of the actual items (e.g., `User` or `Website` objects) for the current page.
*   `count: Long`: The total number of items found across all pages matching the search criteria.
*   `page: Int`: The current page number (0-indexed).
*   `pageSize: Int`: The number of items requested per page.
*   `orderBy: String?`: An optional string indicating the field by which the results are sorted.
