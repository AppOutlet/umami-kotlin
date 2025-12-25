package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.SearchResponse
import dev.appoutlet.umami.domain.Session
import dev.appoutlet.umami.domain.Team
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.domain.Website
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.time.Instant

class MeTest {

    @Test
    fun `getSession returns session`() = runTest {
        val expectedSession = Session(
            token = "test_token",
            authKey = "test_auth_key",
            user = User(
                id = "user_id",
                username = "test_user",
                role = "user",
                createdAt = Instant.DISTANT_PAST,
            )
        )

        val umami = getUmamiInstance(
            "/api/me" to { respond(expectedSession) }
        )

        val actualSession = umami.me().getSession()

        actualSession shouldBe expectedSession
    }

    @Test
    fun `getTeams returns teams`() = runTest {
        val expectedTeams = SearchResponse(
            data = listOf(
                Team(
                    id = "team_id",
                    name = "test_team",
                    accessCode = "test_access_code",
                    createdAt = Instant.DISTANT_PAST,
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10,
        )

        val umami = getUmamiInstance(
            "/api/me/teams" to { respond(expectedTeams) }
        )

        val actualTeams = umami.me().getTeams()

        actualTeams shouldBe expectedTeams
    }

    @Test
    fun `getWebsites returns websites and passes parameters correctly`() = runTest {
        val expectedWebsites = SearchResponse(
            data = listOf(
                Website(
                    id = "website_id",
                    name = "test_website",
                    domain = "test.com",
                    userId = "user_id",
                    createdBy = "user_id",
                    createdAt = Instant.DISTANT_PAST,
                )
            ),
            count = 1,
            page = 1,
            pageSize = 10,
        )

        val umami = getUmamiInstance(
            "/api/me/websites" to { request ->
                request.url.parameters["includeTeams"] shouldBe "true"
                respond(expectedWebsites)
            }
        )

        val actualWebsites = umami.me().getWebsites(includeTeams = true)

        actualWebsites shouldBe expectedWebsites
    }
}
