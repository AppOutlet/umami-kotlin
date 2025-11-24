# Authentication

The `umami-kotlin` library provides a straightforward way to handle authentication with your Umami instance. This guide will walk you through the process of logging in, logging out, and verifying the authentication status.

!!! info "New to Umami Kotlin?"
    Before proceeding, make sure you have initialized the `Umami` client. Check out our getting started guides for [Kotlin Multiplatform](getstarted/kmp.md) and [Android](getstarted/android.md).

All authentication functions in this library are `suspend` functions. This means they must be called from within a coroutine or another `suspend` function.

## Logging In

There are two primary ways to log in using the library: by providing a username and password directly, or by using a `UmamiLogin.Request` object.

### Using Username and Password

The most common way to log in is by calling the `login` suspend function with the user's credentials.

**Example:**
```kotlin
// Assuming 'umami' is an initialized Umami client
val loginResponse = umami.login("my-username", "my-password")

println("Login successful! Token: ${loginResponse.token}")
```

!!! warning "Token Storage"
    Upon a successful login, the `login` function automatically stores the authentication token **in memory** for the lifetime of the `Umami` object. It is **not persisted** to a database or any other storage, so the token will be lost if the application is restarted or the `Umami` instance is recreated.

### Using a Request Object

You can also create a `UmamiLogin.Request` object and pass it to the `login` suspend function.

**Example:**
```kotlin
import dev.appoutlet.umami.api.auth.UmamiLogin

// Assuming 'umami' is an initialized Umami client
val loginRequest = UmamiLogin.Request(
    username = "my-username",
    password = "my-password"
)

val loginResponse = umami.login(loginRequest)
println("Login successful!")
```

### Login Response

A successful login returns a `UmamiLogin.Response` object, which contains the authentication `token` and the `user` object.

- **`token`**: A JWT token used for authenticating subsequent requests.
- **`user`**: A `User` object containing details about the logged-in user, such as their ID and username.

## Logging Out

To log out, simply call the `logout` suspend function. This will invalidate the session on the server and remove the locally stored authentication token.

**Example:**
```kotlin
// Assuming 'umami' is an initialized and logged-in Umami client
umami.logout()

println("Logout successful!")
```

## Verifying Authentication

You can verify the current authentication status at any point by calling the `verify` suspend function. This is useful for checking if the current token is still valid.

If the authentication is valid, the `verify` function will return a `User` object. If the token is invalid or expired, it will throw a `ClientRequestException`.

**Example:**
```kotlin
// Assuming 'umami' is an initialized and logged-in Umami client
val user = umami.verify()

println("Authentication is valid. User: ${user.username}")
```
