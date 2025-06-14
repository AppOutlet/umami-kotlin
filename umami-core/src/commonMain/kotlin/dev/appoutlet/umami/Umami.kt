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
