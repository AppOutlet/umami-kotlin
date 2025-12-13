# 0.3.1

Release date: 2025-12-13

Full changelog: https://github.com/AppOutlet/umami-kotlin/releases/tag/0.3.1

## Highlights
- Custom headers provider for HTTP client, with an in-memory, thread-safe implementation. Simplifies header management and removes the old PersistentHeaders implementation.
- Admin API: ability to fetch all users (get-admin-users), including accompanying documentation and tests.
- Documentation improvements and template refinements for release notes and navigation.

## Changes
### Features
- Custom headers provider
    - New in-memory headers store with thread-safe behavior.
    - Header values guaranteed non-null; simplified client setup.
    - Added KDoc and tests for InMemoryHeaders.
- Admin API: Get all users
    - Introduced endpoint and client function to retrieve admin users.
    - Updated docs and sample usage.

### Dependency updates
- Ktor client dependencies bumped to 3.3.3.
- Mokkery bumped to 3.0.0.
- Kotest assertions core bumped to 6.0.7.

### Build and CI
- Minor CI adjustments (cancel parallel run in coverage workflow, qlty configuration tweaks).

### Documentation
- Improved release notes template and titles.
- Added mkdocs-literate-nav plugin and navigation updates.
- Various KDoc updates across modules.

## Breaking changes
- None.

## Migration notes
- No action required. If you previously relied on PersistentHeaders, migrate to the new in-memory headers provider (the API has been simplified and is thread-safe).

## Contributors (excluding bots)
- Messias Junior (@MessiasLima)

-----

# 0.3.0

This release introduces a new authentication API and includes various dependency and workflow improvements to enhance stability and development experience.

## ✨ What's New

- **Authentication API**: Added a new authentication API to securely manage user sessions.

## ⚡ Improvements

- **Dependency Updates**: Updated several core dependencies including Ktor and Compose for improved performance and stability.
- **Development Workflow Enhancements**: Upgraded various GitHub Actions for Java, Python, and checkout processes, streamlining our continuous integration.

## 📚 Documentation & Other Updates

- Adjusted internal documentation logic for better clarity and maintenance.
- Updated the AGENTS.md guide to reflect recent feedback, improving guidance for AI agents.

---

### 📖 Full Changelog
[Link to full changelog]: https://github.com/AppOutlet/umami-kotlin/compare/0.2.1...0.3.0

