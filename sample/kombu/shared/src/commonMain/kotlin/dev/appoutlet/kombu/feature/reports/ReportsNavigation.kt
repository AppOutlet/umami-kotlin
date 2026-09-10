package dev.appoutlet.kombu.feature.reports

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.core.navigation.Navigation
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.screen.ReportsScreen
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

/**
 * Reports destination of the Kombu sample.
 */
@Single
internal class ReportsNavigation : Navigation<ReportsRoute> {
    override val routeKey = ReportsRoute.key
    override val routeClass = ReportsRoute::class
    override val routeSerializer = ReportsRoute.serializer()

    @Composable
    override fun Content(route: ReportsRoute) {
        ReportsScreen()
    }

    override fun restore(parameters: Map<String, String?>) = ReportsRoute
}

@Serializable
object ReportsRoute : Route {
    override val key: String = "reports"

    override fun toMap(): Map<String, String?> = emptyMap()
}
