package dev.appoutlet.kombu

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform