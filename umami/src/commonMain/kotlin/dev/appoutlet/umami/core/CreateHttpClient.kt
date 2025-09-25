package dev.appoutlet.umami.core

import dev.appoutlet.umami.Umami
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Creates and configures a new [HttpClient] instance for the [Umami] context.
 *
 * The client is set up with:
 * - Default request settings (base URL, JSON content type, accept header, user agent)
 * - Logging plugin (disabled by default)
 * - Content negotiation using kotlinx.serialization with custom JSON settings
 * - Interceptor to append non-blank headers to each request
 *
 * @receiver [Umami] The context providing configuration values such as baseUrl and userAgent.
 * @return Configured [HttpClient] instance.
 */
internal fun Umami.createHttpClient(engine: HttpClientEngine) = HttpClient(engine) {
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

        headers
            .filterValues { it != null }
            .forEach { (key, value) ->
                if (value.isNullOrBlank()) return@forEach
                requestBuilder.headers.append(name = key, value = value)
            }

        execute(requestBuilder)
    }
}

/**
 * Provides the default [HttpClientEngine] for the current platform.
 *
 * Platform mapping:
 * - Android and JVM: OkHttp
 * - macOS and iOS: Darwin
 * - JS and Wasm: Js
 * - Linux: Curl
 * - Windows: WinHttp
 *
 * @return The correct [HttpClientEngine] implementation for the platform.
 */
expect fun defaultHttpClientEngine(): HttpClientEngine
