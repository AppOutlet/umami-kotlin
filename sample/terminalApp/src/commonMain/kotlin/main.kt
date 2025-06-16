import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.event
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val umami = Umami.create(
        baseUrl = "http://192.168.1.1:3000",
        website = "45242d93-0acf-4103-9d8e-65325d615e81",
    )

    umami.event(title = "Hello World", url = "/hello-world", name = "terminal-app-event")
}