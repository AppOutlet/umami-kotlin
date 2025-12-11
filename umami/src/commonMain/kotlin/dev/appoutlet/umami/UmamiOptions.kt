package dev.appoutlet.umami

import dev.appoutlet.umami.core.defaultHttpClientEngine
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.logger.DefaultUmamiLogger
import dev.appoutlet.umami.util.logger.UmamiLogger
import dev.appoutlet.umami.util.createUserAgent
import dev.appoutlet.umami.util.headersmap.InMemoryHeaders
import dev.appoutlet.umami.util.headersmap.SuspendMutableMap
import io.ktor.client.engine.HttpClientEngine
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents the configuration options for the Umami analytics library.
 *
 * @property website The UUID of the website to track events for.
 * @property baseUrl The base URL of the Umami API.
 * @property hostname The hostname of the website.
 * @property language The language of the user's browser.
 * @property screen The screen size of the user's device.
 * @property ip The IP address of the user.
 * @property userAgent The user agent of the user's browser.
 * @property eventQueueCapacity The capacity of the event queue.
 * @property httpClientEngine The HTTP client engine to use for requests.
 * @property coroutineScope The coroutine scope to use for background tasks.
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
    val headers: SuspendMutableMap<String, String?>,
)

/**
 * A builder for creating [UmamiOptions] instances.
 * This class provides a flexible way to configure Umami options using a DSL.
 */
@OptIn(ExperimentalUuidApi::class)
class UmamiOptionsBuilder {
    /** The base URL of the Umami API. Defaults to "https://cloud.umami.is". */
    var baseUrl: Url = Url("https://cloud.umami.is")

    /** The hostname of the website. */
    var hostname: Hostname? = null

    /** The language of the user's browser. */
    var language: Language? = null

    /** The screen size of the user's device. */
    var screenSize: ScreenSize? = null

    /** The IP address of the user. */
    var ip: Ip? = null

    /** The user agent of the user's browser. */
    var userAgent: String = createUserAgent()

    /** The capacity of the event queue. Defaults to [EVENT_QUEUE_CAPACITY]. */
    var eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY

    /** The HTTP client engine to use for requests. */
    var httpClientEngine: HttpClientEngine = defaultHttpClientEngine()

    /** The coroutine scope to use for background tasks. */
    var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    var logger: UmamiLogger = DefaultUmamiLogger()

    val headers: SuspendMutableMap<String, String?> = InMemoryHeaders()

    /** Sets the base URL of the Umami API. */
    fun baseUrl(value: String) { baseUrl = Url(value) }

    /** Sets the hostname of the website. */
    fun hostname(value: String) { hostname = Hostname(value) }

    /** Sets the language of the user's browser. */
    fun language(value: String) { language = Language(value) }

    /** Sets the screen size of the user's device from a string (e.g., "1920x1080"). */
    fun screenSize(value: String) { screenSize = ScreenSize(value) }

    /** Sets the screen size of the user's device from width and height. */
    fun screenSize(width: Int, height: Int) { screenSize = ScreenSize(width = width, height = height) }

    /** Sets the IP address of the user. */
    fun ip(value: String) { ip = Ip(value) }

    /**
     * Builds an [UmamiOptions] instance with the configured properties.
     *
     * @param website The UUID of the website to track events for.
     * @return An [UmamiOptions] instance.
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
