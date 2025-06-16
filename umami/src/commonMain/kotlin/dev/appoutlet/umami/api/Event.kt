package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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
 * @param timestamp The timestamp of the event.
 * @param id The ID of the event.
 */
suspend fun Umami.event(
    referrer: String? = null,
    title: String? = null,
    url: String? = null,
    name: String,
    data: Map<String, Any?>? = null,
    tag: String? = null,
    timestamp: Long? = null,
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
 * Sends an identify event to the Umami API.
 *
 * @param referrer The referrer URL.
 * @param title The title of the page.
 * @param url The URL of the page.
 * @param name The name of the event.
 * @param data Additional data for the event.
 * @param tag A tag for the event.
 * @param timestamp The timestamp of the event.
 * @param id The ID of the event.
 */
suspend fun Umami.identify(
    referrer: String? = null,
    title: String? = null,
    url: String? = null,
    name: String,
    data: Map<String, Any?>? = null,
    tag: String? = null,
    timestamp: Long? = null,
    id: String? = null,
) {
    send(
        type = EventType.Identify,
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
private suspend fun Umami.send(
    type: EventType,
    referrer: String?,
    title: String?,
    url: String?,
    name: String,
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

    val response: EventResponse = httpClient.post("api/send") {
        setBody(request)
    }.body()

    if (response.beep != null) error("Umami server considered the event invalid")

    cache = response.cache
}
