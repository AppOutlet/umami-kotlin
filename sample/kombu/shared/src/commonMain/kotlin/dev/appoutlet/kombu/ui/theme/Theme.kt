package dev.appoutlet.kombu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun KombuTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) KombuDarkColorScheme else KombuLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KombuTypography,
        shapes = KombuShapes,
        content = content,
    )
}
