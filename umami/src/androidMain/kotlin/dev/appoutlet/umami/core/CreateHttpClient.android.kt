package dev.appoutlet.umami.core

import io.ktor.client.engine.okhttp.OkHttp

actual fun defaultHttpClientEngine() = OkHttp.create()
