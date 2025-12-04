# Implementation Plan: Get All Users

**Branch**: `001-get-admin-users` | **Date**: 2025-12-04 | **Spec**: [spec.md](./spec.md)
**Input**: Feature specification from `specs/001-get-admin-users/spec.md`

## Summary

Implement a function to fetch a paginated and searchable list of all admin users from the `GET /api/admin/users` endpoint. This will be implemented in the `umami-api` module following the existing pattern for API calls (`Login.kt`) and will involve updating the `User` domain model to be compliant with the API response.

## Technical Context

**Language/Version**: Kotlin
**Primary Dependencies**: Ktor, Kotlinx Serialization, Kotlinx Coroutines
**Storage**: N/A
**Testing**: Kotest, Mokkery
**Target Platform**: Kotlin Multiplatform (`commonMain`)
**Project Type**: Library module (`umami-api`)
**Performance Goals**: < 3 seconds response time for searches on up to 100,000 records.
**Constraints**: Must integrate with the existing Ktor HTTP client and follow established project patterns.
**Scale/Scope**: Up to 100,000 users.

## Constitution Check

*   [X] **I. Modular by Design**: The feature will be implemented within the `:umami-api` and `:umami` modules, respecting the project's modular architecture.
*   [X] **II. Type-Safe and Idiomatic Kotlin**: The implementation will use Kotlin data classes and `suspend` functions, adhering to modern Kotlin idioms.
*   [X] **III. Asynchronous from the Ground Up**: The API call will be a `suspend` function, ensuring it is non-blocking.
*   [X] **IV. Kotlin Multiplatform**: All new code will be placed in the `commonMain` source set.
*   [X] **V. Rigorously Tested**: A new unit test file will be created with a test case using a `MockEngine` to validate the API request and response parsing.

## Project Structure

### Documentation (this feature)

```text
specs/001-get-admin-users/
├── plan.md              # This file
├── data-model.md        # Phase 1 output
├── quickstart.md        # Phase 1 output
└── tasks.md             # Phase 2 output (created by /speckit.tasks)
```

### Source Code (repository root)

The following files will be created or modified:

```text
├── umami-api/
│   └── src/
│       ├── commonMain/
│       │   └── kotlin/dev/appoutlet/umami/api/admin/
│       │       └── GetAllUsers.kt      # New file
│       │   └── kotlin/dev/appoutlet/umami/domain/
│       │       └── User.kt             # Modified file
│       └── commonTest/
│           └── kotlin/dev/appoutlet/umami/api/admin/
│               └── GetAllUsersTest.kt  # New file
└── umami/
    └── src/
        └── commonMain/
            └── kotlin/dev/appoutlet/umami/
                └── Umami.kt            # Modified to add new function
```

**Structure Decision**: The plan adheres to the existing project structure, placing API-specific models in the `umami-api` module and the public-facing function in the `umami` module. This follows the established pattern seen with `Login.kt` and other API calls.

## Complexity Tracking

No constitutional violations are anticipated. This section will remain empty.