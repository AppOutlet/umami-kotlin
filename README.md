![banner](docs/img/banner.png)
# Umami Kotlin

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.appoutlet/umami?style=for-the-badge&label=Maven%20Central&link=https%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fdev.appoutlet%2Fumami)](https://central.sonatype.com/artifact/dev.appoutlet/umami)

An unofficial, open-source Kotlin Multiplatform library for seamless interaction with the [Umami](https://umami.is) web analytics REST API.

This SDK offers a type-safe, asynchronous wrapper, enabling you to integrate Umami's privacy-centric analytics into your Kotlin applications (Android, iOS, JVM, etc.) with minimal effort.

### What is Umami?

[Umami](https://umami.is) is a simple, fast, and privacy-focused alternative to Google Analytics. It grants you powerful web analytics while respecting user privacy by not collecting any personal information.

## ✨ Key Features

* **Kotlin Multiplatform:** Write your analytics logic once and deploy it across Android, iOS, JVM, and other platforms.
* **Asynchronous by Design:** Built with Kotlin Coroutines for efficient, non-blocking API communication.
* **Type-Safe:** Minimizes errors and ensures correctness when interacting with the Umami API.
* **Lightweight and Simple:** Features a clean, intuitive API that is easy to integrate and use.

## 📚 Documentation

This README provides a high-level overview. For a comprehensive guide, including **installation instructions, detailed usage examples, and the full API reference**, please visit our official documentation website.

### **[➡️ View the Official Docs](https://appoutlet.dev/umami-kotlin/)**

#### Basic usage
```kotlin
// Add the dependency in your commonMain build.gradle.kts file
commonMain.dependencies {
    implementation("dev.appoutlet:umami:LATEST_VERSION")
}

// Initialize the Umami instance with your server URL and website ID
val umami = Umami.create(website = "your-website-id")

// Track a custom event
fun whenSomethingHappens() {
    umami.event(url = "/screen/main", name = "app-launch")
}
```

## 🙌 How to Contribute

Contributions are highly welcome\! If you have an idea for a new feature or want to help improve the SDK, please open an issue for discussion or submit a pull request on GitHub.

This library is proudly developed and maintained by **[AppOutlet](https://appoutlet.dev)**.

## 📜 License

This project is licensed under the [MIT License](https://www.google.com/search?q=LICENSE).

-----
<div align="center">

[![AppOutlet Logo](docs/img/appoutlet.png)](https://appoutlet.dev)
### **A Project by [AppOutlet](https://appoutlet.dev)**

</div>

`umami-kotlin` is developed and maintained by **AppOutlet**. You can explore our other projects on [our website](https://appoutlet.dev).