![banner](docs/banner.png)
# Umami Kotlin
An unofficial, open-source Kotlin Multiplatform library for seamless interaction with the [Umami](https://umami.is) web analytics REST API.

This SDK offers a type-safe, asynchronous wrapper, enabling you to integrate Umami's privacy-centric analytics into your Kotlin applications (Android, iOS, JVM, etc.) with minimal effort.

### What is Umami?

[Umami](https://umami.is) is a simple, fast, and privacy-focused alternative to Google Analytics. It grants you powerful web analytics while respecting user privacy by not collecting any personal information. This SDK facilitates sending data directly from your application to your self-hosted or cloud-based Umami instance.

## ✨ Key Features

* **Kotlin Multiplatform:** Write your analytics logic once and deploy it across Android, iOS, JVM, and other platforms.
* **Asynchronous by Design:** Built with Kotlin Coroutines for efficient, non-blocking API communication.
* **Type-Safe:** Minimizes errors and ensures correctness when interacting with the Umami API.
* **Lightweight and Simple:** Features a clean, intuitive API that is easy to integrate and use.

## 🚀 Getting Started

### Prerequisites

* An Umami instance (either [cloud-hosted](https://umami.is/cloud) or self-hosted).
* Your Website ID from your Umami dashboard.

### Installation

To add the Umami Kotlin to your project, include the following dependency in your `build.gradle.kts` file:

```kotlin
// In your commonMain dependencies
dependencies {
    implementation("dev.appoutlet:umami:<latest-version>")
}
```

*Remember to replace `<latest-version>` with the most recent version from the repository.*

### Usage

1.  **Initialize the SDK:** Create an `Umami` instance with your server URL and website ID.

    ```kotlin
    val umami = Umami.create(
        website = "<your-website-id>"                // The ID of your website in Umami
    )
    ```

2.  **Track Events:** Send custom events with optional data payloads.

    ```kotlin
    // Example of tracking a simple page view or event
    umami.trackEvent(
        url = "/screen/main",
        name = "app-launch"
    )
    ```

## 🗺️ Project Roadmap

For details on our development status and future plans, please consult the [Roadmap](docs/roadmap.md).

## 🙌 How to Contribute

Contributions are highly welcome\! If you have an idea for a new feature or want to help improve the SDK, please open an issue for discussion or submit a pull request.

## 📜 License

This project is licensed under the [MIT License](LICENSE).