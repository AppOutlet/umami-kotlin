package dev.appoutlet.kombu.navigation

import kotlinx.serialization.Serializable

/**
 * Typed, compile-time-safe route hierarchy for the top-level destinations of Kombu.
 *
 * Each destination is a distinct [Serializable] type so navigation is validated at
 * compile time instead of relying on string routes, following the same typed-route
 * pattern used across modern Compose Multiplatform samples.
 */
sealed interface KombuRoute {
    @Serializable data object Home : KombuRoute

    @Serializable data object Websites : KombuRoute

    @Serializable data object Insights : KombuRoute

    @Serializable data object Reports : KombuRoute

    @Serializable data object Settings : KombuRoute
}