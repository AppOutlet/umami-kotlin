# UmamiApi Object

The `UmamiApi` object is the core entry point for managing the Umami REST API. Unlike the `Umami` object, which focuses on fire-and-forget event tracking, the `UmamiApi` class is explicitly tailored for REST API management. It facilitates administrative and operational tasks such as CRUD operations on websites, users, teams, auth, and other endpoints.

It does not maintain an event queue or a background coroutine scope, making it a pure, lightweight REST client wrapper.

## Initialization

You can create an instance of `UmamiApi` using its builder pattern. By default, it communicates with the Umami Cloud API.

```kotlin
import dev.appoutlet.umami.api.UmamiApi

val api = UmamiApi()
```

## Configuration

You can fully customize the `UmamiApi` client to point to a self-hosted instance, change the HTTP engine, or customize headers.

```kotlin
import dev.appoutlet.umami.api.UmamiApi

val api = UmamiApi {
    // 1. Base URL
    // If you self-host Umami, you can configure your custom endpoint.
    // Defaults to "https://cloud.umami.is".
    baseUrl("https://umami.my-domain.com")

    // 2. HTTP Client Engine
    // You can override the default platform Ktor engine (e.g., to use MockEngine in tests).
    // httpClientEngine = defaultHttpClientEngine()

    // 3. Logger
    // By default, UmamiApi logs network requests and responses via Kermit.
    // logger = DefaultUmamiLogger()

    // 4. Headers
    // A suspendable map of custom headers applied to all outgoing REST requests.
    // This is primarily managed internally by the `Auth` module for the Authorization token.
    // headers = InMemoryHeaders()
}
```

## Available Features

Once initialized, the `UmamiApi` object provides access to various sub-APIs via extension functions:

- `api.auth()` - Manage user authentication (login, logout, verify).
- `api.websites()` - Manage website operations.
- `api.users()` - Manage users.
- `api.teams()` - Manage teams.
- `api.admin()` - Admin-only operations.
- `api.me()` - Manage the currently authenticated user.
- `api.links()` - Manage shared links.
- `api.pixels()` - Manage pixels.

Please refer to the individual documentation pages for each feature for detailed usage.