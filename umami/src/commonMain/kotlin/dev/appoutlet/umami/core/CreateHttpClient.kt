package dev.appoutlet.umami.core

import dev.appoutlet.umami.Umami
import io.ktor.client.HttpClient
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

fun Umami.createHttpClient() = HttpClient {
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