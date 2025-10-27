# Setting up Umami Kotlin on Android applications

This guide walks you through installing `umami-kotlin` in a **pure Android (single-platform)** project, configuring the client, and tracking your first event.

!!! tip "Already using Kotlin Multiplatform?"
    See the [KMP Get Started guide](kmp.md) instead. This page focuses on a standard Android-only module.

---
## 1. Add the Dependency
The library is published on Maven Central:

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.appoutlet/umami?&label=Maven%20Central&link=https%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fdev.appoutlet%2Fumami)](https://central.sonatype.com/artifact/dev.appoutlet/umami)

Add the dependency to your Android app module `build.gradle.kts` (or `build.gradle`). Replace `LATEST_VERSION` with the version shown in the badge above.

=== "Kotlin DSL"
    ```kotlin title="app/build.gradle.kts"
    dependencies {
        implementation("dev.appoutlet:umami:LATEST_VERSION")
    }
    ```
=== "Groovy DSL"
    ```groovy title="app/build.gradle"
    dependencies {
        implementation "dev.appoutlet:umami:LATEST_VERSION"
    }
    ```

### Using a Version Catalog (Recommended)
If you manage dependencies with a `libs.versions.toml` catalog:

```toml title="gradle/libs.versions.toml"
[versions]
umami = "LATEST_VERSION" # Replace with the latest version

[libraries]
umami = { group = "dev.appoutlet", name = "umami", version.ref = "umami" }
```

Then in your module build file:
```kotlin title="app/build.gradle.kts"
dependencies {
    implementation(libs.umami)
}
```

This keeps upgrades centralized—just bump the `umami` version in the catalog.

---
## 2. Ensure Internet Permission
Most apps already have this, but confirm your `AndroidManifest.xml` includes:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---
## 3. Get Your Website ID
From your Umami dashboard:

1. Open the website you want to track.
2. Copy its **Website ID** (a UUID).
3. If you self‑host Umami, note your base domain (e.g. `https://analytics.mycompany.com`). If you use Umami Cloud you can omit `baseUrl` (it defaults internally).

---
## 4. Create a Singleton Umami Client
Create the client once and reuse it. A convenient place is a custom `Application` class or your DI container.

### Simple Application-level Singleton
```kotlin title="MyApp.kt"
class MyApp : Application() {
    lateinit var umami: Umami
        private set

    override fun onCreate() {
        super.onCreate()
        umami = Umami("YOUR-WEBSITE-UUID") {
            // baseUrl("https://your-self-hosted-instance") // Only if self-hosting
        }
    }
}
```
Register it in `AndroidManifest.xml`:
```xml
<application
    android:name=".MyApp"
    ... >
</application>
```
Access it anywhere:
```kotlin
val umami = (applicationContext as MyApp).umami
```

### Using Hilt (Optional)
```kotlin title="AnalyticsModule.kt"
@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {
    @Provides
    @Singleton
    fun provideUmami(): Umami = Umami("YOUR-WEBSITE-UUID") {
        // baseUrl("https://your-self-hosted-instance")
    }
}
```
Inject where needed:
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var umami: Umami
    ...
}
```

---
## 5. Track Your First Event
The tracking APIs (`event()` and `identify()`) are regular (non-suspend) functions. They enqueue work asynchronously, so it's safe to call them directly on the main thread—no coroutine or background dispatch required.

### Track a Screen View (Page View Analogy)
```kotlin
class MainActivity : ComponentActivity() {
    @Inject lateinit var umami: Umami

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { /* UI */ }

        // Non-blocking call
        umami.event(url = "/main", title = "Main Screen")
    }
}
```

### Track a Custom Event
```kotlin
umami.event(
    name = "purchase_completed",
    data = mapOf(
        "plan" to "pro",
        "price" to 49.99
    )
)
```

### Identify a User / Session Enrichment
```kotlin
umami.identify(data = mapOf("account_type" to "pro_user"))
```

!!! info
    The calls return immediately; events are placed into an internal queue and processed off the main thread. See detailed parameter explanations on the [Event Tracking page](../event-tracking.md).

---
## 6. Optional Configuration
All optional parameters can be set inside the `Umami` constructor lambda.

Parameter | Type | Purpose | When to change
--- | --- | --- | ---
`baseUrl(String)` | Function | Your own Umami instance | Self-hosted installs
`hostname(String)` | Function | Override site host | Multi-domain analytics
`language(String)` | Function | User locale | Provide when not derivable platform-side
`screenSize(String)` | Function | Screen resolution | You collect manually; optional
`ip(String)` | Function | Override IP | Rare (server-side forwarding)
`userAgent` | `String` | Custom UA string | Simulator/test tagging
`eventQueueCapacity` | `Int` | Channel size | High-volume burst events
`httpClientEngine` | `HttpClientEngine` | Ktor HTTP engine | Platform-specific needs (e.g., custom proxy)
`coroutineScope` | `CoroutineScope` | Background task scope | Integrate with app's lifecycle
`logger` | `UmamiLogger` | Logging backend | Custom logging or disabling logs

Example with common parameters:
```kotlin
import android.content.res.Resources
import java.util.Locale

val umami = Umami("YOUR-WEBSITE-UUID") {
    baseUrl("https://analytics.example.com") // Only for self-hosted
    language(Locale.getDefault().toLanguageTag())
    screenSize("${Resources.getSystem().displayMetrics.widthPixels}x${Resources.getSystem().displayMetrics.heightPixels}")
    eventQueueCapacity = 50
}
```

### Type-safe constructor
If you already have domain objects (e.g., `Uuid`, `Hostname`), you can use the primary constructor for type safety. This is useful if you validate these values elsewhere.

```kotlin
import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.Hostname
import dev.appoutlet.umami.domain.Language
import dev.appoutlet.umami.domain.ScreenSize
import kotlin.uuid.Uuid

val umamiTypeSafe = Umami(Uuid.parse("YOUR-WEBSITE-UUID")) {
    this.hostname = Hostname("example.com")
    this.language = Language("en-US")
    this.screenSize = ScreenSize(1080, 2400)
    eventQueueCapacity = 50
}
```
Both approaches are functionally equivalent. The string-based constructor just handles parsing for you.

---
## 7. Threading & Performance
Events are queued internally (Kotlin `Channel`) and processed off the main thread using Ktor. Your UI stays responsive, and you can fire events freely—even during transitions.

---
## 8. Next Steps
* Dive deeper into [Event Tracking](../event-tracking.md)
* Explore the generated API docs in the [Reference section](/reference).

You're all set—start capturing meaningful insights from your Android app!
