package dev.appoutlet.kombu.feature.insights

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.core.navigation.Navigation
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.screen.InsightsScreen
import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

/**
 * Insights destination of the Kombu sample.
 */
@Single
internal class InsightsNavigation : Navigation<InsightsRoute> {
    override val routeKey = InsightsRoute.key
    override val routeClass = InsightsRoute::class
    override val routeSerializer = InsightsRoute.serializer()

    @Composable
    override fun Content(route: InsightsRoute) {
        InsightsScreen()
    }

    override fun restore(parameters: Map<String, String?>) = InsightsRoute
}

@Serializable
object InsightsRoute : Route {
    override val key: String = "insights"

    override fun toMap(): Map<String, String?> = emptyMap()
}
