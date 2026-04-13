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
- [x] Returns all websites in the system - `GET /api/admin/websites`
- [x] Returns all teams in the system - `GET /api/admin/teams`

## Events

- [ ] Returns a list of events for a website - `GET /api/websites/:websiteId/events`
- [ ] Returns aggregated event statistics - `GET /api/websites/:websiteId/events/stats`
- [ ] Returns event data grouped by event - `GET /api/websites/:websiteId/event-data`
- [ ] Accesses data for a specific event - `GET /api/websites/:websiteId/event-data/:eventId`
- [ ] Returns detailed event data for a website - `GET /api/websites/:websiteId/event-data/events`
- [ ] Returns all available fields for event data - `GET /api/websites/:websiteId/event-data/fields`
- [ ] Returns all available properties for event data - `GET /api/websites/:websiteId/event-data/properties`
- [ ] Returns all available values for a given event data field - `GET /api/websites/:websiteId/event-data/values`
- [ ] Returns statistics for a given event data field - `GET /api/websites/:websiteId/event-data/stats`

## Websites

- [x] Returns a list of all websites available to the user - `GET /api/websites`
- [x] Creates a new website - `POST /api/websites`
- [x] Returns the details of a specific website - `GET /api/websites/:websiteId`
- [x] Updates the details of a specific website - `POST /api/websites/:websiteId`
- [x] Deletes a website - `DELETE /api/websites/:websiteId`
- [x] Resets all data for a website - `POST /api/websites/:websiteId/reset`

### Website Statistics

- [ ] Returns the number of active visitors on a website - `GET /api/websites/:websiteId/active`
- [ ] Gets the date range of available data - `GET /api/websites/:websiteId/daterange`
- [ ] Gets events within a given time range - `GET /api/websites/:websiteId/events/series`
- [ ] Returns detailed metrics for a website over a date range - `GET /api/websites/:websiteId/metrics`
- [ ] Gets expanded metrics for a given time range - `GET /api/websites/:websiteId/metrics/expanded`
- [ ] Returns pageview data for a website - `GET /api/websites/:websiteId/pageviews`
- [ ] Returns summary statistics for a website - `GET /api/websites/:websiteId/stats`

### Realtime

- [ ] Realtime stats within the last 30 minutes - `GET /api/realtime/:websiteId`

### Sessions

- [ ] Returns a list of sessions for a website - `GET /api/websites/:websiteId/sessions`
- [ ] Returns statistics for sessions on a website - `GET /api/websites/:websiteId/sessions/stats`
- [ ] Get collected count of sessions by hour of weekday - `GET /api/websites/:websiteId/sessions/weekly`
- [ ] Returns details for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId`
- [ ] Returns the activity log for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId/activity`
- [ ] Returns all properties for a specific session - `GET /api/websites/:websiteId/sessions/:sessionId/properties`
- [ ] Returns all available properties for session data - `GET /api/websites/:websiteId/session-data/properties`
- [ ] Returns all available values for a given session data property - `GET /api/websites/:websiteId/session-data/values`

## Users

- [x] Returns the details of a specific user - `GET /api/users/:userId`
- [x] Updates the details of a specific user - `POST /api/users/:userId`
- [x] Deletes a user - `DELETE /api/users/:userId`
- [x] Returns a list of websites for a user - `GET /api/users/:userId/websites`
- [x] Returns a list of teams for a user - `GET /api/users/:userId/teams`

## Teams

- [x] Creates a new team - `POST /api/teams`
- [x] Joins an existing team using an access code - `POST /api/teams/join`
- [x] Returns the details of a specific team - `GET /api/teams/:teamId`
- [x] Updates the details of a specific team - `POST /api/teams/:teamId`
- [x] Deletes a team - `DELETE /api/teams/:teamId`

### Team Members

- [x] Returns a list of users in a team - `GET /api/teams/:teamId/users`
- [x] Adds a user to a team - `POST /api/teams/:teamId/users`
- [x] Returns details for a specific user in a team - `GET /api/teams/:teamId/users/:userId`
- [x] Updates the role of a user in a team - `POST /api/teams/:teamId/users/:userId`
- [x] Removes a user from a team - `DELETE /api/teams/:teamId/users/:userId`

### Team Websites

- [x] Returns a list of websites in a team - `GET /api/teams/:teamId/websites`
- [ ] Creates a website for a team - `POST /api/teams/:teamId/websites`
- [ ] Removes a website from a team - `DELETE /api/teams/:teamId/websites/:websiteId`

## Links

- [x] Returns a list of all links - `GET /api/links`
- [x] Creates a new link - `POST /api/links`
- [x] Returns the details of a specific link - `GET /api/links/:linkId`
- [x] Updates the details of a specific link - `POST /api/links/:linkId`
- [x] Deletes a link - `DELETE /api/links/:linkId`

## Me

- [x] Returns the current user - `GET /api/me`
- [ ] Updates the current user's password - `POST /api/me/password`
- [x] Returns a list of websites for the current user - `GET /api/me/websites`
- [x] Returns a list of teams for the current user - `GET /api/me/teams`

## Pixels

- [x] Returns all user pixels - `GET /api/pixels`
- [ ] Creates a new pixel - `POST /api/pixels`
- [x] Gets a pixel by ID - `GET /api/pixels/:pixelId`
- [x] Updates a pixel - `POST /api/pixels/:pixelId`
- [x] Deletes a pixel - `DELETE /api/pixels/:pixelId`

## Reports

- [ ] Returns a list of all reports - `GET /api/reports`
- [ ] Creates a new report - `POST /api/reports`
- [ ] Returns the details of a specific report - `GET /api/reports/:reportId`
- [ ] Updates the details of a specific report - `POST /api/reports/:reportId`
- [ ] Deletes a report - `DELETE /api/reports/:reportId`
- [ ] Run attribution report - `POST /api/reports/attribution`
- [ ] Run breakdown report - `POST /api/reports/breakdown`
- [ ] Run funnel report - `POST /api/reports/funnel`
- [ ] Run goal report - `POST /api/reports/goal`
- [ ] Run journey report - `POST /api/reports/journey`
- [ ] Run performance report - `POST /api/reports/performance`
- [ ] Run retention report - `POST /api/reports/retention`
- [ ] Run revenue report - `POST /api/reports/revenue`
- [ ] Run UTM report - `POST /api/reports/utm`

## Share

- [ ] Creates a share page - `POST /api/share`
- [ ] Gets a share page by ID - `GET /api/share/id/:shareId`
- [ ] Updates a share page - `POST /api/share/id/:shareId`
- [ ] Deletes a share page - `DELETE /api/share/id/:shareId`
- [ ] Gets all share pages that belong to a website - `GET api/websites/:websiteId/shares`
- [ ] Creates a share page belonging to a website - `POST api/websites/:websiteId/shares`
