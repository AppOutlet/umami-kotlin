package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.dto.UpdatePixelRequest
import dev.appoutlet.umami.domain.Pixel
import dev.appoutlet.umami.domain.SearchResponse
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.delete
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

class Pixels(private val umami: Umami) {

    suspend fun getPixels(
        search: String? = null,
        page: Int? = null,
        pageSize: Int? = null,
    ): SearchResponse<Pixel> {
        return umami.httpClient.get(Api.Pixels()) {
            parameter("search", search)
            parameter("page", page)
            parameter("pageSize", pageSize)
        }.body()
    }

    suspend fun getPixel(pixelId: String): Pixel {
        return umami.httpClient.get(Api.Pixels.Id(id = pixelId)).body()
    }

    suspend fun updatePixel(pixelId: String, request: UpdatePixelRequest): Pixel {
        return umami.httpClient.post(Api.Pixels.Id(id = pixelId)) {
            setBody(request)
        }.body()
    }

    suspend fun deletePixel(pixelId: String) {
        umami.httpClient.delete(Api.Pixels.Id(id = pixelId))
    }
}

fun Umami.pixels(): Pixels = Pixels(this)
