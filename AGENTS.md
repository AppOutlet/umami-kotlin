# AGENTS.md

Kotlin Multiplatform library for the [Umami](https://umami.is) web analytics REST API.

## Modules

| Module | Path | Purpose |
|---|---|---|
| `:umami` | `umami/` | Core event tracking (lightweight client) |
| `:umami-api` | `umami-api/` | Full REST API wrapper — depends on `:umami` via `api()` |
| `:sample:*` | `sample/` | Demo apps (Compose Desktop, native terminal). Not published. |

`:umami-api` re-exports `:umami`. Both publish to Maven Central as `dev.appoutlet:umami` / `dev.appoutlet:umami-api`.

## Commands

```bash
./gradlew detekt            # Lint + auto-correct — runs automatically on pre-push; do NOT run autonomously
./gradlew allTests          # All tests, all KMP targets — very slow on dev machines; do NOT run autonomously
./gradlew jvmTest           # JVM tests only — still slow; do NOT run autonomously
./gradlew koverVerify       # Coverage gates — runs full test suite; do NOT run autonomously
```

**When to run what:**
- To verify a change compiles and behaves correctly, run only the specific test class(es) affected:
  ```bash
  ./gradlew jvmTest --tests "dev.appoutlet.umami.api.auth.LoginTest"
  ```
- `detekt`, `allTests`, `jvmTest`, and `koverVerify` must **only** be run when explicitly requested by the user.
- CI pipeline order (reference only): `detekt` → `koverVerify` → `koverXmlReport`.

Dokka and Maven publish require `--no-configuration-cache` despite it being globally enabled.

## Testing

- Wrap every test in `kotlinx.coroutines.test.runTest`.
- Assertions: Kotest matchers (`shouldBe`).
- HTTP mocking: Ktor `MockEngine` via helpers in `dev.appoutlet.umami.testing` package (both modules have their own copy).
- General mocking: Mokkery.
- Test names use backtick style: `` fun `should do something`() = runTest { ... } ``
- Tests live in `commonTest` only — no platform-specific test source sets.
- See `umami-api/src/commonTest/AGENTS.md` for detailed test-writing patterns (MockEngine helpers, `getUmamiInstance`, `respond<T>`, `body<T>()`).

Key difference between the two `getUmamiInstance` copies: `:umami-api` sets `enableEventQueue = false`; `:umami` leaves it enabled.

## Architecture

- **Extension function pattern**: API features are classes taking `Umami` as a constructor param, exposed via extension functions (`umami.auth()`, `umami.websites()`, etc.).
- **Ktor Resources**: API routes are nested `@Resource` classes in `umami-api/.../Api.kt`.
- **`@InternalUmamiApi`**: `@RequiresOptIn` annotation for internal APIs. All source sets opt in via `languageSettings`.
- **`SuspendMutableMap`**: Custom interface for async-safe header management (`InMemoryHeaders` default impl).
- Platform `expect`/`actual` declarations live only in `:umami` (HTTP engine selection, user-agent string).

## Toolchain

- Gradle 8.14.2 (use `./gradlew`, not system Gradle).
- JVM toolchain: Java 21 for compilation. CI uses Java 17 (JetBrains distro).
- KMP targets: Android, JVM, JS (browser), WasmJS, iOS, macOS, Linux, Windows.

## Git Conventions

- **NEVER commit or push code autonomously.** Every change must be reviewed by a human before committing.
- Commit messages: Conventional Commits (`feat:`, `fix:`, `chore:`, etc.).
- Branch names: `feature/`, `fix/` prefixes.
- Pre-push hook runs `detekt` automatically (installed via `./gradlew installGitHooks`, which runs on `assemble`).
