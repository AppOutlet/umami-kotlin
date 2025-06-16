package dev.appoutlet.umami.util

/**
 * Creates a user agent string for HTTP requests.
 *
 * @param applicationName The name of the application.
 * @param applicationVersion The version of the application.
 * @param platform The operating system or platform.
 * @param platformVersion The version of the platform.
 * @param architecture The CPU architecture.
 * @return A formatted user agent string.
 */
fun createUserAgent(
    applicationName: String = "UmamiKmpSdk",
    applicationVersion: String = "1.0.0",
    platform: String = getPlatform(),
    platformVersion: String = "1.0",
    architecture: String = "Unknown",
): String {
    return "Mozilla/5.0 ($platform; $platformVersion; $architecture) $applicationName/$applicationVersion"
}

/**
 * Gets the current platform/operating system.
 * This is an expect function, with actual implementations for each supported platform.
 *
 * @return A string representing the current platform (e.g., "Android", "iOS", "macOS", "Windows", "Linux", "Js", "WasmJs").
 */
internal expect fun getPlatform(): String
