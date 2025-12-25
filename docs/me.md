# Me API

The `Me` API provides a convenient way to access information about the currently authenticated user. You can retrieve session details, a list of associated websites, and a list of teams.

## Accessing the Me API

You can access all `Me` API functionalities through the `umami.me()` extension function:

```kotlin
val umami = Umami(
    website = "your-website-uuid"
) {
    baseUrl = "https://your-umami-instance.com"
    token = "your-authentication-token"
}

// Access the Me API
val meApi = umami.me()
```

## Get Session Information

To retrieve details about the current session, including user information, use the `getSession()` method.

### Usage

```kotlin
val session = umami.me().getSession()
println("Authenticated as: ${session.user.username}")
```

### Example Response

```json
{
  "token": "xxxxxxxxxxxxxxx",
  "user": {
    "id": "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx",
    "username": "admin",
    "role": "admin",
    "isAdmin": true
  }
}
```

## Get Websites

To retrieve a list of websites associated with the current user, use the `getWebsites()` method. You can optionally include websites from teams where the user is a member.

### Usage

```kotlin
// Get only personal websites
val personalWebsites = umami.me().getWebsites()

// Include websites from teams
val allWebsites = umami.me().getWebsites(includeTeams = true)

println("Found ${allWebsites.data.size} websites.")
```

## Get Teams

To retrieve a list of teams the current user belongs to, use the `getTeams()` method.

### Usage

```kotlin
val teams = umami.me().getTeams()
println("User is a member of ${teams.data.size} teams.")
```
