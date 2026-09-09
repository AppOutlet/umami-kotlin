package dev.appoutlet.kombu.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.feature.home.HomeRoute
import dev.appoutlet.kombu.feature.insights.InsightsRoute
import dev.appoutlet.kombu.feature.reports.ReportsRoute
import dev.appoutlet.kombu.feature.settings.SettingsRoute
import dev.appoutlet.kombu.feature.websites.WebsitesRoute
import com.composables.icons.lucide.ChartColumn
import com.composables.icons.lucide.FileText
import com.composables.icons.lucide.Globe
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Settings

/**
 * Metadata for a top-level destination rendered by the bottom navigation bar.
 */
data class TopLevelDestination(
    val route: Route,
    val label: String,
    val icon: ImageVector,
)

/**
 * Route table for the top-level destinations, in the order they appear in the bottom bar.
 */
val kombuTopLevelDestinations = listOf(
    TopLevelDestination(HomeRoute, "Home", Lucide.House),
    TopLevelDestination(WebsitesRoute, "Websites", Lucide.Globe),
    TopLevelDestination(InsightsRoute, "Insights", Lucide.ChartColumn),
    TopLevelDestination(ReportsRoute, "Reports", Lucide.FileText),
    TopLevelDestination(SettingsRoute, "Settings", Lucide.Settings),
)
