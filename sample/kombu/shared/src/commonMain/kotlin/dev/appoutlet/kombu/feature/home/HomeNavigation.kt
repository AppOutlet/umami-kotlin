package dev.appoutlet.kombu.feature.home

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.core.navigation.Navigation
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.screen.HomeScreen
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

/**
 * Landing destination of the Kombu sample.
 */
@Single
internal class HomeNavigation : Navigation<HomeRoute> {
    override val routeKey = HomeRoute.key
    override val routeClass = HomeRoute::class
    override val routeSerializer = HomeRoute.serializer()

    @Composable
    override fun Content(route: HomeRoute) {
        HomeScreen()
    }

    override fun restore(parameters: Map<String, String?>) = HomeRoute
}

@Serializable
object HomeRoute : Route {
    override val key: String = "home"

    override fun toMap(): Map<String, String?> = emptyMap()
}