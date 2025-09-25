package dev.appoutlet.umami.core

import io.ktor.client.engine.js.Js

actual fun defaultHttpClientEngine() = Js.create()
