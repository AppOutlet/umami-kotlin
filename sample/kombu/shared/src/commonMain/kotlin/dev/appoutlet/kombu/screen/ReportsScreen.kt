package dev.appoutlet.kombu.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ReportsScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Reports",
        hint = "Generated reports and exports will be listed here.",
        modifier = modifier,
    )
}
