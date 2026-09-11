package dev.appoutlet.kombu

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.v2.runComposeUiTest
import dev.appoutlet.kombu.navigation.kombuTopLevelDestinations
import io.kotest.matchers.shouldBe
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class AppTest {
    @Test
    fun `should compose the root App with all top level destinations`() = runComposeUiTest {
        setContent { App() }

        onNodeWithTag("Navigation")
            .assertIsDisplayed()
    }
}
