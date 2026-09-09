package dev.appoutlet.kombu

import androidx.compose.runtime.Composable
import dev.appoutlet.kombu.ui.theme.KombuTheme
import org.koin.compose.KoinApplication
import org.koin.plugin.module.dsl.koinConfiguration

/**
 * Entry composable for the Kombu sample. Starts a Koin container from the generated
 * [KombuKoinApplication] configuration (annotation-driven, no manual module registration)
 * and renders the themed root scaffold.
 */
@Composable
fun App() {
    KoinApplication(
        configuration = koinConfiguration<KombuKoinApplication> {
            // No logging backend wired up yet; add one here when needed.
        },
    ) {
        KombuTheme {
            Navigation()
        }
    }
}