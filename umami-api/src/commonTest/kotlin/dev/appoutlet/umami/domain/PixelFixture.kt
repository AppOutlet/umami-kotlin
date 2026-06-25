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
