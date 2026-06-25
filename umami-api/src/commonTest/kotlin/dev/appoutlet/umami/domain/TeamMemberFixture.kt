package dev.appoutlet.umami.domain

import kotlin.time.Instant

fun TeamMember.Companion.fixture(
    id: String = "550e8400-e29b-41d4-a716-446655440200",
    teamId: String = "550e8400-e29b-41d4-a716-446655440002",
    userId: String = "550e8400-e29b-41d4-a716-446655440001",
    role: String = "team-member",
    createdAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    updatedAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    user: User? = User.fixture(),
): TeamMember = TeamMember(
    id = id,
    teamId = teamId,
    userId = userId,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt,
    user = user,
)
