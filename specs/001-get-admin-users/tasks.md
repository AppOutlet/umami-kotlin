# Tasks: Get All Users

This document outlines the tasks required to implement the "Get All Users" feature, organized by user story priority.

## Phase 1: Setup

- [X] T001 Create the package structure for the new admin API endpoint in `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/api/admin/`.
- [X] T002 Create the package structure for the new admin API tests in `umami-api/src/commonTest/kotlin/dev/appoutlet/umami/api/admin/`.

## Phase 2: User Story 1 - Retrieve All Users (P1)

**User Story**: As an administrator, I want to retrieve a list of all users in the system so that I can view and manage user accounts.

**Acceptance Criteria**:
- A `getUsers()` function is available in the Umami client.
- A call to `getUsers()` with no parameters returns the first page of users.
- The `User` objects in the response contain all the fields specified in the data model.
- The feature is covered by a unit test using a `MockEngine` that verifies a successful response.

### Implementation Tasks

- [X] T003 [US1] Define the `UserCount` data class in `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/api/admin/Users.kt`.
- [X] T004 [US1] Update the `User` data class in `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/domain/User.kt` to include all fields from `data-model.md`, ensuring default values are provided for backward compatibility.
- [X] T005 [P] [US1] Define the `Users.Request` and `Users.Response` data classes for the API call in `umami-api/src/commonMain/kotlin/dev/appoutlet/umami/api/admin/Users.kt`.
- [X] T006 [US1] Add the `getUsers()` suspend function to the `Umami` class in `umami/src/commonMain/kotlin/dev/appoutlet/umami/Umami.kt`.
- [X] T007 [US1] Implement the internal API call logic for `GET /api/admin/users` within the `umami-api` module.

### Test Tasks

- [X] T008 [P] [US1] Create the test file `UsersTest.kt` in `umami-api/src/commonTest/kotlin/dev/appoutlet/umami/api/admin/`.
- [X] T009 [US1] Write a test in `UsersTest.kt` to verify that a successful API call with no parameters returns a correctly parsed `Users.Response`.

## Phase 3: User Story 2 - Search for Specific Users (P2)

**User Story**: As an administrator, I want to search for users by their name or other attributes so that I can quickly find a specific account.

**Acceptance Criteria**:
- The `getUsers()` function accepts an optional `search` parameter.
- The `search` query parameter is correctly appended to the request URL.
- The feature is covered by a unit test verifying the search functionality.

### Implementation Tasks

- [X] T010 [US2] Modify the `getUsers()` function signature in `umami/src/commonMain/kotlin/dev/appoutlet/umami/Umami.kt` to accept an optional `search: String?` parameter.

### Test Tasks

- [X] T011 [US2] Write a test in `UsersTest.kt` to verify that the `search` query parameter is correctly appended to the request URL when provided.

## Phase 4: User Story 3 - Paginate Through User List (P3)

**User Story**: As an administrator, I want to navigate through pages of the user list so that I can view all users in the system, not just the first page.

**Acceptance Criteria**:
- The `getUsers()` function accepts optional `page` and `pageSize` parameters.
- The `page` and `pageSize` query parameters are correctly appended to the request URL.
- The feature is covered by a unit test verifying the pagination functionality.

### Implementation Tasks

- [X] T012 [US3] Modify the `getUsers()` function signature in `umami/src/commonMain/kotlin/dev/appoutlet/umami/Umami.kt` to accept optional `page: Int?` and `pageSize: Int?` parameters.

### Test Tasks

- [X] T013 [US3] Write a test in `UsersTest.kt` to verify that `page` and `pageSize` query parameters are correctly appended to the request URL when provided.

## Phase 5: Polish & Cross-Cutting Concerns

- [X] T014 Review and format all new and modified code to align with project standards using `./gradlew detekt`.
- [X] T015 Verify code coverage for the new functionality meets the project requirements with `./gradlew koverVerify`.

## Dependencies

- **US2** depends on **US1**.
- **US3** depends on **US1**.

## Parallel Execution

- **Within US1**:
  - Data model tasks (T003, T004, T005) can be done in parallel.
  - Test setup (T008) can be done in parallel with data model tasks.
- **Across Stories**: US2 and US3 are independent of each other and can be worked on in parallel after US1 is complete.

## Implementation Strategy

The feature will be delivered incrementally, starting with the core functionality of retrieving users (US1) as the Minimum Viable Product (MVP). Search (US2) and Pagination (US3) will follow as separate increments. Each user story is designed to be independently testable.