package dev.appoutlet.kombu.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

object KombuSpacing {
    val extraSmall = 8.dp
    val small = 16.dp
    val medium = 24.dp
    val large = 32.dp
    val extraLarge = 40.dp
}

val MaterialTheme.spacing: KombuSpacing
    get() = KombuSpacing
