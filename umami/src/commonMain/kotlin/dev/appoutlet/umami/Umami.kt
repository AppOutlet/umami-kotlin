package dev.appoutlet.umami

import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.createUserAgent
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.Url
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * The main class for interacting with the Umami API.
 *
 * @property baseUrl The base URL of the Umami API. Defaults to "https://api.umami.is". If you are using a self-hosted version of Umami, you need to provide the URL pointing to your instance.
 * @property website The UUID of the website to track events for.
 * @property hostname Optional hostname for the website.
 * @property language Optional language of the user's browser.
 * @property screen Optional screen size of the user's device.
 * @property ip Optional IP address of the user.
 * @property userAgent The user agent string for HTTP requests. Defaults to a generated string using [createUserAgent].
 */
@OptIn(ExperimentalUuidApi::class)
@Suppress("LongParameterList")
class Umami(
    internal val baseUrl: Url = Url("https://api.umami.is"),
    internal val website: Uuid,
    internal val hostname: Hostname? = null,
    internal val language: Language? = null,
    internal val screen: ScreenSize? = null,
    internal val ip: Ip? = null,
    internal val userAgent: String = createUserAgent(),
) {
    internal var cache: String? = null
    internal val httpClient by lazy {
        HttpClient {
            expectSuccess = true

            defaultRequest {
                url(baseUrl.toString())
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                userAgent(userAgent)
            }

            install(Logging) {
                level = LogLevel.NONE
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = false
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                        explicitNulls = false
                    }
                )
            }
        }.apply {
            plugin(HttpSend).intercept { requestBuilder ->
                cache?.let { requestBuilder.headers.append("x-umami-cache", it) }
                execute(requestBuilder)
            }
        }
    }

    companion object {
        /**
         * Creates an [Umami] instance with string inputs, which are then parsed into appropriate types.
         *
         * @param baseUrl The base URL of the Umami API. Defaults to "https://api.umami.is".
         * @param website The UUID string of the website to track events for.
         * @param hostname Optional hostname string for the website.
         * @param language Optional language string of the user's browser.
         * @param screen Optional screen size string of the user's device (e.g., "1920x1080").
         * @param ip Optional IP address string of the user.
         * @param userAgent The user agent string for HTTP requests. Defaults to a generated string using [createUserAgent].
         * @return An instance of [Umami].
         * @throws IllegalArgumentException if the website UUID string is invalid, or if other string parameters are invalid according to their respective domain classes.
         */
        fun create(
            baseUrl: String = "https://api.umami.is",
            website: String,
            hostname: String? = null,
            language: String? = null,
            screen: String? = null,
            ip: String? = null,
            userAgent: String = createUserAgent(),
        ): Umami {
            return Umami(
                baseUrl = Url(baseUrl),
                website = Uuid.parse(website),
                hostname = hostname?.let(::Hostname),
                language = language?.let(::Language),
                screen = screen?.let(::ScreenSize),
                ip = ip?.let(::Ip),
                userAgent = userAgent
            )
        }
    }
}
