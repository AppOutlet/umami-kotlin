# Authenticated user

The Authenticated user API provides a convenient way to access information about the currently authenticated user. You can retrieve session details, a list of associated websites, and a list of teams.

## Accessing the Authenticated user API

> **Note:** All methods in this API require authentication. You must log in using the `api.auth().login()` method before calling any of these functions. For more details, see the [Authentication documentation](auth.md).

You can access all functionalities through the `api.me()` extension function:

```kotlin
val api = UmamiApi {
    baseUrl("https://your-umami-instance.com")
}

// Access the Me API
val meApi = api.me()
```

## Get Session Information

To retrieve details about the current session, including user information, use the `getSession()` method.

### Usage

```kotlin
val session = api.me().getSession()
println("Authenticated as: ${session.user.username}")
```

## Get Websites

To retrieve a list of websites associated with the current user, use the `getWebsites()` method. You can optionally include websites from teams where the user is a member.

### Usage

```kotlin
// Get only personal websites
val personalWebsites = api.me().getWebsites()

// Include websites from teams
val allWebsites = api.me().getWebsites(includeTeams = true)

println("Found ${allWebsites.data.size} websites.")
```

## Get Teams

To retrieve a list of teams the current user belongs to, use the `getTeams()` method.

### Usage

```kotlin
val teams = api.me().getTeams()
println("User is a member of ${teams.data.size} teams.")
```
