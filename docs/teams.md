# Teams API

The `Teams` API provides a set of methods for managing teams in Umami. It allows you to create, retrieve, update, and delete teams, as well as manage team members and their associated websites.

## Getting Started

To access the Teams API, you need an instance of the `Umami` class. You can then call the `teams()` extension function to get an instance of the `Teams` class:

```kotlin
val umami = Umami(websiteId = "your_website_id")
val teamsApi = umami.teams()
```

## Methods

### `find(page: Int? = null, pageSize: Int? = null): SearchResponse<Team>`

Retrieves a paginated list of teams.

- `page`: The page number to retrieve.
- `pageSize`: The number of teams to retrieve per page.

### `create(name: String): Team`

Creates a new team.

- `name`: The name of the team.

### `join(accessCode: String): TeamMember`

Joins a team.

- `accessCode`: The access code of the team to join.

### `get(teamId: String): Team`

Retrieves a team by its ID.

- `teamId`: The ID of the team to retrieve.

### `update(teamId: String, name: String? = null, accessCode: String? = null): Team`

Updates a team.

- `teamId`: The ID of the team to update.
- `name`: The new name of the team.
- `accessCode`: The new access code of the team.

### `delete(teamId: String)`

Deletes a team.

- `teamId`: The ID of the team to delete.

### `getUsers(teamId: String, search: String? = null, page: Int? = null, pageSize: Int? = null): SearchResponse<TeamMember>`

Retrieves a list of team members.

- `teamId`: The ID of the team.
- `search`: An optional search string to filter users by.
- `page`: An optional page number for pagination.
- `pageSize`: An optional number of results per page.

### `addUser(teamId: String, userId: String, role: String): TeamMember`

Adds a user to a team.

- `teamId`: The ID of the team.
- `userId`: The ID of the user to add.
- `role`: The role of the user in the team.

### `getUser(teamId: String, userId: String): TeamMember`

Retrieves a user from a team.

- `teamId`: The ID of the team.
- `userId`: The ID of the user.

### `updateUserRole(teamId: String, userId: String, role: String): TeamMember`

Updates a user's role in a team.

- `teamId`: The ID of the team.
- `userId`: The ID of the user.
- `role`: The new role of the user.

### `removeUser(teamId: String, userId: String)`

Removes a user from a team.

- `teamId`: The ID of the team.
- `userId`: The ID of the user to remove.

### `getWebsites(teamId: String, search: String? = null, page: Int? = null, pageSize: Int? = null): SearchResponse<Website>`

Retrieves the websites of a team.

- `teamId`: The ID of the team.
- `search`: An optional search string to filter websites by.
- `page`: An optional page number for pagination.
- `pageSize`: An optional number of results per page.
