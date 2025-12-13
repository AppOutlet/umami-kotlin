package dev.appoutlet.umami

import dev.appoutlet.umami.core.defaultHttpClientEngine
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.createUserAgent
import dev.appoutlet.umami.util.headers.InMemoryHeaders
import dev.appoutlet.umami.util.headers.SuspendMutableMap
import dev.appoutlet.umami.util.logger.DefaultUmamiLogger
import dev.appoutlet.umami.util.logger.UmamiLogger
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents the internal configuration options for the Umami analytics library, created from
 * [UmamiOptionsBuilder]. This class consolidates all settings required for the client to operate.
 *
 * @property website The UUID of the website to track events for.
 * @property baseUrl The base URL of the Umami API endpoint (e.g., "https://cloud.umami.is").
 * @property hostname The hostname of the website being tracked.
 * @property language The language of the user's browser, typically in IETF language tag format (e.g., "en-US").
 * @property screenSize The screen size of the user's device (e.g., "1920x1080").
 * @property ip The IP address of the user.
 * @property userAgent The user agent string of the user's browser or client.
 * @property eventQueueCapacity The maximum number of events to hold in the queue before processing.
 * @property httpClientEngine The Ktor HTTP client engine used for making network requests.
 * @property coroutineScope The coroutine scope used for launching background tasks, such as sending events.
 * @property logger The logger instance for logging internal library messages.
 * @property headers A suspendable map for managing custom HTTP headers to be sent with requests.
 */
@OptIn(ExperimentalUuidApi::class)
internal data class UmamiOptions(
    val website: Uuid,
    val baseUrl: Url,
    val hostname: Hostname?,
    val language: Language?,
    val screenSize: ScreenSize?,
    val ip: Ip?,
    val userAgent: String,
    val eventQueueCapacity: Int,
    val httpClientEngine: HttpClientEngine,
    val coroutineScope: CoroutineScope,
    val logger: UmamiLogger,
    val headers: SuspendMutableMap<String, String>,
)

/**
 * A DSL-style builder for constructing [UmamiOptions] instances, providing a flexible way to
 * configure the Umami client.
 *
 * This builder allows setting various properties such as the API endpoint, tracking parameters,
 * and technical configurations like the HTTP client engine and coroutine scope. After configuring
 * the desired options, the internal `build` method is called to create an immutable
 * [UmamiOptions] object.
 *
 * Example usage:
 * ```
 * val umami = Umami(websiteId = "your-website-id") {
 *     baseUrl("https://my.umami.instance")
 *     hostname("myapp.com")
 *     // ... other configurations
 * }
 * ```
 */
@OptIn(ExperimentalUuidApi::class)
class UmamiOptionsBuilder {
    /** The base URL of the Umami API endpoint. Defaults to "https://cloud.umami.is". */
    var baseUrl: Url = Url("https://cloud.umami.is")

    /** The hostname of the website being tracked (e.g., "myapp.com"). */
    var hostname: Hostname? = null

    /** The language of the user's browser, in IETF language tag format (e.g., "en-US"). */
    var language: Language? = null

    /** The screen size of the user's device (e.g., "1920x1080"). */
    var screenSize: ScreenSize? = null

    /** The IP address of the user. */
    var ip: Ip? = null

    /** The user agent string of the client. Defaults to a generated value. */
    var userAgent: String = createUserAgent()

    /** The maximum number of events to hold in the queue. Defaults to [EVENT_QUEUE_CAPACITY]. */
    var eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY

    /** The Ktor HTTP client engine for network requests. Defaults to the platform's default engine. */
    var httpClientEngine: HttpClientEngine = defaultHttpClientEngine()

    /** The coroutine scope for background tasks. Defaults to [Dispatchers.Default]. */
    var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    /** The logger for internal library messages. Defaults to [DefaultUmamiLogger]. */
    var logger: UmamiLogger = DefaultUmamiLogger()

    /** A suspendable map for managing custom HTTP headers. Defaults to [InMemoryHeaders]. */
    var headers: SuspendMutableMap<String, String> = InMemoryHeaders()

    /** Sets the base URL of the Umami API from a string. */
    fun baseUrl(value: String) { baseUrl = Url(value) }

    /** Sets the hostname of the website being tracked. */
    fun hostname(value: String) { hostname = Hostname(value) }

    /** Sets the language of the user's browser from an IETF language tag string. */
    fun language(value: String) { language = Language(value) }

    /** Sets the screen size from a string in "widthxheight" format (e.g., "1920x1080"). */
    fun screenSize(value: String) { screenSize = ScreenSize(value) }

    /** Sets the screen size from explicit width and height values. */
    fun screenSize(width: Int, height: Int) { screenSize = ScreenSize(width = width, height = height) }

    /** Sets the IP address of the user. */
    fun ip(value: String) { ip = Ip(value) }

    /**
     * Builds an immutable [UmamiOptions] instance from the current builder configuration.
     * This method is intended for internal use by the library.
     *
     * @param website The UUID of the website to be tracked.
     * @return An [UmamiOptions] instance with the configured settings.
     */
    internal fun build(website: Uuid): UmamiOptions {
        return UmamiOptions(
            website = website,
            baseUrl = baseUrl,
            hostname = hostname,
            language = language,
            screenSize = screenSize,
            ip = ip,
            userAgent = userAgent,
            eventQueueCapacity = eventQueueCapacity,
            httpClientEngine = httpClientEngine,
            coroutineScope = coroutineScope,
            logger = logger,
            headers = headers
        )
    }
}
