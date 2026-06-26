---
name: fixtures
description: |
  Create test fixtures for Kotlin domain models. Use this skill whenever you need to create a sample object (a "fixture") for use in tests, examples, or whenever representative domain data is required.
  Specifically, use this skill when you are:
  - Writing tests and need sample instances of domain models (e.g. `Pixel`, `User`, `Team`).
  - Creating builder/factory-style helpers for tests (a "Fixture").
  - Asked to "create a fixture", "make a `XxxFixture`", "add a sample object", or "generate test data".
  - Reviewing or editing tests that lack sample data and would benefit from fixtures.
  Trigger this skill proactively when test files contain inline object literals with hard-coded field values that could be replaced with a fixture, or when a domain model gains new fields that existing fixtures should mirror.
---

# Fixtures skill

A *fixture* is a thin factory function that produces a fully-populated instance of a domain model with sensible defaults, so tests can focus on the values they actually care about.

## When to create a fixture

Create a fixture for a domain model whenever that model needs to be instantiated in more than one test (or even in a single test, for readability). The fixture lives in `commonTest` alongside other test sources, in the same package as the model it produces.

## How a fixture is structured

A fixture is a top-level extension function on the model's `Companion` object named `fixture`. It takes the *exact same arguments as the primary constructor* of the model, each with a sensible default, and returns a new instance built from it.

**Package placement — critical:** the fixture must live in the *exact same package* as the domain model (e.g. `dev.appoutlet.umami.domain`), in the **testing source set** (`commonTest`), never in `commonMain`. The production source never sees the fixture; only tests do. Because the package matches, tests importing the model can reach the extension with a single `import dev.appoutlet.umami.domain.fixture` line and no extra package gymnastics.

### Rules for defaults (HARD RULES — no exceptions)

The following are **hard rules**, not suggestions. Every fixture must satisfy all of them. A fixture that violates any rule is wrong and must be corrected before it ships.

1. **Mirror the constructor signature exactly — same names, same order, same nullability.** This keeps the fixture trivial to scan against the model and forces every field to be considered. Never omit a constructor parameter, even ones with defaults in the model itself — restate them with explicit fixture values so callers don't have to consult the model.
2. **Null values are strictly prohibited as defaults.** A nullable constructor field (`teamId: String?`, `deletedAt: Instant?`) still receives a **non-null** default — a real UUID, a real `Instant`, etc. The `?` in the signature is kept only to mirror the constructor's nullability; the default value itself is never `null`. A null default hides the field in serialization and assertions, which defeats the purpose of a representative sample.
3. **Reuse another domain model's fixture for domain-typed arguments — and create it in cascade if missing.** If a constructor field is itself a domain model (e.g. `User.teams: List<Team>`), the default **must** be `Team.fixture()` — never a hand-written literal. This keeps sample data consistent across fixtures and avoids divergence as models evolve. If the referenced fixture does not yet exist, create it *before* finishing the current one (see "Cascade creation"). Never duplicate an existing fixture — always check the test source set first.
4. **Empty lists (and empty collections) are prohibited as defaults — lists MUST have at least three elements.** A `List<Bar>` field defaults to `listOf(Bar.fixture(), Bar.fixture(<distinguishing arg>), Bar.fixture(<another distinguishing arg>))` — three distinct entries, never `emptyList()`. Vary a field like `name` so the elements are distinct and debugging reads naturally. Example: `teams: List<Team> = listOf(Team.fixture(), Team.fixture(name = "Org team"), Team.fixture(name = "Marketing team"))`.
5. **Scalars use meaningful, human-readable values, not the type's zero value.** UUIDs look like UUIDs, names read like real entities (`"Umami Pixel"`), enums/roles match documented values (`"admin"`), timestamps use a fixed ISO instant. `false`, `0`, `""`, and `"test"` are forbidden as defaults. Stable, readable defaults make failures obvious and assertions self-documenting.
6. **Reproducible across runs.** Defaults must not depend on the current time, randomness, or environment — the same fixture call must always produce the same value, so test failures are deterministic.

## Reference example

`umami-api/src/commonTest/kotlin/dev/appoutlet/umami/domain/PixelFixture.kt` is the canonical example. Note how:

- The function is an extension on `Pixel.Companion`, so callers write `Pixel.fixture()`.
- The signature mirrors `Pixel`'s constructor exactly (`id`, `name`, `slug`, `userId`, `teamId`, `createdAt`, `updatedAt`, `deletedAt`), preserving nullability (`teamId: String?`, `deletedAt: Instant?`) while every default is non-null (rule 2).
- Even nullable fields (`teamId`, `deletedAt`) get non-null defaults — a real UUID and a real `Instant`.
- Timestamps use a fixed ISO instant (`Instant.parse("2025-10-27T18:50:54.079Z")`).

