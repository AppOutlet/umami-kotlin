package dev.appoutlet.kombu

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.v2.runComposeUiTest
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
