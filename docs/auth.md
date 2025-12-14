# Authentication

The `umami-kotlin` library provides a straightforward way to handle authentication with your Umami instance. This guide will walk you through the process of logging in, logging out, and verifying the authentication status.

!!! info "New to Umami Kotlin?"
    Before proceeding, make sure you have initialized the `Umami` client. Check out our getting started guides for [Kotlin Multiplatform](getstarted/kmp.md) and [Android](getstarted/android.md).

All authentication functions in this library are `suspend` functions. This means they must be called from within a coroutine or another `suspend` function.

## Obtaining an Auth Instance

You can access the `Auth` API functionalities through an extension function on your `Umami` client instance:

```kotlin
// Assuming 'umami' is an initialized Umami client
val authApi = umami.auth()
```

## Logging In

There are two primary ways to log in using the library: by providing a username and password directly, or by using an `Auth.Login.Request` object.

### Using Username and Password

The most common way to log in is by calling the `login` suspend function with the user's credentials.

**Example:**
```kotlin
// Assuming 'authApi' is an instance of Auth
val loginResponse = authApi.login("my-username", "my-password")

println("Login successful! Token: ${loginResponse.token}")
```

!!! warning "Token Storage"
    Upon a successful login, the `login` function automatically stores the authentication token **in memory** for the lifetime of the `Umami` object. It is **not persisted** to a database or any other storage, so the token will be lost if the application is restarted or the `Umami` instance is recreated.

### Using a Request Object

You can also create an `Auth.Login.Request` object and pass it to the `login` suspend function.

**Example:**
```kotlin
import dev.appoutlet.umami.api.Auth // Import Auth class to access Login inner interface

// Assuming 'authApi' is an instance of Auth
val loginRequest = Auth.Login.Request(
    username = "my-username",
    password = "my-password"
)

val loginResponse = authApi.login(loginRequest)
println("Login successful!")
```

### Login Response

A successful login returns an `Auth.Login.Response` object, which contains the authentication `token` and the `user` object.

-   **`token`**: A JWT token used for authenticating subsequent requests.
-   **`user`**: A `User` object containing details about the logged-in user, such as their ID and username.

## Logging Out

To log out, simply call the `logout` suspend function on the `Auth` instance. This will invalidate the session on the server and remove the locally stored authentication token.

**Example:**
```kotlin
// Assuming 'authApi' is an initialized and logged-in Auth instance
authApi.logout()

println("Logout successful!")
```

## Verifying Authentication

You can verify the current authentication status at any point by calling the `verify` suspend function on the `Auth` instance. This is useful for checking if the current token is still valid.

If the authentication is valid, the `verify` function will return a `User` object. If the token is invalid or expired, it will throw a `ClientRequestException`.

**Example:**
```kotlin
import io.ktor.client.features.ClientRequestException

// Assuming 'authApi' is an initialized Auth instance
suspend fun checkAuthentication() {
    try {
        val user = authApi.verify()
        println("Authentication is valid. User: ${user.username}")
    } catch (e: ClientRequestException) {
        println("Authentication is invalid or expired: ${e.message}")
    } catch (e: Exception) {
        println("An error occurred during verification: ${e.message}")
    }
}
```

