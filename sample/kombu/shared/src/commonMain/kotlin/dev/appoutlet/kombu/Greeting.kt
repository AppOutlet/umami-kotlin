package dev.appoutlet.kombu

class Greeting {
    private val platform = getPlatform()

    fun greet(): String = sayHello(platform.name)
}
