package sample.app

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.appoutlet.umami.api.auth.login
import dev.appoutlet.umami.api.auth.logout
import dev.appoutlet.umami.api.auth.verify
import dev.appoutlet.umami.domain.User
import kotlin.math.log
import kotlinx.coroutines.launch

@Composable
fun Authentication() {
    var loggedUser by remember { mutableStateOf<User?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            loggedUser = umami.verify()
        } catch (throwable: Throwable) {
            errorMessage = throwable.message
        }
    }

    AnimatedContent(targetState = loggedUser) { user ->
        if (user == null) {
            LoginForm(
                onLoginSuccess = {
                    loggedUser = it
                    errorMessage = null
                },
                onLoginError = { errorMessage = it },
            )
        } else {
            UserProfile(
                user = user,
                onLogOut = { loggedUser = null },
                onLogOutFailed = { errorMessage = it },
            )
        }
    }

    errorMessage?.let {
        Spacer(modifier = Modifier.height(8.dp))
        Text(it)
    }
}

@Composable
fun LoginForm(
    onLoginSuccess: (User) -> Unit,
    onLoginError: (String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        val response = umami.login(username, password)
                        onLoginSuccess(response.user)
                    } catch (e: Exception) {
                        onLoginError(e.message ?: "Unknown error")
                    }
                }
            }
        ) {
            Text("Login")
        }
    }
}

@Composable
fun UserProfile(user: User, onLogOut: () -> Unit, onLogOutFailed: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Column {
        Text(text = "Welcome, ${user.username}!")
        Button(onClick = {
            coroutineScope.launch {
                try {
                    umami.logout()
                    onLogOut()
                } catch (e: Exception) {
                    onLogOutFailed(e.message ?: "Unknown error")
                }
            }
        }) {
            Text("Log Out")
        }
    }
}
