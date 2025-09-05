# Event tracking

Once you have configured the `Umami` client, you can begin tracking user interactions. `umami-kotlin` provides a straightforward API for sending analytics data to your Umami instance asynchronously, ensuring that tracking calls never block your application's main thread.

!!! warning ""
    If you haven't already, please refer to the [Get Started](getstarted/kmp.md) guide to set up the `Umami` client in your project.

The library offers two primary functions for this purpose:

* `event()`: Used to track page views and custom events. This will be your most commonly used function.
* `identify()`: Used to send specific data to identify a user and create a new session. (optional)

!!! info
    Both `event()` and `identify()` are regular (non-suspend) functions. They queue work internally and return immediately without blocking.

## **Tracking Page Views and Custom Events**

The `event()` function is the universal method for sending most tracking data. You can use it to record a simple page view, a specific user interaction (like a button click), or both at the same time.

```kotlin
fun event(
    referrer: String? = null,
    title: String? = null,
    url: String? = null,
    name: String? = null,
    data: Map<String, Any>? = null,
    tag: String? = null,
    timestamp: Long = now(),
    id: String? = null
)
```


### **Parameters**

* `referrer` (String): The URL of the page that referred the user to the current page.
* `title` (String): The title of the page being viewed.
* `url` (String): The URL of the page. This is the primary field for tracking a **page view**.
* `name` (String): The name of the custom event. This is the primary field for tracking a **custom event**.
* `data` (Map): A map of additional, custom data to associate with the event.
* `tag` (String): A tag to categorize the event.
* `timestamp` (Long): The timestamp of when the event occurred, in **seconds** since the epoch. Defaults to the current time.
* `id` (String): A unique identifier for the event.

!!! info
    Please refer to [the full event method specification](reference/umami/dev.appoutlet.umami.api/event.html) for more details.


### **Usage Examples**

**1. Tracking a Page View**
To track a page view, provide a `url` and an optional `title`.

```kotlin
// Track a user navigating to the "Settings" screen
umami.event(url = "/settings", title = "User Settings")
```

**2. Tracking a Custom Event**
To track a specific interaction, like a user clicking a button, provide a `name`. You can also include custom `data` for more context.

```kotlin
// Track a user completing a purchase
umami.event(
    name = "purchase-completed",
    data = mapOf(
        "plan" to "premium",
        "price" to 49.99
    )
)
```

## **Identifying a User**

The `identify()` function is a specialized method used to associate custom data with the current user's session. This is useful for enriching your analytics with session-specific information.

```kotlin
fun identify(
    data: Map<String, Any>,
    timestamp: Long = now(),
    id: String? = null
)
```

### **Parameters**

* `data` (Map): The map of custom data you want to associate with the user's session.
* `timestamp` (Long): The timestamp of when the identification occurred. Defaults to the current time.
* `id` (String): A unique identifier for the event.

!!! info
    Please refer to [the full identify method specification](reference/umami/dev.appoutlet.umami.api/identify.html) for more details.

### **Usage Example**

```kotlin
// Identify the user with their account type after they log in
umami.identify(data = mapOf("account_type" to "pro_user"))
```

## **Asynchronous Processing**

It's important to understand that all event tracking in `umami-kotlin` is handled **asynchronously**. When you call `event()` or `identify()`, the payload is not sent to the network immediately on the calling thread.

Instead, the event is placed into an internal queue (a Kotlin `Channel`). A dedicated background process consumes items from this queue one by one and sends them to the Umami server.

This design ensures that your analytics calls are non-blocking and have a minimal performance impact on your application, which is especially critical for maintaining a responsive UI on the main thread.

### **A Note on Future Updates**

Currently, events are processed sequentially. Batch processing of events (sending multiple events in a single network request) is a planned future enhancement to further improve network efficiency.