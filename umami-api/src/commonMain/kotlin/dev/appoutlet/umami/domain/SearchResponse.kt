package dev.appoutlet.umami.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a generic search response structure from the Umami API.
 * This data class encapsulates paginated results and metadata about the search.
 *
 * @param T The type of data contained in the search results.
 * @property data A list of items of type [T] representing the search results for the current page.
 * @property count The total number of items found across all pages matching the search criteria.
 * @property page The current page number of the search results (0-indexed).
 * @property pageSize The maximum number of items returned per page.
 * @property orderBy An optional string indicating the field by which the results are ordered.
 */
@Serializable
data class SearchResponse<T>(
    @SerialName("data") val data: List<T>,
    @SerialName("count") val count: Long,
    @SerialName("page") val page: Int,
    @SerialName("pageSize") val pageSize: Int,
    @SerialName("orderBy") val orderBy: String? = null,
)
