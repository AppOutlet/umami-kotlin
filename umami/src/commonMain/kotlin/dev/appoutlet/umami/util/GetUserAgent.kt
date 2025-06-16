package dev.appoutlet.umami.util

fun createUserAgent(
    applicationName: String = "UmamiKmpSdk",
    applicationVersion: String = "1.0.0",
    platform: String = getPlatform(),
    platformVersion: String = "1.0",
    architecture: String = "Unknown",
): String {
    return "Mozilla/5.0 ($platform; $platformVersion; $architecture) $applicationName/$applicationVersion"
}

internal expect fun getPlatform(): String
