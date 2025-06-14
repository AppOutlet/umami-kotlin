package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
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
    val request = EventRequest(
        type = EventType.Event.value,
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