# AGENTS.md: Instructions for AI Agents

This document provides guidance for AI agents contributing to the `umami-kotlin` repository.

## Project Overview

This is a Kotlin Multiplatform library that serves as a wrapper for the [Umami](https://umami.is/) web analytics REST API. The goal is to provide a type-safe, asynchronous, and easy-to-use interface for Kotlin developers on various platforms (Android, iOS, JVM, etc.).

The project is structured into the following main modules:
- `:umami`: The core library containing the main logic.
- `:sample:simple-compose-app`: A sample application demonstrating usage with Jetpack Compose.
- `:sample:terminalApp`: A sample terminal application.

The source code in the `:umami` library is organized into the following packages:
- `api`: Handles the direct communication with the Umami REST API.
- `core`: Contains the central logic, including the HTTP client and event queue.
- `domain`: Holds the data models and value objects (e.g., `Hostname`, `Ip`).

## Key Libraries and Design Patterns

### Key Libraries

*   **Ktor**: Used for all HTTP networking. The library is designed to be multiplatform, with different client engines for each target (e.g., `OkHttp` for Android/JVM, `Darwin` for iOS).
*   **Kotlinx Coroutines**: For handling asynchronous operations, especially the event queue.
*   **Kotlinx Serialization**: For JSON serialization and deserialization.
*   **Kermit**: For logging.
*   **Kotest**: For assertions in tests.
*   **Mokkery**: For creating mocks in tests.

### Design Patterns

*   **Builder Pattern**: The `UmamiOptionsBuilder` is used to provide a flexible and readable way to configure the `Umami` instance.
*   **Facade Pattern**: The main `Umami` class acts as a facade, providing a simple, high-level interface to the more complex underlying systems like the event queue and HTTP client.
*   **Producer-Consumer Pattern**: The library uses a Kotlin `Channel` as a queue to process analytics events asynchronously. Events are produced by the client code and consumed by a background coroutine that sends them to the Umami API.

## Contribution Guidelines

All contributions must adhere to the guidelines outlined in [`CONTRIBUTING.md`](./CONTRIBUTING.md) and the [`CODE_OF_CONDUCT.md`](./CODE_OF_CONDUCT.md).

## Development Setup

The project is built with Gradle. The recommended IDEs are **IntelliJ IDEA Community Edition** or **Android Studio**.

## Code Quality and Testing

### Static Analysis

The project uses two main tools for static analysis:
1.  **Detekt**: The configuration is in `detekt/detekt.yml`. You can run it with `./gradlew detekt`.
2.  **Qlty**: A multi-linter tool configured via `.qlty/qlty.toml`.

Please ensure your changes do not introduce any new static analysis issues.

### Code Coverage

Code coverage is enforced using **Kover**. The minimum coverage requirements are defined in the root `build.gradle.kts` file. You can run the coverage check with:

```bash
./gradlew koverVerify
```

### Testing

All new features must be accompanied by tests. Bug fixes should also include a test that reproduces the bug.

When verifying that your changes compile and work correctly, run the JVM tests for the specific module you modified. Using `jvmTest` ensures compatibility with environments that don't have the Android SDK installed:

```bash
# For the core library
./gradlew :umami:jvmTest

# For the API library
./gradlew :umami-api:jvmTest

# For sample applications (JVM target)
./gradlew :sample:terminalApp:jvmTest
```

Running tests will verify that the code compiles correctly, as compilation is a prerequisite for test execution. Full compilation and integration checks for all platforms (including Android) are performed automatically in the CI stage.

## Build and Release

*   **Documentation**: The project uses **Dokka** to generate documentation. You can generate the docs with `./gradlew dokkaHtml`.
*   **Publishing**: The library is published to Maven Central using the `maven-publish` plugin.

## Maintaining AGENTS.md

This file is a living document. If you make significant architectural changes, add a new major library, or change the build process, please update this file accordingly.
