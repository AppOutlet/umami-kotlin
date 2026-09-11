package dev.appoutlet.kombu

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import dev.nucleusframework.application.DecoratedWindow
import dev.nucleusframework.application.NucleusBackend
import dev.nucleusframework.application.nucleusApplication
import dev.nucleusframework.window.TitleBar

fun main() = nucleusApplication(backend = NucleusBackend.Tao) {
    DecoratedWindow(
        onCloseRequest = ::exitApplication,
        title = "Kombu",
        minimumSize = DpSize(width = 460.dp, height = 640.dp)
    ) {
        Box {
            App()
            TitleBar(style = getTransparentTitleBarStyle())
        }
    }
}