```kotlin
package dev.appoutlet.umami.domain

import kotlin.time.Instant

fun Pixel.Companion.fixture(
    id: String = "550e8400-e29b-41d4-a716-446655440000",
    name: String = "Umami Pixel",
    slug: String = "umami-pixel",
    userId: String = "550e8400-e29b-41d4-a716-446655440001",
    teamId: String? = "550e8400-e29b-41d4-a716-446655440002",
    createdAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    updatedAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    deletedAt: Instant? = Instant.parse("2025-10-27T18:50:54.079Z"),
): Pixel = Pixel(
    id = id,
    name = name,
    slug = slug,
    userId = userId,
    teamId = teamId,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
)
```

## Companion requirement

The fixture is an extension on the model's `Companion` object (e.g. `Pixel.Companion.fixture` / called as `Pixel.fixture()`). All `data class` models in this codebase have a default `Companion` (the compiler synthesizes one for `@Serializable` data classes), so no additional setup is needed. Do not add a custom `companion object` to the production model just to host the fixture — the fixture lives in `commonTest` and extends the synthesized companion.

## Where fixtures live

- **Location**: the testing source set of the module that owns the model — `umami-api/src/commonTest/kotlin/dev/appoutlet/umami/domain/<Model>Fixture.kt` for `:umami-api`, `umami/src/commonTest/...` for `:umami`. One file per model.
- **Naming**: `<Model>Fixture.kt`.
- **Package**: the *exact same package* as the model (e.g. `dev.appoutlet.umami.domain`), even though the file lives in `commonTest`. This lets tests reach the `fixture` extension with a single import of `dev.appoutlet.umami.domain.fixture` alongside the model (see `PixelsTest.kt`).

## Cascade creation

Fixtures for a model rarely stand alone — the constructor usually references other domain models, and rule 3 says to default those fields to the referenced model's own `fixture()` call. Producing the requested fixture therefore often means *also* producing fixtures for its dependencies first. Do this **recursively and in cascade**, following a strict discipline so dependencies land exactly once:

1. **Scan first, create second.** Before defining any defaults, read the model's constructor and collect every non-scalar type that's a domain model of this codebase (singletons and `List<DomainModel>` alike). This is the dependency set.
2. **Check before creating — no duplication.** For each dependency, search the module's `commonTest` source set for an existing `<Model>Fixture.kt` that defines `Model.Companion.fixture`. Use `Glob` on `**/<Model>Fixture.kt` and `Grep` for `fun <Model>\.Companion\.fixture` to be sure. If found, reuse it; do **not** recreate it, even if you'd write the defaults slightly differently. Consistency with one existing fixture beats diverging across copies.
3. **Recurse depth-first.** If a dependency has no fixture yet, recurse into it *now*: read its model, build its fixture, write it to disk, and only then return to the parent. Leaves get created first, then their parents, so each fixture's defaults can reference fixtures that already compile.
4. **Break cycles safely.** If a dependency's fixture already exists *or* is currently in progress in this same cascade pass (i.e., you've opened its model but not yet written its fixture), stop recursing for that branch: an existing fixture must be reused, not rebuilt (rule 2), and a half-built fixture will be finished by its own recursion pass. Domain models with mutual references (rare) should have one direction use plain literals or an explicit `= null` for the nullable side to keep the graph acyclic at construction time.
5. **One fixture per model, ever.** Across the whole cascade, each model gets written exactly once. Never emit two `<Model>Fixture.kt` files, even in different modules — if the same domain class appears in both `:umami` and `:umami-api`, reuse the `:umami` fixture from `:umami-api`'s tests rather than duplicating (the modules already differ in this respect; check the sibling `testing` package for cross-module helpers before creating a parallel copy).
6. **Mention what you did.** When you finish the originally-requested fixture, report which additional fixtures you created in the cascade and which you reused. This keeps the human reviewer informed and surfaces accidental duplication before it ships.

## When the domain model changes

Fixtures mirror the constructor. Whenever a domain model gains, removes, or reorders constructor parameters, update the corresponding fixture in the same change:

1. Add the new parameter to the fixture with a matching default.
2. Pass it through to the constructor call.
3. If it's a new nested domain type, run the cascade check (above) — reuse an existing fixture or create the new one first, then reference it as the default.
4. Existing tests that call `Model.fixture()` without arguments keep working because the new parameter has a default — but tests that previously relied on absence of the field may need updates to assert expected new behavior.

This is why every constructor parameter must be restated explicitly in the fixture (rule 1): if a new field is added to the model, a compile error at the fixture forces the author to choose a meaningful default rather than silently inheriting one from the model.

