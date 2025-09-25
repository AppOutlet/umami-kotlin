package dev.appoutlet.umami.core

import io.ktor.client.engine.curl.Curl

actual fun defaultHttpClientEngine() = Curl.create()
