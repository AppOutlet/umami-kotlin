# Get Websites

The `umami-kotlin` library provides a convenient way to retrieve a paginated and searchable list of all websites from your Umami instance. This guide will walk you through using the `getWebsites` function to manage and query website data.

!!! info "Prerequisites"
    Before proceeding, ensure you have initialized and authenticated the `Umami` client with appropriate admin privileges. Check out our [authentication guide](../auth.md) and getting started guides for [Kotlin Multiplatform](../getstarted/kmp.md) and [Android](../getstarted/android.md).

The `getWebsites` function is a `suspend` function, which means it must be called from within a coroutine or another `suspend` function.

!!! info "Authentication Required"
    This function requires you to be authenticated with admin privileges. If you haven't authenticated yet, please refer to the [Authentication guide](../auth.md) to learn how to log in.

## Overview

The `getWebsites` function allows you to:

- Retrieve a list of all websites in your Umami instance
- Search for specific websites by name or other attributes
- Paginate through large result sets
- Control the number of results per page

## Basic Usage

There are two ways to call the `getWebsites` function: by passing individual parameters directly, or by using a `Websites.Request` object.

### Using Individual Parameters

The simplest way to retrieve websites is by calling the `getWebsites` function with optional parameters.

**Example: Get all websites with default pagination**
```kotlin
// Assuming 'umami' is an initialized and authenticated Umami client
val response = umami.getWebsites()

println("Total websites: ${response.count}")
response.data.forEach { website ->
    println("Website: ${website.name} - ${website.domain}")
}
```

### Using a Request Object

For more control, you can create a `Websites.Request` object and pass it to the `getWebsites` function.

**Example:**
```kotlin
import dev.appoutlet.umami.api.admin.Websites

// Assuming 'umami' is an initialized and authenticated Umami client
val request = Websites.Request(
    search = "example",
    page = 1,
    pageSize = 10
)

val response = umami.getWebsites(request)
println("Found ${response.data.size} websites matching 'example'")
```

## Parameters

The `getWebsites` function accepts the following optional parameters:

- **`search`** (String?): A search query to filter websites by name or other attributes. Defaults to `null` (no filtering).
- **`page`** (Int?): The page number for pagination (1-indexed). Defaults to `null` (uses server default: 1).
- **`pageSize`** (Int?): The number of websites to return per page. Defaults to `null` (uses server default).

!!! tip "Search Functionality"
    The `search` parameter is case-insensitive and searches across website attributes like name and domain.

## Response

A successful call to `getWebsites` returns a `Websites.Response` object with the following properties:

- **`data`**: A list of `Website` objects representing the websites on the current page.
- **`count`**: The total number of websites matching the search criteria (across all pages).
- **`page`**: The current page number.
- **`pageSize`**: The number of websites per page.
- **`orderBy`**: The field by which the results are ordered (as determined by the server).

### Website Object

Each `Website` object in the `data` list contains detailed information about a website:

- **`id`**: The unique identifier (UUID) of the website.
- **`name`**: The name of the website.
- **`domain`**: The domain of the website.
- **`shareId`**: The share ID of the website (nullable).
- **`resetAt`**: The timestamp when the website statistics were last reset (nullable).
- **`createdAt`**: The timestamp when the website was created.
- **`updatedAt`**: The timestamp when the website was last updated (nullable).
- **`deletedAt`**: The timestamp when the website was deleted (nullable).
- **`userId`**: The ID of the user who owns the website.

## Usage Examples

### Example 1: List All Websites

Retrieve all websites without any filters:

```kotlin
val response = umami.getWebsites()

println("Total websites in system: ${response.count}")
println("Showing page ${response.page} with ${response.pageSize} websites per page")

response.data.forEach { website ->
    println("${website.name} - ${website.domain} - Created: ${website.createdAt}")
}
```

### Example 2: Search for Specific Websites

Search for websites whose names or domains match a specific query:

```kotlin
val response = umami.getWebsites(search = "blog")

println("Found ${response.count} websites matching 'blog'")
response.data.forEach { website ->
    println("- ${website.name} (${website.domain})")
}
```

### Example 3: Paginate Through Websites

Retrieve websites with custom pagination:

```kotlin
val pageSize = 5
var currentPage = 1
var totalPages = 1

do {
    val response = umami.getWebsites(
        page = currentPage,
        pageSize = pageSize
    )
    
    totalPages = (response.count + pageSize - 1) / pageSize
    
    println("Page $currentPage of $totalPages:")
    response.data.forEach { website ->
        println("  ${website.name} - ${website.domain}")
    }
    
    currentPage++
} while (currentPage <= totalPages)
```

### Example 4: Display Website Details with Share Links

Retrieve websites and display their share IDs if available:

```kotlin
val response = umami.getWebsites()

response.data.forEach { website ->
    println("Website: ${website.name}")
    println("  Domain: ${website.domain}")
    println("  Owner ID: ${website.userId}")
    website.shareId?.let { shareId ->
        println("  Share ID: $shareId")
    } ?: println("  Share ID: Not shared")
}
```

## Error Handling

Like all API calls, `getWebsites` can throw exceptions if the request fails. It's recommended to wrap calls in a try-catch block:

```kotlin
try {
    val response = umami.getWebsites(search = "example")
    println("Found ${response.count} websites")
} catch (e: ClientRequestException) {
    println("Failed to retrieve websites: ${e.message}")
} catch (e: Exception) {
    println("An unexpected error occurred: ${e.message}")
}
```

!!! warning "Authentication Required"
    The `getWebsites` function requires admin-level authentication. Attempting to call this function without proper authentication or with insufficient privileges will result in a `ClientRequestException` with a 401 or 403 status code.

## Full API Reference

For complete details on the `getWebsites` function and related types, refer to the [API documentation](../reference/umami-api/dev.appoutlet.umami.api.admin/get-websites.html).
