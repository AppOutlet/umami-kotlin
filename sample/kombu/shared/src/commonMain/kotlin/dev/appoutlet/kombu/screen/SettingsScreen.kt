package dev.appoutlet.kombu.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Settings",
        hint = "Account, team and application settings will live here.",
        modifier = modifier,
    )
}