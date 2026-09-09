package dev.appoutlet.kombu.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColors = lightColorScheme(
    primary = Color(0xFF0E7C66),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB7F1DF),
    onPrimaryContainer = Color(0xFF00201A),
    secondary = Color(0xFF4A645C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFCCEAE0),
    onSecondaryContainer = Color(0xFF06201A),
    tertiary = Color(0xFF3F6375),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFC2E8FF),
    onTertiaryContainer = Color(0xFF001E2C),
    background = Color(0xFFF4FBFA),
    onBackground = Color(0xFF161D1B),
    surface = Color(0xFFF4FBFA),
    onSurface = Color(0xFF161D1B),
    surfaceVariant = Color(0xFFDAE5E1),
    onSurfaceVariant = Color(0xFF3F4946),
    outline = Color(0xFF6F7976),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF8CD5C3),
    onPrimary = Color(0xFF00382E),
    primaryContainer = Color(0xFF005142),
    onPrimaryContainer = Color(0xFFA9F1DE),
    secondary = Color(0xFFB1CDC4),
    onSecondary = Color(0xFF1C352E),
    secondaryContainer = Color(0xFF334C45),
    onSecondaryContainer = Color(0xFFCCEAE0),
    tertiary = Color(0xFFA7CCE1),
    onTertiary = Color(0xFF0A3345),
    tertiaryContainer = Color(0xFF254B5D),
    onTertiaryContainer = Color(0xFFC2E8FF),
    background = Color(0xFF0E1513),
    onBackground = Color(0xFFDEE4E1),
    surface = Color(0xFF0E1513),
    onSurface = Color(0xFFDEE4E1),
    surfaceVariant = Color(0xFF3F4946),
    onSurfaceVariant = Color(0xFFBEC9C5),
    outline = Color(0xFF899390),
)

val KombuTypography = Typography()

val KombuShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(24.dp),
)

@Composable
fun KombuTheme(content: @Composable () -> Unit) {
    val colorScheme = if (isSystemInDarkTheme()) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = KombuTypography,
        shapes = KombuShapes,
        content = content,
    )
}
