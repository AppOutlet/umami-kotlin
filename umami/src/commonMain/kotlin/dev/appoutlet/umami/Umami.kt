package dev.appoutlet.umami

import co.touchlab.kermit.Logger
import dev.appoutlet.umami.api.processEventQueueItem
import dev.appoutlet.umami.core.createHttpClient
import dev.appoutlet.umami.core.defaultHttpClientEngine
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.createUserAgent
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.Url
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

/**
 * Default event queue capacity. The capacity can be customized on the Umami object creation.
 * After this number, the queue will wait until the list has room for adding more items in the queue.
 */
const val EVENT_QUEUE_CAPACITY = 25


/**
 * The main entry point for the Umami analytics library.
 * This class is responsible for initializing the Umami configuration and managing the event queue.
 *
 * @param website The UUID of the website to track events for.
 * @param umamiOptions A builder for configuring Umami options, such as the base URL, hostname, and event queue capacity.
 */
@OptIn(ExperimentalUuidApi::class)
class Umami(internal val website: Uuid, umamiOptions: UmamiOptionsBuilder.() -> Unit = {}) {
    private val options = UmamiOptionsBuilder().apply(umamiOptions).build(website)

    /**
     * A mutable map to hold custom headers for HTTP requests.
     * This can be used to add additional headers like authentication tokens or custom metadata.
     */
    internal var headers = mutableMapOf<String, String?>()

    /**
     * An HTTP client for making requests to the Umami API.
     * This client is created lazily to ensure it is initialized only when needed.
     */
    internal val httpClient by lazy { createHttpClient(options.httpClientEngine) }

    /**
     * A channel that acts as an event queue for HTTP requests.
     * This queue allows for asynchronous processing of events, enabling the application to send events
     * without blocking the main thread.
     */
    internal val eventQueue = Channel<HttpRequestBuilder>(capacity = options.eventQueueCapacity)

    /**
     * @param baseUrl The base URL of the Umami API.
     * @param website The UUID of the website to track events for.
     * @param hostname The hostname of the website.
     * @param language The language of the user's browser.
     * @param screen The screen size of the user's device.
     * @param ip The IP address of the user.
     * @param userAgent The user agent of the user's browser.
     * @param eventQueueCapacity The capacity of the event queue.
     * @param httpClientEngine The HTTP client engine to use for requests.
     * @param coroutineScope The coroutine scope to use for background tasks.
     */
    @Deprecated(
        message = "Use the primary constructor with UmamiOptionsBuilder instead.",
        replaceWith = ReplaceWith("Umami(website = ...) { }")
    )
    constructor(
        baseUrl: Url = Url("https://api.umami.is"),
        website: Uuid,
        hostname: Hostname? = null,
        language: Language? = null,
        screen: ScreenSize? = null,
        ip: Ip? = null,
        userAgent: String = createUserAgent(),
        eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY,
        httpClientEngine: HttpClientEngine = defaultHttpClientEngine(),
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ) : this(
        website = website,
        umamiOptions = {
            this.baseUrl = baseUrl
            this.hostname = hostname
            this.language = language
            this.screen = screen
            this.ip = ip
            this.userAgent = userAgent
            this.eventQueueCapacity = eventQueueCapacity
            this.httpClientEngine = httpClientEngine
            this.coroutineScope = coroutineScope
        },
    )

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
        options.coroutineScope.launch {
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
        @Deprecated(
            message = "Use the primary constructor with UmamiOptionsBuilder instead.",
            replaceWith = ReplaceWith("Umami(website = ...) { }")
        )
        fun create(
            baseUrl: String = "https://cloud.umami.is",
            website: String,
            hostname: String? = null,
            language: String? = null,
            screen: String? = null,
            ip: String? = null,
            userAgent: String = createUserAgent(),
            eventQueueCapacity: Int = EVENT_QUEUE_CAPACITY,
            httpClientEngine: HttpClientEngine = defaultHttpClientEngine(),
            coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
        ): Umami {
            return Umami(
                baseUrl = Url(baseUrl),
                website = Uuid.parse(website),
                hostname = hostname?.let(::Hostname),
                language = language?.let(::Language),
                screen = screen?.let(::ScreenSize),
                ip = ip?.let(::Ip),
                userAgent = userAgent,
                eventQueueCapacity = eventQueueCapacity,
                httpClientEngine = httpClientEngine,
                coroutineScope = coroutineScope,
            )
        }
    }
}
