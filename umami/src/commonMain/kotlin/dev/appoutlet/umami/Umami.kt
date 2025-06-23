package dev.appoutlet.umami

import co.touchlab.kermit.Logger
import dev.appoutlet.umami.api.processEventQueueItem
import dev.appoutlet.umami.core.createHttpClient
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Ip
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import dev.appoutlet.umami.util.createUserAgent
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.statement.HttpStatement
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
    internal val umamiCoroutineScope = CoroutineScope(Dispatchers.Default)

    internal var headers = mutableMapOf<String, String?>()

    internal val httpClient by lazy { createHttpClient() }

    internal val eventQueue = Channel<HttpRequestBuilder>(capacity = eventQueueCapacity)

    init {
        Logger.setTag("Umami")
        consumeEventQueue()
    }

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
