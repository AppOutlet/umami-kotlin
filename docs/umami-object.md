# The `Umami` Object

The `Umami` class is the main entry point for the Umami analytics library. It is responsible for initializing the configuration and managing the event tracking queue.

## Creating an `Umami` Instance

To start using the library, you need to create an instance of the `Umami` class. You can do this by providing your website's unique identifier (ID). The website ID can be passed either as a `String` or as a `kotlin.uuid.Uuid` object.

### Basic Initialization

Here are the simplest ways to initialize the `Umami` object:

```kotlin
import dev.appoutlet.umami.Umami
import kotlin.uuid.Uuid

// Initialize with a String website ID
val umamiFromString = Umami(website = "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")

// Or initialize with a Uuid object
val websiteId = Uuid.parse("xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
val umamiFromUuid = Umami(website = websiteId)
```

## Configuring with `UmamiOptionsBuilder`

The `Umami` constructor accepts an optional lambda function that allows you to customize its behavior. This is done using the `UmamiOptionsBuilder`, which provides a DSL (Domain-Specific Language) to configure various settings.

Here’s an example of how to configure `Umami` during initialization:

```kotlin
val umami = Umami(website = "your-website-id") {
    // Use builder methods to set custom options
    baseUrl("https://my.umami.instance")
    hostname("myapp.com")
}
```

The settings you provide in the builder are used to create an internal `UmamiOptions` data class, which holds the final configuration for the client.

## Configuration Options

The `UmamiOptionsBuilder` allows you to configure the following parameters:

### `baseUrl`
- **Description**: The base URL of your self-hosted Umami instance's API.
- **Type**: `String`
- **Default**: `"https://cloud.umami.is"`
- **Builder Method**: `baseUrl(value: String)`
- **Example**:
  ```kotlin
  baseUrl("https://analytics.my-domain.com")
  ```

### `hostname`
- **Description**: The hostname of the website you are tracking (e.g., "myapp.com"). This value is sent in the `hostname` field of the event payload.
- **Type**: `dev.appoutlet.umami.domain.Hostname`
- **Default**: `null`
- **Configuration**:
  - **Property**: `hostname`
  - **Method**: `hostname(value: String)`
- **Examples**:
  ```kotlin
  import dev.appoutlet.umami.domain.Hostname

  // Using the property
  hostname = Hostname("myapp.com")

  // Using the method
  hostname("myapp.com")
  ```

### `language`
- **Description**: The language of the user's browser, specified in IETF language tag format (e.g., "en-US", "fr-FR").
- **Type**: `String`
- **Default**: `null`
- **Builder Method**: `language(value: String)`
- **Example**:
  ```kotlin
  language("en-GB")
  ```

### `screenSize`
- **Description**: The screen resolution of the user's device.
- **Type**: `String` ("widthxheight") or two `Int` values.
- **Default**: `null`
- **Builder Methods**:
  - `screenSize(value: String)`
  - `screenSize(width: Int, height: Int)`
- **Examples**:
  ```kotlin
  // Using a string
  screenSize("1920x1080")

  // Using width and height
  screenSize(width = 1366, height = 768)
  ```

### `ip`
- **Description**: The IP address of the user.
- **Type**: `String`
- **Default**: `null`
- **Builder Method**: `ip(value: String)`
- **Example**:
  ```kotlin
  ip("192.168.1.1")
  ```

### `userAgent`
- **Description**: The user agent string of the client or browser making the request.
- **Type**: `String`
- **Default**: A generated value based on the platform (e.g., "umami-kotlin/1.0.0").
- **Builder Property**: `userAgent`
- **Example**:
  ```kotlin
  userAgent = "MyCustomApp/1.0"
  ```

### `eventQueueCapacity`
- **Description**: The maximum number of tracking events to hold in the in-memory queue before it starts blocking. When the queue is full, new events will wait until there is space.
- **Type**: `Int`
- **Default**: `25`
- **Builder Property**: `eventQueueCapacity`
- **Example**:
  ```kotlin
  eventQueueCapacity = 50
  ```

### `httpClientEngine`
- **Description**: The Ktor HTTP client engine used for sending tracking events. You can provide a pre-configured engine if needed.
- **Type**: `io.ktor.client.engine.HttpClientEngine`
- **Default**: The default Ktor engine for the target platform.
- **Builder Property**: `httpClientEngine`
- **Example**:
  ```kotlin
  import io.ktor.client.engine.cio.CIO

  httpClientEngine = CIO.create()
  ```

### `coroutineScope`
- **Description**: The coroutine scope used for launching background tasks, such as processing the event queue.
- **Type**: `kotlinx.coroutines.CoroutineScope`
- **Default**: `CoroutineScope(Dispatchers.Default)`
- **Builder Property**: `coroutineScope`
- **Example**:
  ```kotlin
  import kotlinx.coroutines.CoroutineScope
  import kotlinx.coroutines.Dispatchers

  coroutineScope = CoroutineScope(Dispatchers.IO)
  ```

### `logger`
- **Description**: The logger instance for logging internal library messages. You can provide a custom implementation of the `UmamiLogger` interface.
- **Type**: `dev.appoutlet.umami.util.logger.UmamiLogger`
- **Default**: `DefaultUmamiLogger`, which uses the Kermit logging library.
- **Builder Property**: `logger`
- **Example**:
  ```kotlin
  // Assuming you have a custom logger implementation
  logger = MyCustomLogger()
  ```

### `headers`
- **Description**: A map for managing custom HTTP headers to be sent with every tracking request. This is useful for adding authentication tokens or other custom headers.
- **Type**: `dev.appoutlet.umami.util.headers.SuspendMutableMap<String, String>`
- **Default**: An empty `InMemoryHeaders` map.
- **Builder Property**: `headers`
- **Example**:
  ```kotlin
  // You can provide your own implementation or modify the default one
  headers = MyCustomHeaderProvider()
  ```

