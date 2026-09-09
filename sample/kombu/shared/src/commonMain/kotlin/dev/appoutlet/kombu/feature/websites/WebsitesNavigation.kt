package dev.appoutlet.kombu.feature.websites

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.core.navigation.Navigation
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.screen.WebsitesScreen
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

/**
 * Websites destination of the Kombu sample.
 */
@Single
internal class WebsitesNavigation : Navigation<WebsitesRoute> {
    override val routeKey = WebsitesRoute.key
    override val routeClass = WebsitesRoute::class
    override val routeSerializer = WebsitesRoute.serializer()

    @Composable
    override fun Content(route: WebsitesRoute) {
        WebsitesScreen()
    }

    override fun restore(parameters: Map<String, String?>) = WebsitesRoute
}

@Serializable
object WebsitesRoute : Route {
    override val key: String = "websites"

    override fun toMap(): Map<String, String?> = emptyMap()
}