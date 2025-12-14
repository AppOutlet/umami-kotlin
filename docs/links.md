# Links API

The `umami-kotlin` library provides a `Links` API for managing links within your Umami instance.

!!! info "New to Umami Kotlin?"
    Before using the Links API, ensure you have initialized the `Umami` client. Refer to our getting started guides for [Kotlin Multiplatform](../getstarted/kmp.md) and [Android](../getstarted/android.md) for setup instructions.

All functions within the `Links` API are `suspend` functions, meaning they must be called from within a coroutine or another `suspend` function.

## Obtaining a Links Instance

You can access the `Links` API functionalities through an extension function on your `Umami` client instance:

```kotlin
// Assuming 'umami' is an initialized Umami client
val linksApi = umami.links()
```

## Retrieving Links

The `getLinks` function allows you to retrieve a paginated list of links.

### Function Signature

```kotlin
suspend fun getLinks(
    search: String? = null,
    page: Int? = null,
    pageSize: Int? = null,
): SearchResponse<Link>
```

### Parameters

*   `search` (Optional `String`): A search string to filter links by.
*   `page` (Optional `Int`): The page number of the results to retrieve. Useful for pagination.
*   `pageSize` (Optional `Int`): The maximum number of link records to return per page.

### Return Type

Returns a `SearchResponse<Link>` object, which encapsulates the list of `Link` objects for the current page, along with pagination metadata.

### Example Usage

```kotlin
import dev.appoutlet.umami.domain.Link
import dev.appoutlet.umami.domain.SearchResponse

// Assuming 'linksApi' is an instance of Links
suspend fun fetchLinks() {
    try {
        // Fetch all links
        val allLinks: SearchResponse<Link> = linksApi.getLinks()
        println("Total links: ${allLinks.count}")
        allLinks.data.forEach { link ->
            println("Link ID: ${link.id}, Name: ${link.name}, URL: ${link.url}")
        }

        // Fetch links matching a search term on page 1 with 5 items per page
        val filteredLinks: SearchResponse<Link> = linksApi.getLinks(
            search = "newsletter",
            page = 1,
            pageSize = 5
        )
        println("Filtered links on page 1: ${filteredLinks.data.size}")

    } catch (e: Exception) {
        println("Error fetching links: ${e.message}")
    }
}
```
