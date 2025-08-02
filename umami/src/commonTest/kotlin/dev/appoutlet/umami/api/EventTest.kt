package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.test.runTest

class EventTest {
    @OptIn(ExperimentalUuidApi::class)
    private val umami = Umami.create(website = Uuid.random().toString())

    @Test
    fun `track event`() = runTest {
        1 shouldBe 1
    }
}