package dev.appoutlet.umami.domain

import kotlin.time.Instant

fun Team.Companion.fixture(
    id: String = "550e8400-e29b-41d4-a716-446655440300",
    name: String = "Umami Team",
    accessCode: String = "umami-team-access",
    logoUrl: String? = "https://umami.is/team-logo.png",
    createdAt: Instant = Instant.parse("2025-10-27T18:50:54.079Z"),
    updatedAt: Instant? = Instant.parse("2025-10-27T18:50:54.079Z"),
    deletedAt: Instant? = Instant.parse("2025-10-27T18:50:54.079Z"),
    teamUser: List<TeamMember> = listOf(
        TeamMember.fixture(),
        TeamMember.fixture(),
        TeamMember.fixture(),
    ),
): Team = Team(
    id = id,
    name = name,
    accessCode = accessCode,
    logoUrl = logoUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
    deletedAt = deletedAt,
    teamUser = teamUser,
)
