package dev.appoutlet.kombu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dev.appoutlet.kombu.core.navigation.AppNavigator
import dev.appoutlet.kombu.core.navigation.LocalNavigator
import dev.appoutlet.kombu.core.navigation.NavigationAggregator
import dev.appoutlet.kombu.core.navigation.Route
import dev.appoutlet.kombu.core.navigation.getSavedStateConfiguration
import dev.appoutlet.kombu.feature.home.HomeRoute
import dev.appoutlet.kombu.navigation.kombuTopLevelDestinations
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * Root scaffold for Kombu: renders the active route with Navigation3, keeps a top app bar and a
 * bottom navigation bar for the top-level destinations, and wires browser-history support on web.
 */
@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun Navigation() {
    val navigationAggregator = koinInject<NavigationAggregator>()

    val config = remember(navigationAggregator) {
        getSavedStateConfiguration(navigationAggregator.navigation)
    }

    val backStack = rememberNavBackStack(configuration = config, HomeRoute) as NavBackStack<Route>
    val navigator = remember(backStack) { AppNavigator(backStack) }

    ChronologicalBrowserNavigation(backStack = { backStack })

    val currentRoute by remember(backStack) {
        derivedStateOf { backStack.lastOrNull() }
    }

    val currentLabel = kombuTopLevelDestinations
        .firstOrNull { it.route == currentRoute }
        ?.label

    CompositionLocalProvider(LocalNavigator provides navigator) {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text(currentLabel ?: "Kombu") })
            },
            bottomBar = {
                NavigationBar {
                    kombuTopLevelDestinations.forEach { destination ->
                        val selected = destination.route == currentRoute
                        NavigationBarItem(
                            selected = selected,
                            onClick = { navigator.setRoot(destination.route) },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.label,
                                )
                            },
                            label = { Text(destination.label) },
                        )
                    }
                }
            },
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding).fillMaxSize()) {
                NavDisplay(
                    backStack = backStack,
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator(),
                    ),
                    onBack = { backStack.removeLastOrNull() },
                    entryProvider = entryProvider {
                        navigationAggregator.navigation.forEach { it.setup(this) }
                    },
                )
            }
        }
    }
}

@Composable
expect fun ChronologicalBrowserNavigation(backStack: () -> NavBackStack<Route>)
