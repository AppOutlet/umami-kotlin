package dev.appoutlet.kombu.navigation

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
data class KombuTopLevelDestination(
    val route: KombuRoute,
    val label: String,
    val icon: DrawableResource,
)

/**
 * Route table for the top-level destinations, in the order they appear in the
 * bottom navigation bar.
 */
val kombuTopLevelDestinations = listOf(
    KombuTopLevelDestination(KombuRoute.Home, "Home", Res.drawable.home),
    KombuTopLevelDestination(KombuRoute.Websites, "Websites", Res.drawable.websites),
    KombuTopLevelDestination(KombuRoute.Insights, "Insights", Res.drawable.insights),
    KombuTopLevelDestination(KombuRoute.Reports, "Reports", Res.drawable.reports),
    KombuTopLevelDestination(KombuRoute.Settings, "Settings", Res.drawable.settings),
)