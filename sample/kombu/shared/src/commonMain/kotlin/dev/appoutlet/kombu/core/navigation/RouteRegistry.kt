package dev.appoutlet.kombu.core.navigation

/**
 * Maps a route [key] plus its serialized parameters back to a [Route]. Used on web to restore a
 * route from the browser history fragment. Each [Navigation] registers itself during [Navigation.setup].
 */
object RouteRegistry {
    private val factories = mutableMapOf<String, (Map<String, String?>) -> Route>()

    fun register(key: String, factory: (Map<String, String?>) -> Route) {
        factories[key] = factory
    }

    fun restore(key: String, parameters: Map<String, String?>): Route? = factories[key]?.invoke(parameters)

    internal fun clear() = factories.clear()
}
