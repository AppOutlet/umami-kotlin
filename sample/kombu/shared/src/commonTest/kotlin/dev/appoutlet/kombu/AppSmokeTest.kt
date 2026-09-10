package dev.appoutlet.kombu

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.appoutlet.kombu.navigation.kombuTopLevelDestinations
import io.kotest.matchers.shouldBe
import kotlin.test.Test

/**
 * Smoke tests for the Kombu sample. They prove that the shared test wiring (`kotlin-test`, Kotest
 * matchers and the Compose Multiplatform UI testing API) works from `commonTest`, and that the root
 * [App] composable composes: Koin starts, Navigation3 renders the landing route and the bottom
 * navigation bar lists every top-level destination.
 */
class AppSmokeTest {

    @Test
    fun `should expose Kotest matchers to common tests`() {
        "Kombu".lowercase() shouldBe "kombu"
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun `should compose the root App with all top level destinations`() = runComposeUiTest {
        setContent { App() }

        onNodeWithText("Your analytics overview will live here.").assertIsDisplayed()

        kombuTopLevelDestinations.forEach { destination ->
            onAllNodesWithText(destination.label).fetchSemanticsNodes().isNotEmpty() shouldBe true
        }
    }
}
