package sample.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.event
import kotlinx.coroutines.launch

val umami = Umami.create(
    baseUrl = "http://192.168.1.1:3000",
    website = "45242d93-0acf-4103-9d8e-65325d615e81"
)

@Composable
fun App() {
    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var eventName by remember { mutableStateOf("button_clicked") }
            var url by remember { mutableStateOf("/home") }
            val coroutineScope = rememberCoroutineScope()

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
                    umami.event(
                        name = eventName,
                        url = url
                    )
                }
            }) {
                Text("Track Event")
            }
        }
    }
}
