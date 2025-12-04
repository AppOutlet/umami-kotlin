# Roadmap

This document outlines the current development status and future plans for the Umami API, following the structure of the [official API documentation](https://umami.is/docs/api). It is intended for developers who are using or contributing to this library.

## Core

- [x] Send tracking event data - `POST /api/send`

## Authentication

- [x] Login to receive an authentication token - `POST /api/auth/login`
- [x] Verify an authentication token - `POST /api/auth/verify`
- [x] Logout and invalidate the current token - `POST /api/auth/logout`

## Admin

These endpoints are only available for admin users on self-hosted instances.

- [x] Returns all users in the system - `GET /api/admin/users`
- [ ] Returns all websites in the system - `GET /api/admin/websites`
- [ ] Returns all teams in the system - `GET /api/admin/teams`

## Websites

- [ ] Returns a list of all websites available to the user - `GET /api/websites`
- [ ] Creates a new website - `POST /api/websites`
- [ ] Returns the details of a specific website - `GET /api/websites/:websiteId`
- [ ] Updates the details of a specific website - `POST /api/websites/:websiteId`
- [ ] Deletes a website - `DELETE /api/websites/:websiteId`
- [ ] Resets all data for a website - `POST /api/websites/:websiteId/reset`

### Website Statistics

- [ ] Returns the number of active visitors on a website - `GET /api/websites/:websiteId/active`
- [ ] Returns pageview data for a website - `GET /api/websites/:websiteId/pageviews`
- [ ] Returns summary statistics for a website - `GET /api/websites/:websiteId/stats`
- [ ] Returns detailed metrics for a website over a date range - `GET /api/websites/:websiteId/metrics`

### Events

- [ ] Returns a list of events for a website - `GET /api/websites/:websiteId/events`
- [ ] Returns detailed event data for a website - `GET /api/websites/:websiteId/event-data/events`
- [ ] Returns all available fields for event data - `GET /api/websites/:websiteId/event-data/fields`
- [ ] Returns all available values for a given event data field - `GET /api/websites/:websiteId/event-data/values`
- [ ] Returns statistics for a given event data field - `GET /api/websites/:websiteId/event-data/stats`

### Sessions

- [ ] Returns a list of sessions for a website - `GET /api/websites/:websiteId/sessions`
- [ ] Returns statistics for sessions on a website - `GET /api/websites/:websiteId/sessions/stats`
- [ ] Returns details for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId`
- [ ] Returns the activity log for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId/activity`
- [ ] Returns all properties for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId/properties`
- [ ] Returns all available properties for session data - `GET /api/websites/:websiteId/session-data/properties`
- [ ] Returns all available values for a given session data property - `GET /api/websites/:websiteId/session-data/values`

## Users

- [ ] Returns the details of a specific user - `GET /api/users/:userId`
- [ ] Updates the details of a specific user - `POST /api/users/:userId`
- [ ] Deletes a user - `DELETE /api/users/:userId`
- [ ] Returns a list of websites for a user - `GET /api/users/:userId/websites`
- [ ] Returns a list of teams for a user - `GET /api/users/:userId/teams`

## Teams

- [ ] Creates a new team - `POST /api/teams`
- [ ] Joins an existing team using an access code - `POST /api/teams/join`
- [ ] Returns the details of a specific team - `GET /api/teams/:teamId`
- [ ] Updates the details of a specific team - `POST /api/teams/:teamId`
- [ ] Deletes a team - `DELETE /api/teams/:teamId`

### Team Members

- [ ] Returns a list of users in a team - `GET /api/teams/:teamId/users`
- [ ] Adds a user to a team - `POST /api/teams/:teamId/users`
- [ ] Returns details for a specific user in a team - `GET /api/teams/:teamId/users/:userId`
- [ ] Updates the role of a user in a team - `POST /api/teams/:teamId/users/:userId`
- [ ] Removes a user from a team - `DELETE /api/teams/:teamId/users/:userId`

### Team Websites

- [ ] Returns a list of websites in a team - `GET /api/teams/:teamId/websites`