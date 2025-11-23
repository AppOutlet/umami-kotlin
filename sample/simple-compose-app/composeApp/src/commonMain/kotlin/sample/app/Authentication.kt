package sample.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import dev.appoutlet.umami.api.auth.login

@Composable
fun Authentication() {
    LaunchedEffect(Unit) {
        val user = umami.login(
            username = "admin",
            password = "password",
        )
    }
}
