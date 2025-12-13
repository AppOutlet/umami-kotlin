package dev.appoutlet.umami.util.headers

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class InMemoryHeadersTest {

    @Test
    fun `put should add a new entry and return null`() = runTest {
        val headers = InMemoryHeaders()
        val previousValue = headers.put("key1", "value1")
        previousValue shouldBe null
        headers.get("key1") shouldBe "value1"
    }

    @Test
    fun `put should update an existing entry and return the old value`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        val previousValue = headers.put("key1", "value2")
        previousValue shouldBe "value1"
        headers.get("key1") shouldBe "value2"
    }

    @Test
    fun `remove should delete an entry and return its value`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        val removedValue = headers.remove("key1")
        removedValue shouldBe "value1"
        headers.get("key1") shouldBe null
    }

    @Test
    fun `remove should return null for a non-existent entry`() = runTest {
        val headers = InMemoryHeaders()
        val removedValue = headers.remove("key1")
        removedValue shouldBe null
    }

    @Test
    fun `putAll should add all entries from the given map`() = runTest {
        val headers = InMemoryHeaders()
        val newEntries = mapOf("key1" to "value1", "key2" to "value2")
        headers.putAll(newEntries)
        headers.get("key1") shouldBe "value1"
        headers.get("key2") shouldBe "value2"
        headers.size() shouldBe 2
    }

    @Test
    fun `putAll should overwrite existing entries`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        val newEntries = mapOf("key1" to "newValue", "key2" to "value2")
        headers.putAll(newEntries)
        headers.get("key1") shouldBe "newValue"
        headers.get("key2") shouldBe "value2"
        headers.size() shouldBe 2
    }

    @Test
    fun `clear should remove all entries from the map`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.put("key2", "value2")
        headers.clear()
        headers.isEmpty() shouldBe true
        headers.size() shouldBe 0
    }

    @Test
    fun `keys should return a set of all keys`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.put("key2", "value2")
        val keys = headers.keys()
        keys shouldBe setOf("key1", "key2")
    }

    @Test
    fun `values should return a collection of all values`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.put("key2", "value2")
        val values = headers.values()
        values shouldContain "value1"
        values shouldContain "value2"
        values.size shouldBe 2
    }

    @Test
    fun `entries should return a set of all map entries`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.put("key2", "value2")
        val entries = headers.entries()
        val expectedKeys = setOf("key1", "key2")
        val expectedValues = listOf("value1", "value2")
        entries.size shouldBe 2
        entries.all { it.key in expectedKeys } shouldBe true
        entries.all { it.value in expectedValues } shouldBe true
    }

    @Test
    fun `containsKey should return true for an existing key`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.containsKey("key1") shouldBe true
    }

    @Test
    fun `containsKey should return false for a non-existent key`() = runTest {
        val headers = InMemoryHeaders()
        headers.containsKey("key1") shouldBe false
    }

    @Test
    fun `get should return the value for an existing key`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.get("key1") shouldBe "value1"
    }

    @Test
    fun `get should return null for a non-existent key`() = runTest {
        val headers = InMemoryHeaders()
        headers.get("key1") shouldBe null
    }

    @Test
    fun `isEmpty should return true for a new map`() = runTest {
        val headers = InMemoryHeaders()
        headers.isEmpty() shouldBe true
    }

    @Test
    fun `isEmpty should return false for a non-empty map`() = runTest {
        val headers = InMemoryHeaders()
        headers.put("key1", "value1")
        headers.isEmpty() shouldBe false
    }

    @Test
    fun `size should return the number of entries in the map`() = runTest {
        val headers = InMemoryHeaders()
        headers.size() shouldBe 0
        headers.put("key1", "value1")
        headers.size() shouldBe 1
        headers.put("key2", "value2")
        headers.size() shouldBe 2
        headers.remove("key1")
        headers.size() shouldBe 1
    }
}
