package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.TeamMember
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.domain.fixture
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiApiInstance
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
            data = listOf(Team.fixture()),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val api = getUmamiApiInstance(
            "/api/teams" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().find()
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `create should return the created team`() = runTest {
        val expectedResponse = Team.fixture(name = "Test Team")

        val api = getUmamiApiInstance(
            "/api/teams" to {
                it.body<CreateTeamRequest>().name shouldBe expectedResponse.name
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().create(expectedResponse.name)
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `join should return the team membership`() = runTest {
        val expectedResponse = TeamMember.fixture()

        val api = getUmamiApiInstance(
            "/api/teams/join" to {
                it.body<JoinTeamRequest>().accessCode shouldBe "test-code"
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().join("test-code")
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `get should return a team`() = runTest {
        val expectedResponse = Team.fixture(id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e")

        val api = getUmamiApiInstance(
            "/api/teams/${expectedResponse.id}" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().get(expectedResponse.id)
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `update should return the updated team`() = runTest {
        val expectedResponse = Team.fixture(
            id = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e",
            name = "Updated Team",
            accessCode = "updated-code",
        )

        val api = getUmamiApiInstance(
            "/api/teams/${expectedResponse.id}" to {
                val body = it.body<UpdateTeamRequest>()
                body.name shouldBe expectedResponse.name
                body.accessCode shouldBe expectedResponse.accessCode
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().update(
            expectedResponse.id,
            expectedResponse.name,
            expectedResponse.accessCode,
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `delete should not throw an exception on success`() = runTest {
        val teamId = "e6e4f1a6-2b4c-4c7a-9f5b-1e2a3b4c5d6e"
        val api = getUmamiApiInstance(
            "/api/teams/$teamId" to {
                respondOk()
            }
        )

        api.teams().delete(teamId)
    }

    @Test
    fun `getUsers should return a list of team members`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"

        val expectedResponse = SearchResponse(
            data = listOf(TeamMember.fixture()),
            count = 1,
            page = 1,
            pageSize = 10
        )

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/users" to {
                it.url.parameters["search"] shouldBe "test"
                it.url.parameters["page"] shouldBe "2"
                it.url.parameters["pageSize"] shouldBe "20"
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().getUsers(
            teamId = teamId,
            search = "test",
            page = 2,
            pageSize = 20
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `addUser should return the new team member`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
        val userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210"

        val expectedResponse = TeamMember.fixture(role = "team-member")

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/users" to {
                val body = it.body<AddUserRequest>()
                body.userId shouldBe userId
                body.role shouldBe expectedResponse.role
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().addUser(
            teamId,
            userId,
            expectedResponse.role,
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `getUser should return a team member`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
        val userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210"

        val expectedResponse = TeamMember.fixture()

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/users/$userId" to {
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().getUser(teamId, userId)
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `updateUserRole should return the updated team member`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
        val userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210"

        val expectedResponse = TeamMember.fixture(role = "team-manager")

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/users/$userId" to {
                it.body<UpdateUserRoleRequest>().role shouldBe expectedResponse.role
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().updateUserRole(
            teamId,
            userId,
            expectedResponse.role,
        )
        actualResponse shouldBe expectedResponse
    }

    @Test
    fun `removeUser should not throw an exception on success`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"
        val userId = "f0e9d8c7-b6a5-4321-fedc-ba9876543210"

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/users/$userId" to {
                respondOk()
            }
        )

        api.teams().removeUser(teamId, userId)
    }

    @Test
    fun `getWebsites should return a list of websites`() = runTest {
        val teamId = "a1b2c3d4-e5f6-7890-1234-567890abcdef"

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

        val api = getUmamiApiInstance(
            "/api/teams/$teamId/websites" to {
                it.url.parameters["search"] shouldBe "test"
                it.url.parameters["page"] shouldBe "2"
                it.url.parameters["pageSize"] shouldBe "20"
                respond(expectedResponse)
            }
        )

        val actualResponse = api.teams().getWebsites(
            teamId = teamId,
            search = "test",
            page = 2,
            pageSize = 20
        )
        actualResponse shouldBe expectedResponse
    }
}
