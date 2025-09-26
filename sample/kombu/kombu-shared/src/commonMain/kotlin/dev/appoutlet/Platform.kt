package dev.appoutlet

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform