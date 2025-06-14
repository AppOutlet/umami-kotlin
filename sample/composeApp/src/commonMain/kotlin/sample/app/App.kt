package sample.app

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.event
import kotlinx.coroutines.launch

// This instance can be injected into your ViewModel and can be created wherever it suits better your architecture.
val umami = Umami.create(
    baseUrl = "http://192.168.1.1:3000",
    website = "45242d93-0acf-4103-9d8e-65325d615e81",
)

@Composable
fun App() {
    MaterialTheme {
        var eventName by remember { mutableStateOf("button_clicked") }
        var url by remember { mutableStateOf("/home") }
        val coroutineScope = rememberCoroutineScope()

        val snackbarHostState = remember { SnackbarHostState() }

        Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
            Column {
                Text("Umami KMP Sample", style = MaterialTheme.typography.headlineMedium)

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
                            snackbarHostState.showSnackbar("Event tracked successfully")
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
}




