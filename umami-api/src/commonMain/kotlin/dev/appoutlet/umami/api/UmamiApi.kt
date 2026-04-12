package dev.appoutlet.umami.api

import dev.appoutlet.umami.util.annotation.InternalUmamiApi
import dev.appoutlet.umami.util.headers.SuspendMutableMap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * The main entry point for the Umami REST API.
 * This class handles interactions with the Umami API (e.g. websites, users, teams)
 * rather than simple event tracking.
 *
 * @param block DSL to configure the API settings.
 */
class UmamiApi(block: UmamiApiConfig.() -> Unit = {}) {

    private val config = UmamiApiConfig().apply(block)

    /**
     * A suspendable map for managing custom HTTP headers.
     * Accessible so feature classes can get/set items like Authorization.
     */
    @InternalUmamiApi
    val headers: SuspendMutableMap<String, String> = config.headers

    /**
     * An HTTP client for making requests to the Umami API.
     * This client is configured for the REST API and is not intended for public use directly.
     */
    @InternalUmamiApi
    val httpClient by lazy {
        HttpClient(config.httpClientEngine) {
            expectSuccess = true

            defaultRequest {
                url(config.baseUrl.toString())
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        config.logger.info(message)
                    }
                }
                level = LogLevel.ALL
            }

            install(Resources)

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
                headers.entries().forEach { (key, value) ->
                    requestBuilder.headers.append(name = key, value = value)
                }

                execute(requestBuilder)
            }
        }
    }
}
