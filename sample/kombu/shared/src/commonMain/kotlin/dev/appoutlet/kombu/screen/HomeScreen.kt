package dev.appoutlet.kombu.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    PlaceholderScreen(
        title = "Home",
        hint = "Your analytics overview will live here.",
        modifier = modifier,
    )
}