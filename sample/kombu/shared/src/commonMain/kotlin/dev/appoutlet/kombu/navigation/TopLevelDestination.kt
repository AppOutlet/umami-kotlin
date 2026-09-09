package dev.appoutlet.kombu.navigation

import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.feature.home.HomeRoute
import dev.appoutlet.kombu.feature.insights.InsightsRoute
import dev.appoutlet.kombu.feature.reports.ReportsRoute
import dev.appoutlet.kombu.feature.settings.SettingsRoute
import dev.appoutlet.kombu.feature.websites.WebsitesRoute
import org.jetbrains.compose.resources.DrawableResource
import umami_kotlin.sample.kombu.shared.generated.resources.Res
import umami_kotlin.sample.kombu.shared.generated.resources.home
import umami_kotlin.sample.kombu.shared.generated.resources.insights
import umami_kotlin.sample.kombu.shared.generated.resources.reports
import umami_kotlin.sample.kombu.shared.generated.resources.settings
import umami_kotlin.sample.kombu.shared.generated.resources.websites

/**
 * Metadata for a top-level destination rendered by the bottom navigation bar.
 */
data class TopLevelDestination(
    val route: Route,
    val label: String,
    val icon: DrawableResource,
)

/**
 * Route table for the top-level destinations, in the order they appear in the bottom bar.
 */
val kombuTopLevelDestinations = listOf(
    TopLevelDestination(HomeRoute, "Home", Res.drawable.home),
    TopLevelDestination(WebsitesRoute, "Websites", Res.drawable.websites),
    TopLevelDestination(InsightsRoute, "Insights", Res.drawable.insights),
    TopLevelDestination(ReportsRoute, "Reports", Res.drawable.reports),
    TopLevelDestination(SettingsRoute, "Settings", Res.drawable.settings),
)