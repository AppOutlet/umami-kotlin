package dev.appoutlet.umami.core

import io.ktor.client.engine.darwin.Darwin

actual fun defaultHttpClientEngine() = Darwin.create()
