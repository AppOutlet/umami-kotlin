package sample.app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import dev.appoutlet.umami.api.event
import kotlinx.coroutines.launch

@Composable
fun EventTracking() {
    var eventName by remember { mutableStateOf("button_clicked") }
    var url by remember { mutableStateOf("/home") }
    val coroutineScope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column {

            TextField(
                value = eventName,
                onValueChange = { eventName = it },
                label = { Text("Event Name") },
            )

            TextField(
                value = url,
                onValueChange = { url = it },
                label = { Text("Url") },
            )

            Button(onClick = {
                coroutineScope.launch {
                    try {
                        umami.event(name = eventName, url = url)
                        snackbarHostState.showSnackbar("Event enqueued successfully")
                    } catch (e: Exception) {
                        e.printStackTrace()
                        snackbarHostState.showSnackbar("Failed to track event: ${e.message}")
                    }
                }

            }) {
                Text("Track Event")
            }
        }
    }
}
