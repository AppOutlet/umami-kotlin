package dev.appoutlet.kombu

import androidx.compose.ui.window.ComposeUIViewController

@Suppress("FunctionNaming") // UIKit-conventional iOS entry point name.
fun MainViewController() = ComposeUIViewController { App() }
