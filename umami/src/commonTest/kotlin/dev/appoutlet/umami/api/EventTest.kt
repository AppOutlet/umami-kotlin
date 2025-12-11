package dev.appoutlet.umami.api

import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import dev.appoutlet.umami.util.headers
import io.kotest.matchers.shouldBe
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Duration.Companion.seconds
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class EventTest {

    @Test
    fun `track event`() = runTest {
        val fixtureReferer = "referer"
        val fixtureTitle = "title"
        val fixtureUrl = "https://example.com"
        val fixtureName = "eventName"
        val fixtureData = mapOf("key" to "value")
        val fixtureTag = "tag"
        val fixtureTimestamp = 1234567890L
        val fixtureId = "event-id"

        val fixtureResponse = EventResponse(
            cache = "event-cache-token",
            sessionId = "session-id",
            visitId = "visit-id",
            beep = null
        )

        val umami = getUmamiInstance(
            "/api/send" to { request ->
                request.method shouldBe HttpMethod.Post

                val eventRequest = request.body<EventRequest>()
                eventRequest.type shouldBe EventType.Event.value

                with(eventRequest.payload) {
                    referrer shouldBe fixtureReferer
                    title shouldBe fixtureTitle
                    url shouldBe fixtureUrl
                    name shouldBe fixtureName
                    data shouldBe fixtureData
                    tag shouldBe fixtureTag
                    timestamp shouldBe fixtureTimestamp
                    id shouldBe fixtureId
                }

                respond(fixtureResponse)
            }
        )

        umami.event(
            referrer = fixtureReferer,
            title = fixtureTitle,
            url = fixtureUrl,
            name = fixtureName,
            data = fixtureData,
            tag = fixtureTag,
            timestamp = fixtureTimestamp,
            id = fixtureId
        )

        advanceUntilIdle()
        delay(5.seconds)

        umami.eventQueue.close()
    }

    @Test
    fun `identification event`() = runTest {
        val fixtureData = mapOf("key" to "value")
        val fixtureTimestamp = 1234567890L
        val fixtureId = "event-id"
        val fixtureResponse = EventResponse(
            cache = "event-cache-token",
            sessionId = "session-id",
            visitId = "visit-id",
            beep = null
        )

        val umami = getUmamiInstance(
            "/api/send" to { request ->
                request.method shouldBe HttpMethod.Post

                val eventRequest = request.body<EventRequest>()
                eventRequest.type shouldBe EventType.Identify.value

                with(eventRequest.payload) {
                    referrer shouldBe null
                    title shouldBe null
                    url shouldBe null
                    name shouldBe null
                    data shouldBe fixtureData
                    tag shouldBe null
                    timestamp shouldBe fixtureTimestamp
                    id shouldBe fixtureId
                }

                respond(fixtureResponse)
            }
        )

        umami.identify(
            data = fixtureData,
            timestamp = fixtureTimestamp,
            id = fixtureId
        )

        umami.eventQueue.close()
    }

    @Test
    fun `store cache token`() = runTest {
        val fixtureResponse = EventResponse(
            cache = "event-cache-token",
            sessionId = "session-id",
            visitId = "visit-id",
            beep = null
        )

        val fixtureRequest = EventRequest(
            type = "identify",
            payload = EventPayload(
                website = "website-id",
                data = emptyMap(),
                hostname = null,
                language = null,
                referrer = null,
                screen = null,
                title = null,
                url = null,
                name = null,
                tag = null,
                ip = null,
                userAgent = "user-agent-string",
                timestamp = null,
                id = null
            )
        )

        val umami = getUmamiInstance(
            "/api/send" to { request ->
                respond(fixtureResponse)
            }
        )

        val requestBuilder = HttpRequestBuilder().apply {
            url("api/send")
            method = HttpMethod.Post
            setBody(fixtureRequest)
        }

        val job = umami.processEventQueueItem(requestBuilder)

        job.join()

        umami.headers.get("x-umami-cache") shouldBe fixtureResponse.cache

        umami.eventQueue.close()
    }
}
