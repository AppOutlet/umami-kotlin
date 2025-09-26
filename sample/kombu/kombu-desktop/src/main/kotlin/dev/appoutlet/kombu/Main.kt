@file:JvmName("Kombu")

package dev.appoutlet.kombu

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.appoutlet.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Kombu",
    ) {
        App()
    }
}
