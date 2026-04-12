package auth

import dev.appoutlet.umami.api.UmamiApi
import dev.appoutlet.umami.api.auth
import dev.appoutlet.umami.api.me
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val api = UmamiApi {
        baseUrl("http://192.168.1.1")
    }

    val auth = api.auth()

    val me = api.me()

    val loginSession = auth.login(
        username = "sample-username",
        password = "sample-password"
    )

    val session = me.getSession()

    println(loginSession)
    println(session)
}
