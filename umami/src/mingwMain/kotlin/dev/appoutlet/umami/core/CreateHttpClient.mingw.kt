package dev.appoutlet.umami.core

import io.ktor.client.engine.winhttp.WinHttp

actual fun defaultHttpClientEngine() = WinHttp.create()
