package dev.appoutlet.kombu

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import dev.nucleusframework.window.styling.LocalTitleBarStyle
import dev.nucleusframework.window.styling.TitleBarStyle

@Composable
fun getTransparentTitleBarStyle(): TitleBarStyle {
    val titleBarStyle = LocalTitleBarStyle.current
    return titleBarStyle.copy(
        colors = titleBarStyle.colors.copy(
            background = Color.Transparent,
            inactiveBackground = Color.Transparent,
        )
    )
}
