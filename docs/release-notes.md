# Release notes
## 0.3.1

This release introduces a custom headers provider for the HTTP client and adds the ability to fetch all users via the Admin API, alongside documentation and dependency updates.

**✨ What's New**

- **Custom Headers Provider**: Implemented a new in-memory, thread-safe headers store to simplify client setup.
- **Admin API**: Added functionality to fetch all users (get-admin-users), including accompanying documentation and tests.

**⚡ Improvements**

- **Dependency Updates**: Bumped Ktor client to 3.3.3, Mokkery to 3.0.0, and Kotest assertions to 6.0.7.
- **Build and CI**: Minor adjustments including cancelling parallel runs in coverage workflow and tweaks to qlty configuration.
- **Migration**: If you previously relied on PersistentHeaders, migrate to the new in-memory headers provider (the API has been simplified and is thread-safe).

**📚 Documentation & Other Updates**

- **Release Notes**: Improved template and titles.
- **Navigation**: Added mkdocs-literate-nav plugin and updated navigation.
- **KDoc**: Various updates across modules.

**📖 Full Changelog**
[Link to full changelog](https://github.com/AppOutlet/umami-kotlin/releases/tag/0.3.1)

-----

## 0.3.0

This release introduces a new authentication API and includes various dependency and workflow improvements to enhance stability and development experience.

**✨ What's New**

- **Authentication API**: Added a new authentication API to securely manage user sessions.

**⚡ Improvements**

- **Dependency Updates**: Updated several core dependencies including Ktor and Compose for improved performance and stability.
- **Development Workflow Enhancements**: Upgraded various GitHub Actions for Java, Python, and checkout processes, streamlining our continuous integration.

**📚 Documentation & Other Updates**

- Adjusted internal documentation logic for better clarity and maintenance.
- Updated the AGENTS.md guide to reflect recent feedback, improving guidance for AI agents.

**📖 Full Changelog**
[Link to full changelog](https://github.com/AppOutlet/umami-kotlin/releases/tag/0.3.0)

