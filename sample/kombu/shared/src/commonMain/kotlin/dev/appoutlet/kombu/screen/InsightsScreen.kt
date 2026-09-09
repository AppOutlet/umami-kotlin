package dev.appoutlet.kombu.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun InsightsScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Insights",
        hint = "Traffic insights and visitor behaviour will appear here.",
        modifier = modifier,
    )
}
