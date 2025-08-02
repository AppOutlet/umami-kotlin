import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.event
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val umami = Umami.create(
        website = "7330db89-268f-4f13-bd45-d1e2755c1b00",
    )

    umami.event(
        title = "Hello World",
        url = "/hello-world",
        name = "terminal-app-event",
    )
}