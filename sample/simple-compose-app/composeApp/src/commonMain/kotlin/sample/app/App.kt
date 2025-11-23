package sample.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.appoutlet.umami.Umami

// This instance can be injected into your ViewModel and can be created wherever it suits better your architecture.
val umami = Umami(website = "74f75ec4-d0a3-47f2-9458-11f9febf7d7b") {
    baseUrl("http://192.168.1.1:3000")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        var selectedScreen by remember { mutableStateOf(Screen.Home) }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = selectedScreen.name) },
                    navigationIcon = {
                        if (selectedScreen != Screen.Home) {
                            IconButton(onClick = { selectedScreen = Screen.Home }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            AnimatedContent(
                targetState = selectedScreen,
                modifier = Modifier.padding(paddingValues)
            ) { screen ->
                when (screen) {
                    Screen.Home -> Home { screenClicked ->
                        selectedScreen = screenClicked
                    }

                    Screen.EventTracking -> EventTracking()
                    Screen.Authentication -> Authentication()
                }
            }
        }

    }
}


@Composable
private fun Home(onScreenSelect : (Screen) -> Unit) {
    LazyColumn {
        item {
            SampleButton(
                title = "Event Tracking",
                description = "Track custom events with Umami",
                onClick = { onScreenSelect(Screen.EventTracking) }
            )

            SampleButton(
                title = "Authentication",
                description = "Login, Log out and User Identification",
                onClick = { onScreenSelect(Screen.Authentication) }
            )
        }
    }
}


@Composable
private fun SampleButton(
    title: String,
    description: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = description, style = MaterialTheme.typography.bodyMedium)
    }
}

enum class Screen {
    Home,
    EventTracking,
    Authentication,
}
