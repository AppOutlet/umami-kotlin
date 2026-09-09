package dev.appoutlet.kombu

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import dev.appoutlet.kombu.core.navigation.Route

/**
 * iOS has no browser history to keep in sync.
 */
@Composable
actual fun ChronologicalBrowserNavigation(backStack: () -> NavBackStack<Route>) {
    // no-op
}