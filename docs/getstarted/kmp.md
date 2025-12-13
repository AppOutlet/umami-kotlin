# Track your first event in a Kotlin Multiplatform (KMP) application

This guide is fully focused on adding analytics with `umami-kotlin` to a **shared Kotlin Multiplatform codebase** (Android, iOS, Desktop, Web, etc.). You'll add the dependency to `commonMain`, configure a cross-platform singleton, and track your first events from shared code.

---
## 1. Supported targets
`umami-kotlin` is published as a Multiplatform library and can be used from:

* Android (JVM / Compose Multiplatform)
* iOS (via Kotlin/Native; accessible from Swift/Objective-C)
* Desktop (JVM)
* JavaScript / WASM (where Ktor HTTP engine is available)
* Other native targets (Linux, macOS, Windows) as enabled by your KMP setup

---
## 2. Add the dependency (commonMain)
Add the library to the `commonMain` source set so all platforms can access it.

=== "composeApp/build.gradle.kts"
    ```kotlin
    kotlin {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation("dev.appoutlet:umami:LATEST_VERSION")
                }
            }
        }
    }
    ```

=== "Version Catalog (libs.versions.toml)"
    ```toml
    [versions]
    umami = "LATEST_VERSION" # Replace with latest

    [libraries]
    umami = { group = "dev.appoutlet", name = "umami", version.ref = "umami" }
    ```
    ```kotlin title="composeApp/build.gradle.kts"
    kotlin {
        sourceSets {
            val commonMain by getting {
                dependencies {
                    implementation(libs.umami)
                }
            }
        }
    }
    ```

!!! note
    Make sure you refresh/Sync Gradle after adding the dependency so the IDE recognizes the API across all targets.

---
## 3. Create a shared analytics singleton
Define a single instance in shared code so every platform uses the same configuration.

```kotlin
// shared/src/commonMain/kotlin/analytics/Analytics.kt
package analytics

import dev.appoutlet.umami.Umami

object AnalyticsService {
    // Basic factory usage (parses strings internally)
    val umami: Umami = Umami("YOUR-WEBSITE-UUID") {
        // baseUrl("https://your-self-hosted-instance") // Only if self-hosting
    }

    fun trackAppLaunch() = umami.event(url = "/launch", title = "App Launch")
}
```

### Type-safe constructor
If you already validate inputs (e.g. UUID, hostname) you can use the primary constructor with domain objects:
```kotlin
import dev.appoutlet.umami.Umami
import kotlin.uuid.Uuid

val umamiTypeSafe = Umami(Uuid.parse("YOUR-WEBSITE-UUID")) {
    baseUrl("https://analytics.example.com")
    hostname("example.com")
    language("en-US")
    screenSize("1080x1920")
    eventQueueCapacity = 50
}
```
Both approaches are functionally equivalent; the string-based constructor just wraps parsing.

### Using Koin (Dependency Injection)
If you prefer DI, define a Koin module in `commonMain`:
```kotlin
// shared/src/commonMain/kotlin/di/AnalyticsModule.kt
package di

import dev.appoutlet.umami.Umami
import org.koin.dsl.module

val analyticsModule = module {
    single { Umami("YOUR-WEBSITE-UUID") }
}
```
Start Koin in each platform's entry point (example Android shown; do similarly for iOS/Desktop):
```kotlin
// Android example (e.g. in Application.onCreate)
startKoin { modules(analyticsModule) }
```
Inject where needed in shared code by implementing `KoinComponent`:
```kotlin
// shared/src/commonMain/kotlin/analytics/AnalyticsFacade.kt
package analytics

import dev.appoutlet.umami.Umami
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AnalyticsFacade : KoinComponent {
    private val umami: Umami by inject()
    fun trackAppLaunch() = umami.event(url = "/launch", title = "App Launch")
}
```

---
## 4. Track events from shared code
The primary tracking functions are **regular (non-suspend)** and return immediately:

* `event()` – Page views + custom events
* `identify()` – Attach metadata to a session (optional)

```kotlin
AnalyticsService.umami.event(url = "/home", title = "Home Screen")
AnalyticsService.umami.event(name = "button_clicked", data = mapOf("id" to "save"))
AnalyticsService.umami.identify(data = mapOf("plan" to "pro"))
```

You can wrap these in your own domain API to centralize naming or tagging conventions.

### Koin usage example
If using the Koin setup above:
```kotlin
// Simple wrapper using injected instance
object EventTracker : org.koin.core.component.KoinComponent {
    private val umami: dev.appoutlet.umami.Umami by inject()

    fun trackHome() = umami.event(url = "/home", title = "Home Screen")
    fun trackButton(id: String) = umami.event(name = "button_clicked", data = mapOf("id" to id))
    fun identifyPlan(plan: String) = umami.identify(data = mapOf("plan" to plan))
}

// Usage
EventTracker.trackHome()
EventTracker.trackButton("save")
EventTracker.identifyPlan("pro")
```

---
## 5. Asynchronous processing model
Calls to `event()` / `identify()` enqueue a request into an internal Kotlin `Channel` (capacity default = 25). A background coroutine (on `Dispatchers.Default`) processes each item sequentially using Ktor. Your UI / main thread is never blocked.

Key points:
* If the queue is full, producers suspend until space is available (rare in typical UI usage).
* Events are sent in order of invocation.

To tune throughput:
```kotlin
val umamiCustom = Umami("...") {
    eventQueueCapacity = 100
}
```

---
## 6. Optional configuration
All optional parameters can be set inside the `Umami` constructor lambda. For a more detailed explanation of each parameter, see [The Umami object documentation](../umami-object.md).
`logger` | `UmamiLogger` | Logging backend | Custom logging or disabling logs


Example adding language & dynamic screen size (Android + Desktop differences handled via expect/actual):
```kotlin
// commonMain
expect fun currentScreenSize(): String
expect fun currentLanguageTag(): String

val umami = Umami("YOUR-WEBSITE-UUID") {
    language(currentLanguageTag())
    screenSize(currentScreenSize())
}
```
```kotlin
// androidMain
actual fun currentScreenSize(): String = "1080x2400"
actual fun currentLanguageTag(): String = java.util.Locale.getDefault().toLanguageTag()
```
```kotlin
// iosMain
import platform.UIKit.UIScreen
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale

actual fun currentScreenSize(): String {
    val bounds = UIScreen.mainScreen.bounds
    return "${'$'}{bounds.size.width.toInt()}x${'$'}{bounds.size.height.toInt()}"
}
actual fun currentLanguageTag(): String = NSLocale.currentLocale.languageCode
```
---
## 7. Next steps
* Explore [Event tracking details](../event-tracking.md)

You're ready to gather cross-platform usage insights with a single analytics layer.
