package dev.appoutlet.umami.api

import dev.appoutlet.umami.core.defaultHttpClientEngine
import dev.appoutlet.umami.util.headers.InMemoryHeaders
import dev.appoutlet.umami.util.headers.SuspendMutableMap
import dev.appoutlet.umami.util.logger.DefaultUmamiLogger
import dev.appoutlet.umami.util.logger.UmamiLogger
import io.ktor.client.engine.HttpClientEngine

/**
 * A DSL-style builder for configuring [UmamiApi].
 * This configuration focuses purely on REST API interaction requirements.
 */
class UmamiApiConfig {
    /**
     * The base URL of the Umami instance.
     * Defaults to [BaseUrl.Cloud] (`https://api.umami.is/v1/`).
     *
     * For self-hosted instances, use [BaseUrl.SelfHosted]:
     * ```
     * baseUrl = BaseUrl.SelfHosted("https://umami.my-domain.com")
     * ```
     */
    var baseUrl: BaseUrl = BaseUrl.Cloud

    /**
     * The Ktor HTTP client engine used for making requests.
     * Defaults to the platform's default engine.
     */
    var httpClientEngine: HttpClientEngine = defaultHttpClientEngine()

    /**
     * The logger implementation to use for network requests.
     * Defaults to [DefaultUmamiLogger].
     */
    var logger: UmamiLogger = DefaultUmamiLogger()

    /**
     * A suspendable map to hold dynamic headers (e.g., Authorization)
     * applied to all requests.
     * Defaults to [InMemoryHeaders].
     */
    var headers: SuspendMutableMap<String, String> = InMemoryHeaders()

    /**
     * Convenience function to set the base URL for a self-hosted instance.
     *
     * Equivalent to `baseUrl = BaseUrl.SelfHosted(url)`.
     * The default `/api` prefix will be appended automatically.
     *
     * @param url The base URL string (e.g. `"https://umami.my-domain.com"`).
     */
    fun baseUrl(url: String) {
        baseUrl = BaseUrl.SelfHosted(url)
    }
}
