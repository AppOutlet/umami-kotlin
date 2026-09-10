package dev.appoutlet.kombu.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import kotlinx.serialization.KSerializer
import kotlin.reflect.KClass

/**
 * A single navigable feature. Koin injects every implementation into [NavigationAggregator]
 * (multibinding), which wires the route into the root scaffold.
 */
interface Navigation<T : Route> {
    val routeKey: String
    val routeClass: KClass<T>
    val routeSerializer: KSerializer<T>
    val metadata: Map<String, Any>
        get() = emptyMap()

    fun setup(scope: EntryProviderScope<Route>) {
        scope.addEntryProvider(clazz = routeClass, metadata = metadata, content = this::Content)
        RouteRegistry.register(key = routeKey, factory = this::restore)
    }

    @Composable
    fun Content(route: T)

    fun restore(parameters: Map<String, String?>): T
}
