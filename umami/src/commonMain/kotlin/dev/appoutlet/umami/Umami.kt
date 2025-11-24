package dev.appoutlet.umami

import dev.appoutlet.umami.api.processEventQueueItem
import dev.appoutlet.umami.core.createHttpClient
import dev.appoutlet.umami.util.annotation.InternalUmamiApi
import io.ktor.client.request.HttpRequestBuilder
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Default event queue capacity. The capacity can be customized on the Umami object creation.
 * After this number, the queue will wait until the list has room for adding more items in the queue.
 */
const val EVENT_QUEUE_CAPACITY = 25

/**
 * The main entry point for the Umami analytics library.
 * This class is responsible for initializing the Umami configuration and managing the event queue.
 *
 * @property website The UUID of the website to track events for.
 * @param enableEventQueue A flag to enable or disable the event queue.
 * @param umamiOptions A builder for configuring Umami options.
 * @see InternalUmamiApi This is an internal API and may be changed in the future.
 */
@OptIn(ExperimentalUuidApi::class)
class Umami @InternalUmamiApi constructor(
    internal val website: Uuid,
    private val enableEventQueue: Boolean = true,
    umamiOptions: UmamiOptionsBuilder.() -> Unit = {},
) {
    internal val options = UmamiOptionsBuilder().apply(umamiOptions).build(website)

    /**
     * A mutable map to hold custom headers for HTTP requests.
     * This can be used to add additional headers like authentication tokens or custom metadata.
     *
     * Note: This property is marked with [InternalUmamiApi] and is not intended for public use.
     * Its behavior may change in future releases.
     */
    @InternalUmamiApi
    var headers = mutableMapOf<String, String?>()

    /**
     * An HTTP client for making requests to the Umami API.
     * This client is created lazily to ensure it is initialized only when needed.
     *
     * Note: This property is marked with [InternalUmamiApi] and is not intended for public use.
     * Its behavior may change in future releases.
     */
    @InternalUmamiApi
    val httpClient by lazy { createHttpClient(options.httpClientEngine) }

    /**
     * A channel that acts as an event queue for HTTP requests.
     * This queue allows for asynchronous processing of events, enabling the application to send events
     * without blocking the main thread.
     */
    internal val eventQueue = Channel<HttpRequestBuilder>(capacity = options.eventQueueCapacity)

    /** [Job] to control the event queue */
    internal lateinit var eventQueueJob: Job

    /**
     * Creates an Umami instance.
     *
     * @param website The UUID of the website to track events for (as a String).
     * @param umamiOptions A builder for configuring Umami options, such as the base URL, hostname, and queue capacity.
     */
    constructor(
        website: String,
        umamiOptions: UmamiOptionsBuilder.() -> Unit = {},
    ) : this(
        website = Uuid.parse(website),
        enableEventQueue = true,
        umamiOptions = umamiOptions,
    )

    /**
     * Creates an Umami instance.
     *
     * @param website The UUID of the website to track events for.
     * @param umamiOptions A builder for configuring Umami options, such as the base URL, hostname, and queue capacity.
     */
    constructor(
        website: Uuid,
        umamiOptions: UmamiOptionsBuilder.() -> Unit = {},
    ) : this(
        website = website,
        enableEventQueue = true,
        umamiOptions = umamiOptions,
    )

    init {
        if (enableEventQueue) {
            consumeEventQueue()
        }
    }

    /**
     * Starts consuming the event queue in a coroutine.
     * This function launches a coroutine that continuously processes items from the event queue.
     * Each item is processed by the [processEventQueueItem] function, which sends the event to the Umami API.
     */
    private fun consumeEventQueue() {
        eventQueueJob = options.coroutineScope.launch {
            eventQueue.consumeEach { request -> processEventQueueItem(request) }
        }
    }
}
