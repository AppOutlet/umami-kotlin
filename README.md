![banner](docs/banner.png)

# Umami Kotlin SDK

An unofficial, open-source library for Kotlin Multiplatform that makes it easy to interact with the [Umami](https://umami.is) web analytics REST API.

This SDK provides a type-safe, asynchronous wrapper, allowing you to integrate Umami's privacy-focused analytics into your Kotlin applications (Android, iOS, JVM, etc.) with minimal effort.

## What is Umami?

[Umami](https://umami.is) is a simple, fast, privacy-focused alternative to Google Analytics. It provides you with powerful web analytics while respecting user privacy and without collecting any personal information. This SDK helps you send data to your self-hosted or cloud-hosted Umami instance directly from your application.

## ✨ Features

* **Kotlin Multiplatform:** Write your analytics tracking logic once and share it across Android, iOS, JVM, and more.
* **Asynchronous:** Built with Kotlin Coroutines for non-blocking API calls.
* **Type-Safe:** Reduces the risk of errors when interacting with the Umami API.
* **Lightweight & Simple:** A clean API surface that is easy to understand and integrate.

## 🗺️ Roadmap

For details on the current development status and future plans, please see the [Roadmap](docs/roadmap.md).

## How to use it?

### Installation

To use the Umami Kotlin SDK in your project, add the following dependency to your `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("dev.appoutlet:umami:<library version>")
}
```

### Usage

Here's a basic example of how to use the SDK:

```kotlin
val umami = Umami.create(website = "<website id>")

umami.event(
    title = "Hello World",
    url = "/hello-world",
    name = "terminal-app-event",
)
```

## 🙌 Contributing

Contributions are welcome\! If you'd like to help improve the SDK, please feel free to open an issue to discuss a new feature or submit a pull request.

## 📜 License

This project is licensed under the [MIT License](LICENSE).
