package dev.appoutlet.kombu.core.navigation

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation3.runtime.NavBackStack

/**
 * Abstraction over the navigation back stack used by screens to move around.
 */
interface Navigator {
    fun navigate(destination: Route)
    fun setRoot(destination: Route)
    fun goBack()
}

internal data class AppNavigator(val backStack: NavBackStack<Route>) : Navigator {
    override fun navigate(destination: Route) {
        if (backStack.last() != destination) {
            backStack.add(destination)
        }
    }

    override fun setRoot(destination: Route) {
        backStack.clear()
        backStack.add(destination)
    }

    override fun goBack() {
        backStack.removeLastOrNull()
    }
}

internal val LocalNavigator = compositionLocalOf<Navigator> {
    object : Navigator {
        override fun navigate(destination: Route) {
            error("No implementation provided for LocalNavigator")
        }

        override fun goBack() {
            error("No implementation provided for LocalNavigator")
        }

        override fun setRoot(destination: Route) {
            error("No implementation provided for LocalNavigator")
        }
    }
}
