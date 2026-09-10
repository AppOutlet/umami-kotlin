package dev.appoutlet.kombu

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import com.github.terrakok.navigation3.browser.ChronologicalBrowserNavigation
import com.github.terrakok.navigation3.browser.buildBrowserHistoryFragment
import com.github.terrakok.navigation3.browser.getBrowserHistoryFragmentParameters
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.core.navigation.RouteRegistry

/**
 * Keeps the Navigation3 back stack in sync with the browser history on web: each route is
 * serialized into a history fragment (key + parameters) via [buildBrowserHistoryFragment] and
 * restored back through [RouteRegistry] when the browser drives navigation.
 */
@Composable
actual fun ChronologicalBrowserNavigation(backStack: () -> NavBackStack<Route>) {
    ChronologicalBrowserNavigation(
        backStack = backStack(),
        saveKey = { route ->
            buildBrowserHistoryFragment(route.key, route.toMap())
        },
        restoreKey = { fragment ->
            val parameters = getBrowserHistoryFragmentParameters(fragment)
            val key = fragment.drop(1)
            RouteRegistry.restore(key, parameters)
        },
    )
}
