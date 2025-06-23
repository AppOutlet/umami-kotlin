# Getting started

Welcome\! This guide will walk you through the essential steps to install `umami-kotlin`, configure the client, and send your first event. You'll be up and running in just a few minutes.

### **Step 1: Add the Dependency**

First, you need to add `umami-kotlin` to your project. The library is hosted on Maven Central.

[](https://central.sonatype.com/artifact/dev.appoutlet/umami)

Add the following to your `build.gradle.kts` file's `dependencies` block. Make sure to use the latest version number shown in the badge above.


=== "Multiplatform"
    ```kotlin title="composeApp/build.gradle.kts"
    // In your commonMain dependencies
    kotlin {
        sourceSets {
            commonMain.dependencies {
                implementation("dev.appoutlet:umami:LATEST_VERSION")
            }
        }
    }
    ```
=== "Single platform (Kotlin/JVM, Android, etc.)"
    ```kotlin title="app/build.gradle.kts"
    // In your module dependencies
    dependencies {
        implementation("dev.appoutlet:umami:LATEST_VERSION")
    }
    ```
### **Step 2: Create and Configure the Client**

Next, you need to create an instance of the `Umami` client. This object will handle all communication with the Umami API.

It's highly recommended to create this as a **singleton** in your project's dependency injection setup (see our Recipes section for examples).

To create it, use the `Umami.create()` factory method:

```kotlin
val umami = Umami.create(
    website = "your-website-id",
    // The `baseUrl` is optional. It defaults to the Umami Cloud if not provided.
    baseUrl = "https://your-umami-instance.com" // Only needed for self-hosted
)
```

You'll need your **Website ID** from your Umami dashboard. If you're self-hosting Umami, you must also provide your instance's URL to the `baseUrl` parameter.

### **Step 3: Track Your First Event**

With the client configured, you're ready to track an event\!

Here is a complete, runnable example:

```kotlin
fun trackMyFirstEvent() {
    val umami = Umami.create(website = "your-website-id")

    // Let's track a page view for a user opening the app's home screen
    umami.event(url = "/home", title = "Home Screen")

    println("First event tracked!")
}
```

This code initializes the client and sends a simple page view event. You can also track custom events by providing a `name` to the `event()` function. It's that simple!

The events are processed asynchronously backed by a [Channel](https://kotlinlang.org/docs/coroutines-and-channels.html#channels) and the HTTP requests are made out of the main thread, so you can call this from anywhere in your application without blocking the UI or main thread. More details about event tracking on [the event tracking page](event-tracking.md).