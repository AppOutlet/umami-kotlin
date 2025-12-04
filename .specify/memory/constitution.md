<!--
---
Sync Impact Report
---
Version Change: 0.0.0 → 1.0.0
Modified Principles:
- [PRINCIPLE_1_NAME] → I. Modular by Design
- [PRINCIPLE_2_NAME] → II. Type-Safe and Idiomatic Kotlin
- [PRINCIPLE_3_NAME] → III. Asynchronous from the Ground Up
- [PRINCIPLE_4_NAME] → IV. Kotlin Multiplatform
- [PRINCIPLE_5_NAME] → V. Rigorously Tested
Added Sections: N/A
Removed Sections: N/A
Templates Requiring Updates:
- ✅ .specify/templates/plan-template.md
- ✅ .specify/templates/spec-template.md
- ✅ .specify/templates/tasks-template.md
Follow-up TODOs: N/A
-->
# Umami Kotlin Constitution

## Core Principles

### I. Modular by Design
The project is split into two modules: `umami` for core event tracking and `umami-api` for the full API wrapper. This separation ensures that users who only need event tracking can use a lightweight library.

### II. Type-Safe and Idiomatic Kotlin
The library must provide a type-safe API, leveraging Kotlin's features to create an intuitive and developer-friendly experience.

### III. Asynchronous from the Ground Up
All API calls and event processing must be asynchronous, using Kotlin Coroutines to ensure non-blocking operations.

### IV. Kotlin Multiplatform
The library must be designed and built to support multiple platforms (Android, iOS, JVM, etc.), sharing as much code as possible in `commonMain`.

### V. Rigorously Tested
Every new feature must be accompanied by unit tests. Bug fixes must include a regression test. High code coverage is mandatory and enforced by Kover.

## Governance
This Constitution supersedes all other practices. Amendments require documentation, approval, and a migration plan. All pull requests and reviews must verify compliance.

**Version**: 1.0.0 | **Ratified**: 2025-12-04 | **Last Amended**: 2025-12-04