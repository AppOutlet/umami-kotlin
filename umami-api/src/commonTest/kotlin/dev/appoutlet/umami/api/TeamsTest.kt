package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.TeamMember
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.client.engine.mock.respondOk
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class TeamsTest {
    @Test
    fun `find should return a list of teams`() = runTest {
        val expectedResponse = SearchResponse(
            data = listOf(
                Team(
                    id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
                    name = "Test Team",
                    accessCode = "test-code",
                    createdAt = Instant.parse("2022-01-01T00:00:00Z"),
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/teams" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().find()
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `create should return the created team`() = runTest {
        val expectedResponse = Team(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            name = "Test Team",
            accessCode = "test-code",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/teams" to {
                it.body<CreateTeamRequest>().name shouldBe "Test Team"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().create("Test Team")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `join should return the team membership`() = runTest {
        val expectedResponse = TeamMember(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            role = "member",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2022-01-01T00:00:00Z")
        )

        val umami = getUmamiInstance(
            "/api/teams/join" to {
                it.body<JoinTeamRequest>().accessCode shouldBe "test-code"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().join("test-code")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `get should return a team`() = runTest {
        val expectedResponse = Team(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            name = "Test Team",
            accessCode = "test-code",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/teams/e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().get("e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `update should return the updated team`() = runTest {
        val expectedResponse = Team(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            name = "Updated Team",
            accessCode = "updated-code",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
        )

        val umami = getUmamiInstance(
            "/api/teams/e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e" to {
                val body = it.body<UpdateTeamRequest>()
                body.name shouldBe "Updated Team"
                body.accessCode shouldBe "updated-code"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().update(
            "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            "Updated Team",
            "updated-code"
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `delete should not throw an exception on success`() = runTest {
        val umami = getUmamiInstance(
            "/api/teams/e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e" to {
                respondOk()
            }
        )

        umami.teams().delete("e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e")
    }

    @Test
    fun `getUsers should return a list of team members`() = runTest {
        val expectedResponse = SearchResponse(
            data = listOf(
                TeamMember(
                    id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
                    teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
                    userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
                    role = "member",
                    createdAt = Instant.parse("2022-01-01T00:00:00Z"),
                    updatedAt = Instant.parse("2022-01-01T00:00:00Z")
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/users" to {
                it.url.parameters["search"] shouldBe "test"
                it.url.parameters["page"] shouldBe "2"
                it.url.parameters["pageSize"] shouldBe "20"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().getUsers(
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            search = "test",
            page = 2,
            pageSize = 20
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `addUser should return the new team member`() = runTest {
        val expectedResponse = TeamMember(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            role = "team-member",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2022-01-01T00:00:00Z")
        )

        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/users" to {
                val body = it.body<AddUserRequest>()
                body.userId shouldBe "f0e9d8c7-b6a5-4321-fedc-ba9876543210"
                body.role shouldBe "team-member"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().addUser(
            "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            "team-member"
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `getUser should return a team member`() = runTest {
        val expectedResponse = TeamMember(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            role = "member",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2022-01-01T00:00:00Z")
        )

        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/users/f0e9d8c7-b6a5-4321-fedc-ba9876543210" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().getUser(
            "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            "f0e9d8c7-b6a5-4321-fedc-ba9876543210"
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `updateUserRole should return the updated team member`() = runTest {
        val expectedResponse = TeamMember(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            role = "team-manager",
            createdAt = Instant.parse("2022-01-01T00:00:00Z"),
            updatedAt = Instant.parse("2022-01-01T00:00:00Z")
        )

        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/users/f0e9d8c7-b6a5-4321-fedc-ba9876543210" to {
                it.body<UpdateUserRoleRequest>().role shouldBe "team-manager"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().updateUserRole(
            "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
            "team-manager"
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `removeUser should not throw an exception on success`() = runTest {
        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/users/f0e9d8c7-b6a5-4321-fedc-ba9876543210" to {
                respondOk()
            }
        )

        umami.teams().removeUser(
            "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            "f0e9d8c7-b6a5-4321-fedc-ba9876543210"
        )
    }

    @Test
    fun `getWebsites should return a list of websites`() = runTest {
        val expectedResponse = SearchResponse(
            data = listOf(
                Website(
                    id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
                    name = "Test Website",
                    domain = "test.com",
                    shareId = null,
                    createdAt = Instant.parse("2022-01-01T00:00:00Z"),
                    userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210",
                    createdBy = "f0e9d8c7-b6a5-4321-fedc-ba9876543210"
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val umami = getUmamiInstance(
            "/api/teams/a1b2c3d4-e5f6-7890-1234-567890abcdef/websites" to {
                it.url.parameters["search"] shouldBe "test"
                it.url.parameters["page"] shouldBe "2"
                it.url.parameters["pageSize"] shouldBe "20"
                respond(expectedResponse)
            }
        )

        val actualResponse = umami.teams().getWebsites(
            teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef",
            search = "test",
            page = 2,
            pageSize = 20
        )
        actualResponse shouldBe expectedResponse
    }
}
