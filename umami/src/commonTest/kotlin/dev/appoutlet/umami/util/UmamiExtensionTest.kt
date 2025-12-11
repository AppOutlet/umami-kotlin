package dev.appoutlet.umami.util

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.util.logger.UmamiLogger
import dev.mokkery.mock
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class UmamiExtensionTest {
    private val mockLogger = mock<UmamiLogger>()

    @Test
    fun `should return logger`() = runTest {
        val umami = Umami(website = Uuid.random().toString()) {
            logger = mockLogger
        }

        umami.logger shouldBe mockLogger
    }
}
