package dev.appoutlet.umami.domain

import kotlin.time.Instant

fun User.Companion.fixture(
    id: String = "550e8400-e29b-41d4-a716-446655440100",
    username: String = "ada-lovelace",
    role: String = "admin",
    createdAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    updatedAt: Instant? = Instant.parse("2025-10-27T18:50:54.079Z"),
    deletedAt: Instant? = Instant.parse("2025-10-27T18:50:54.079Z"),
    logoUrl: String? = "https://umami.is/logo.png",
    displayName: String? = "Ada Lovelace",
    isAdmin: Boolean = true,
    teams: List<Team> = emptyList(),
): User = User(
    id = id,
    username = username,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    logoUrl = logoUrl,
    displayName = displayName,
    isAdmin = isAdmin,
    teams = teams,
)
