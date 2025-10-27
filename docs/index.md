![banner](img/banner.png)
# Umami Kotlin

[![Maven Central Version](https://img.shields.io/maven-central/v/dev.appoutlet/umami?style=for-the-badge&label=Maven%20Central&link=https%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fdev.appoutlet%2Fumami)](https://central.sonatype.com/artifact/dev.appoutlet/umami)

Welcome to the official documentation for `umami-kotlin`, a simple and powerful library for adding privacy-first analytics to your Kotlin applications.

This open-source library makes it easy to interact with the excellent, privacy-focused [Umami](https://umami.is) analytics platform. 

While born from the need for a unified solution in Kotlin Multiplatform, due its "multiplatform" aspect, Umami Kotlin is supported by the following platforms:

* 🌎[Kotlin Multiplatform](getstarted/kmp.md) (iOS, Android, Desktop, WebAssembly, etc.)
* 🤖Android Applications 

### **At a Glance**

Adding analytics to your Kotlin project is simple. Here’s a quick look:

```kotlin
// Add the dependency in your commonMain build.gradle.kts file
commonMain.dependencies {
    implementation("dev.appoutlet:umami:LATEST_VERSION")
}

// Initialize the Umami instance with your server URL and website ID
val umami = Umami("your-website-uuid")

// Track a custom event
fun whenSomethingHappens() {
    umami.event(url = "/screen/main", name = "app-launch")
}
```

### **Core Philosophy**

* **💎 Truly Multiplatform:** Write your analytics code once and run it everywhere—Android, iOS, Desktop, server-side (Ktor, SpringBoot, etc.), and any other **Java or Kotlin/JVM project**.
* **🚀 Lightweight & Simple:** A clean, intuitive API. No complex setup, no boilerplate. Just simple function calls designed to get out of your way.
* **🔒 Privacy-Focused:** Integrate a powerful analytics tool without compromising your users' data or your principles.

### **Ready to Start?**

Dive into our **Get Started** guide to add `umami-kotlin` to your project and track your first event in minutes. Or, if you want to see the source code, check out the project on GitHub.

**[Get Started  :octicons-rocket-16:](getstarted/kmp.md){ .md-button .md-button--primary }**
**[View on GitHub :octicons-mark-github-24:](https://github.com/AppOutlet/umami-kotlin){ .md-button }**

-----

[![AppOutlet Logo](img/appoutlet.png){ align=left width=200 }](https://appoutlet.dev)

### **A Project by [AppOutlet](https://appoutlet.dev)**

 `umami-kotlin` is developed and maintained by **AppOutlet**. You can explore our other projects on [our website](https://appoutlet.dev).
 
