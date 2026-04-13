package dev.appoutlet.umami.api

import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.path

private const val UMAMI_CLOUD_BASE_URL = "https://api.umami.is"
private const val UMAMI_CLOUD_PATH_PREFIX = "v1"

/**
 * Represents the base URL configuration for the Umami API.
 *
 * The [url] must include the path prefix (e.g. `/v1` for Cloud, `/api` for self-hosted)
 * and should end with a trailing slash for correct path joining.
 */
sealed class BaseUrl(val url: Url) {

    /**
     * Umami Cloud: `https://api.umami.is/v1/`.
     */
    data object Cloud : BaseUrl(
        URLBuilder(UMAMI_CLOUD_BASE_URL)
            .apply {
                path(UMAMI_CLOUD_PATH_PREFIX.withTrailingSlash())
            }
            .build()
    )

    /**
     * A self-hosted Umami instance.
     *
     * @param url The full base URL **including** the path prefix and trailing slash
     *   (e.g. `Url("https://umami.my-domain.com/api/")`).
     */
    class SelfHosted(url: Url) : BaseUrl(url) {

        /**
         * Convenience constructor that builds the [Url] from a base URL string
         * and an optional path prefix.
         *
         * @param baseUrl The scheme + host portion (e.g. `"https://umami.my-domain.com"`).
         * @param prefix  The API path prefix. Defaults to `"/api"`.
         */
        constructor(
            baseUrl: String,
            prefix: String = "api",
        ) : this(
            URLBuilder(baseUrl).apply {
                path(prefix.withTrailingSlash())
            }.build()
        )
    }
}

fun String.withTrailingSlash(): String {
    return if (this.endsWith("/").not()) "$this/" else this
}