## Creating a fixture — step-by-step

Given a domain model `Foo` in `dev.appoutlet.umami.domain`:

1. **Resolve the dependency set first.** Open `Foo` and read its primary constructor. For each parameter whose type is another domain model `Bar` (single or `List<Bar>`), run the cascade check from the "Cascade creation" section: `Glob`/`Grep` for `BarFixture.kt` in `commonTest`. For each missing fixture, recurse through this same step-by-step to produce `BarFixture.kt` *before* returning to `Foo`. Stop recursing once every referenced domain model in the set has a fixture on disk (or is a non-domain type).
2. For each parameter of `Foo`, decide the default:
    - `String` (id) → a UUID-shaped literal, unique within this fixture.
    - `String` (name/description) → a human-readable value tied to the model (`"Foo bar"`).
    - `String?` (nullable ID) → still a UUID literal (rule 2).
    - `Instant` → a fixed ISO timestamp, consistent with other fixtures in the file.
    - `Instant?` → still a fixed `Instant`, not null (rule 2).
    - Domain model `Bar` → `Bar.fixture()` (rule 3). At this point `BarFixture.kt` is guaranteed to exist from step 1 — do not recreate it.
    - `List<Bar>` → `listOf(Bar.fixture(), Bar.fixture(<distinguishing arg>), Bar.fixture(...))` — three entries (rule 4). Vary a field like `name` so the elements are distinct and debugging reads naturally.
    - Boolean / numeric / enum-string → a meaningful value (`isAdmin: Boolean = true`, not the type's zero value).
3. Create the file in the **testing source set** (`commonTest`) under the module that owns the model, using the model's *exact package*: e.g. `umami-api/src/commonTest/kotlin/dev/appoutlet/umami/domain/FooFixture.kt`. Do not place fixtures in `commonMain`.
4. Define `fun Foo.Companion.fixture(...)` in that same package, with the parameters and defaults from step 2, in the same order as the constructor.
5. Return `Foo(...)` forwarding each argument.
6. No import of nested fixture extensions is needed when they share `Foo`'s package; for cross-package domain references, add the matching `import ...fixture` lines.

## Fixture usage (HARD RULE)

**Pass custom values to a fixture only when the test specifically asserts on, or relies on, that value.** In every other case, call `Model.fixture()` with no arguments and trust the defaults.

The purpose of a fixture is to make tests read as *“given some representative `Pixel`, when … then …”* rather than as a wall of hard-coded literals. Over-specifying defeats that purpose: if a test passes `name = "Umami Pixel"` to `Pixel.fixture(name = "Umami Pixel")` just because that happens to be the default, the test gains noise without gaining clarity, and when the default later changes the test silently keeps its now-redundant override.

- Call `Pixel.fixture()` bare for any `Pixel` the test does not directly assert on or use as input to an assertion.
- Only override a parameter when its value is **load-bearing** for the test — i.e., the test asserts the value equals something, branches on it, or the API call under test must send/return that exact value. Example: `getPixel(pixelId)` where `pixelId` must match the mocked URL path.
- Never copy a default value into a call site as an override. If you find yourself writing `Pixel.fixture(name = "Umami Pixel")` where `"Umami Pixel"` is already the default, delete the override and call `Pixel.fixture()`.
- When a test does override, override **only** the load-bearing parameters; let every other field take its default. Mixing one custom value with a handful of restated defaults obscures which field the test actually cares about.

## Quick checklist before finishing (every item is a hard pass/fail gate)

- [ ] Every constructor parameter is restated, in order, with preserved nullability.
- [ ] No default is `null` — even nullable fields have a non-null default.
- [ ] No default is an empty collection — lists have ≥ 3 distinct elements.
- [ ] Domain-typed fields default to that domain's fixture (cascade-created if missing, never duplicated).
- [ ] Scalars are meaningful and stable (no `""`, `"test"`, or `now()`).
- [ ] One model per file, named `<Model>Fixture.kt`, in the model's exact package, in the `commonTest` testing source set (never `commonMain`).
- [ ] Extension on the synthesized `Companion`, callable as `Model.fixture()`.
- [ ] The fixture compiles in `commonTest`; no production source depends on it.
- [ ] Tests call `Model.fixture()` bare unless a parameter is load-bearing for that specific test.

## When not to use this skill

- Production or non-test code — fixtures are test-only.
- Models that already have a trivial, all-defaults constructor and are never used in a test with varied fields; inline literals are fine for a one-off.
- Builders/constructors that need validation side effects or inter-field invariants — fixtures produce plain values and shouldn't encode business rules.
