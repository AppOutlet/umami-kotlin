package dev.appoutlet.umami

import co.touchlab.kermit.Logger
import dev.appoutlet.umami.api.processEventQueueItem
import dev.appoutlet.umami.core.createHttpClient
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.createUserAgent
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Default event queue capacity. The capacity can be customized on the Umami object creation.
 * After this number, the queue will wait until the list has room for adding more items in the queue.
 */
private const val EVENT_QUEUE_CAPACITY = 25

/**
 * The main class for interacting with the Umami API.
 *
 * @constructor Creates an instance of [Umami] with the specified parameters using typesafe parameters.
 * @property baseUrl The base URL of the Umami API. Defaults to "https://api.umami.is". If you are using a self-hosted
 * version of Umami, you need to provide the URL pointing to your instance.
 * @property website The UUID of the website to track events for.
 * @property hostname Optional hostname for the website.
 * @property language Optional language of the user's browser.
 * @property screen Optional screen size of the user's device.
 * @property ip Optional IP address of the user.
 * @property userAgent The user agent string for HTTP requests. Defaults to a generated string using [createUserAgent].
 * @property eventQueueCapacity The capacity of the event queue. Defaults to [EVENT_QUEUE_CAPACITY].
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
    internal val eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY,
) {
    /**
     * Coroutine scope for running background tasks related to Umami.
     * This scope uses the Default dispatcher, which is suitable for CPU-intensive tasks.
     */
    internal val umamiCoroutineScope = CoroutineScope(Dispatchers.Default)

    /**
     * A mutable map to hold custom headers for HTTP requests.
     * This can be used to add additional headers like authentication tokens or custom metadata.
     */
    internal var headers = mutableMapOf<String, String?>()

    /**
     * An HTTP client for making requests to the Umami API.
     * This client is created lazily to ensure it is initialized only when needed.
     */
    internal val httpClient by lazy { createHttpClient() }

    /**
     * A channel that acts as an event queue for HTTP requests.
     * This queue allows for asynchronous processing of events, enabling the application to send events
     * without blocking the main thread.
     */
    internal val eventQueue = Channel<HttpRequestBuilder>(capacity = eventQueueCapacity)

    init {
        Logger.setTag("Umami")
        consumeEventQueue()
    }

    /**
     * Starts consuming the event queue in a coroutine.
     * This function launches a coroutine that continuously processes items from the event queue.
     * Each item is processed by the [processEventQueueItem] function, which sends the event to the Umami API.
     */
    private fun consumeEventQueue() {
        umamiCoroutineScope.launch {
            eventQueue.consumeEach { request -> processEventQueueItem(request) }
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
         * @param userAgent The user agent string for HTTP requests. Defaults to a generated string [createUserAgent].
         * @param eventQueueCapacity The capacity of the event queue. Defaults to [EVENT_QUEUE_CAPACITY].
         * @return An instance of [Umami].
         * @throws IllegalArgumentException if the website UUID string is invalid, or if other string parameters are
         * invalid according to their respective domain classes.
         */
        fun create(
            baseUrl: String = "https://cloud.umami.is",
            website: String,
            hostname: String? = null,
            language: String? = null,
            screen: String? = null,
            ip: String? = null,
            userAgent: String = createUserAgent(),
            eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY
        ): Umami {
            return Umami(
                baseUrl = Url(baseUrl),
                website = Uuid.parse(website),
                hostname = hostname?.let(::Hostname),
                language = language?.let(::Language),
                screen = screen?.let(::ScreenSize),
                ip = ip?.let(::Ip),
                userAgent = userAgent,
                eventQueueCapacity = eventQueueCapacity
            )
        }
    }
}
