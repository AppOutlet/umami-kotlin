package dev.appoutlet.kombu.feature.settings

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.core.navigation.Navigation
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.screen.SettingsScreen
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

/**
 * Settings destination of the Kombu sample.
 */
@Single
internal class SettingsNavigation : Navigation<SettingsRoute> {
    override val routeKey = SettingsRoute.key
    override val routeClass = SettingsRoute::class
    override val routeSerializer = SettingsRoute.serializer()

    @Composable
    override fun Content(route: SettingsRoute) {
        SettingsScreen()
    }

    override fun restore(parameters: Map<String, String?>) = SettingsRoute
}

@Serializable
object SettingsRoute : Route {
    override val key: String = "settings"

    override fun toMap(): Map<String, String?> = emptyMap()
}
