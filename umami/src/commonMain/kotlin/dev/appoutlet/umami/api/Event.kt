package dev.appoutlet.umami.api

import co.touchlab.kermit.Logger
import dev.appoutlet.umami.Umami
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi

/**
 * Sends an event to the Umami API.
 *
 * @param referrer The referrer URL.
 * @param title The title of the page.
 * @param url The URL of the page.
 * @param name The name of the event.
 * @param data Additional data for the event.
 * @param tag A tag for the event.
 * @param timestamp The timestamp of the event. The default is the current time in milliseconds since the epoch.
 * @param id The ID of the event.
 */
fun Umami.event(
    referrer: String? = null,
    title: String? = null,
    url: String? = null,
    name: String? = null,
    data: Map<String, Any?>? = null,
    tag: String? = null,
    timestamp: Long? = now(),
    id: String? = null,
) {
    send(
        type = EventType.Event,
        referrer = referrer,
        title = title,
        url = url,
        name = name,
        data = data,
        tag = tag,
        timestamp = timestamp,
        id = id
    )
}

/**
 * Sends an event to identify the user on the Umami server.
 *
 * @param data Additional data for the event.
 * @param timestamp The timestamp of the event.
 * @param id The ID of the event.
 */
fun Umami.identify(
    data: Map<String, Any?>? = null,
    timestamp: Long? = now(),
    id: String? = null,
) {
    send(
        type = EventType.Identify,
        referrer = null,
        title = null,
        url = null,
        name = null,
        data = data,
        tag = null,
        timestamp = timestamp,
        id = id
    )
}

/**
 * Returns the current time in milliseconds since the epoch.
 *
 * Uses the multiplatform `Clock.System.now()` to obtain the current instant and returns the number of seconds since the epoch.
 * Note: The returned value is in seconds, not milliseconds.
 */
@OptIn(ExperimentalTime::class)
private fun now(): Long = Clock.System.now().epochSeconds

/**
 * Sends an event to the Umami API.
 *
 * @param type The type of the event.
 * @param referrer The referrer URL.
 * @param title The title of the page.
 * @param url The URL of the page.
 * @param name The name of the event.
 * @param data Additional data for the event.
 * @param tag A tag for the event.
 * @param timestamp The timestamp of the event.
 * @param id The ID of the event.
 */
@OptIn(ExperimentalUuidApi::class)
@Suppress("LongParameterList")
private fun Umami.send(
    type: EventType,
    referrer: String?,
    title: String?,
    url: String?,
    name: String?,
    data: Map<String, Any?>?,
    tag: String?,
    timestamp: Long?,
    id: String?,
) {
    val request = EventRequest(
        type = type.value,
        payload = EventPayload(
            website = website.toString(),
            data = data?.mapValues { it.value?.toString() },
            hostname = hostname?.value,
            language = language?.value,
            referrer = referrer,
            screen = screen?.value,
            title = title,
            url = url,
            name = name,
            tag = tag,
            ip = ip?.value,
            userAgent = userAgent,
            timestamp = timestamp,
            id = id
        )
    )

    val requestBuilder = HttpRequestBuilder().apply {
        url("api/send")
        method = HttpMethod.Post
        setBody(request)
    }

    eventQueue.trySend(requestBuilder)
}

/**
 * Processes a single event queue item by sending the HTTP request to the Umami API.
 *
 * @param request The HTTP request builder containing the event data.
 * @throws ClientRequestException if the request fails due to a client error (4xx).
 * @throws ResponseException if the response is invalid or cannot be processed.
 * @throws Throwable for any other unexpected errors during the request.
 */
internal fun Umami.processEventQueueItem(request: HttpRequestBuilder) = umamiCoroutineScope.launch {
    try {
        val response = httpClient.post(request).body<EventResponse>()

        if (response.beep != null) {
            Logger.e { "Umami server considered the event invalid \n $response" }
        }

        headers["x-umami-cache"] = response.cache
    } catch (clientRequestException: ClientRequestException) {
        Logger.e(throwable = clientRequestException) {
            "Error processing event request"
        }
    } catch (responseException: ResponseException) {
        Logger.e(throwable = responseException) {
            "Error processing event response"
        }
    }
}
