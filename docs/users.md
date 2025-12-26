# Users API

The `Users` API provides functionalities for managing users in Umami. It allows you to create, retrieve, update, and delete users, as well as manage their associated websites and teams.

## Getting Started

To access the Users API, you need an instance of the `Umami` class. You can then call the `users()` extension function to get an instance of the `Users` class:

```kotlin
val umami = Umami(websiteId = "your_website_id")
val usersApi = umami.users()
```

## Methods

### `create(username: String, password: String, role: String, id: String? = null): User`

Creates a new user.

- `username`: The user's username.
- `password`: The user's password.
- `role`: The user's role.
- `id`: The user's ID (optional).

### `get(userId: String): User`

Gets a user by ID.

- `userId`: The unique identifier of the user.

### `update(userId: String, username: String? = null, password: String? = null, role: String? = null): User`

Updates a user.

- `userId`: The unique identifier of the user.
- `username`: The new username.
- `password`: The new password.
- `role`: The new role.

### `delete(userId: String)`

Deletes a user.

- `userId`: The unique identifier of the user.

### `getWebsites(userId: String, includeTeams: Boolean = false, search: String? = null, page: Int? = null, pageSize: Int? = null): SearchResponse<Website>`

Gets all websites that belong to a user.

- `userId`: The unique identifier of the user.
- `includeTeams`: Set to true if you want to include websites where you are the team owner.
- `search`: Search text.
- `page`: Determines page.
- `pageSize`: Determines how many results to return.

### `getTeams(userId: String, page: Int? = null, pageSize: Int? = null): SearchResponse<Team>`

Gets all teams that belong to a user.

- `userId`: The unique identifier of the user.
- `page`: Determines page.
- `pageSize`: Determines how many results to return.
