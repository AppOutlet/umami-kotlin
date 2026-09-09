package dev.appoutlet.kombu

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.appoutlet.kombu.navigation.KombuRoute
import dev.appoutlet.kombu.navigation.kombuTopLevelDestinations
import dev.appoutlet.kombu.screen.HomeScreen
import dev.appoutlet.kombu.screen.InsightsScreen
import dev.appoutlet.kombu.screen.ReportsScreen
import dev.appoutlet.kombu.screen.SettingsScreen
import dev.appoutlet.kombu.screen.WebsitesScreen
import org.jetbrains.compose.resources.painterResource

/**
 * Root scaffold for Kombu: a top app bar, a bottom navigation bar and a
 * [NavHost] that renders the active top-level destination.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KombuApp() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val currentLabel = kombuTopLevelDestinations
        .firstOrNull { destination ->
            currentDestination?.hierarchy?.any { it.hasRoute(destination.route::class) } == true
        }
        ?.label

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(currentLabel ?: "Kombu") })
        },
        bottomBar = {
            NavigationBar {
                kombuTopLevelDestinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy
                        ?.any { it.hasRoute(destination.route::class) } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon),
                                contentDescription = destination.label,
                            )
                        },
                        label = { Text(destination.label) },
                    )
                }
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = KombuRoute.Home,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable<KombuRoute.Home> { HomeScreen() }
            composable<KombuRoute.Websites> { WebsitesScreen() }
            composable<KombuRoute.Insights> { InsightsScreen() }
            composable<KombuRoute.Reports> { ReportsScreen() }
            composable<KombuRoute.Settings> { SettingsScreen() }
        }
    }
}