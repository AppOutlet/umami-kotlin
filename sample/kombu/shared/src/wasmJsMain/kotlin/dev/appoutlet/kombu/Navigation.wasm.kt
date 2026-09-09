package dev.appoutlet.kombu

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import dev.appoutlet.kombu.core.navigation.Route

/**
 * WebAssembly has no browser history integration wired up yet.
 */
@Composable
actual fun ChronologicalBrowserNavigation(backStack: () -> NavBackStack<Route>) {
    // no-op
}