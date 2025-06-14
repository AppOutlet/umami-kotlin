# Roadmap

This document outlines the current development status and future plans for the Umami API. It is intended for developers who are using or contributing to this library.

## Umami API (2% Complete)

| Status | Description                                     | Endpoint                  |
| :----: | :---------------------------------------------- | :------------------------ |
| [x]    | Send tracking event data                        | `POST /api/send`          |
| [ ]    | Login user                                      | `POST /api/auth/login`    |
| [ ]    | Verify authentication token                     | `POST /api/auth/verify`   |
| [ ]    | Get all users (admin)                           | `GET /api/admin/users`    |
| [ ]    | Get user details                                | `GET /api/users/:userId`  |
| [ ]    | Update user details                             | `POST /api/users/:userId` |
| [ ]    | Delete user                                     | `DELETE /api/users/:userId`|
| [ ]    | Get websites for a user                         | `GET /api/users/:userId/websites` |
| [ ]    | Get teams for a user                            | `GET /api/users/:userId/teams` |
| [ ]    | Create a new team                               | `POST /api/teams`         |
| [ ]    | Join an existing team                           | `POST /api/teams/join`    |
| [ ]    | Get team details                                | `GET /api/teams/:teamId`  |
| [ ]    | Update team details                             | `POST /api/teams/:teamId` |
| [ ]    | Delete team                                     | `DELETE /api/teams/:teamId`|
| [ ]    | Get users in a team                             | `GET /api/teams/:teamId/users` |
| [ ]    | Add user to a team                              | `POST /api/teams/:teamId/users` |
| [ ]    | Get specific user in a team                     | `GET /api/teams/:teamId/users/:userId` |
| [ ]    | Update user role/permissions in a team          | `POST /api/teams/:teamId/users/:userId` |
| [ ]    | Remove user from a team                         | `DELETE /api/teams/:teamId/users/:userId` |
| [ ]    | Get websites for a team                         | `GET /api/teams/:teamId/websites` |
| [ ]    | Get events for a website                        | `GET /api/websites/:websiteId/events` |
| [ ]    | Get detailed event data for a website           | `GET /api/websites/:websiteId/event-data/events` |
| [ ]    | Get fields for event data of a website          | `GET /api/websites/:websiteId/event-data/fields` |
| [ ]    | Get values for event data fields of a website   | `GET /api/websites/:websiteId/event-data/values` |
| [ ]    | Get statistics for event data of a website      | `GET /api/websites/:websiteId/event-data/stats` |
| [ ]    | Get sessions for a website                      | `GET /api/websites/:websiteId/sessions` |
| [ ]    | Get statistics for sessions of a website        | `GET /api/websites/:websiteId/sessions/stats` |
| [ ]    | Get details for a specific session              | `GET /api/websites/:websiteId/sessions/:sessionId` |
| [ ]    | Get activity for a specific session             | `GET /api/websites/:websiteId/sessions/:sessionId/activity` |
| [ ]    | Get properties for a specific session           | `GET /api/websites/:websiteId/sessions/:sessionId/properties` |
| [ ]    | Get properties for session data of a website    | `GET /api/websites/:websiteId/session-data/properties` |
| [ ]    | Get values for session data properties          | `GET /api/websites/:websiteId/session-data/values` |
| [ ]    | Get all websites                                | `GET /api/websites`       |
| [ ]    | Create a new website                            | `POST /api/websites`      |
| [ ]    | Get website details                             | `GET /api/websites/:websiteId` |
| [ ]    | Update website details                          | `POST /api/websites/:websiteId` |
| [ ]    | Delete website                                  | `DELETE /api/websites/:websiteId` |
| [ ]    | Reset website data                              | `POST /api/websites/:websiteId/reset` |
| [ ]    | Get active user count for a website             | `GET /api/websites/:websiteId/active` |
| [ ]    | Get events for a website (Note: duplicate)      | `GET /api/websites/:websiteId/events` |
| [ ]    | Get pageviews for a website                     | `GET /api/websites/:websiteId/pageviews` |
| [ ]    | Get metrics for a website                       | `GET /api/websites/:websiteId/metrics` |
| [ ]    | Get summary statistics for a website            | `GET /api/websites/:websiteId/stats` |
