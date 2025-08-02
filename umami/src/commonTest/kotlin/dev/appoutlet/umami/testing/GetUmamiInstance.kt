package dev.appoutlet.umami.testing

import dev.appoutlet.umami.Umami
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.content.TextContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.test.TestScope
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    prettyPrint = true
    encodeDefaults = true
    explicitNulls = false
}

@OptIn(ExperimentalUuidApi::class)
fun TestScope.getUmamiInstance(
    vararg requests: Pair<String, MockRequestHandleScope.(HttpRequestData) -> HttpResponseData>,
): Umami {
    val mockEngine = MockEngine { request ->
        mapOf(*requests)[request.url.encodedPath]
            ?.invoke(this, request) ?: error("No mock response defined for ${request.url.encodedPath}")
    }

    return Umami.create(
        website = Uuid.random().toString(),
        httpClientEngine = mockEngine,
        coroutineScope = this
    )
}

inline fun <reified T> HttpRequestData.body(): T {
    val textJson = (body as TextContent).text
    return json.decodeFromString<T>(textJson)
}

inline fun <reified T> MockRequestHandleScope.respond(content: T) : HttpResponseData {
    return respond(
        content = json.encodeToString(content),
        status = HttpStatusCode.OK,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}
