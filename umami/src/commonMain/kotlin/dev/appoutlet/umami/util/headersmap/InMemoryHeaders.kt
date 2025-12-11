package dev.appoutlet.umami.util.headersmap

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * An in-memory implementation of [SuspendMutableMap] that stores key-value pairs.
 * This class is thread-safe through the use of a [Mutex] to protect concurrent access.
 * It wraps a standard [MutableMap] and provides a suspendable interface for its operations.
 */
class InMemoryHeaders : SuspendMutableMap<String, String?> {
    private val mutex = Mutex()
    private val headers: MutableMap<String, String?> = mutableMapOf()

    /**
     * Associates the specified [value] with the specified [key] in this map in a thread-safe manner.
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key, or `null` if the key was not present in the map.
     */
    override suspend fun put(key: String, value: String?) = mutex.withLock { headers.put(key, value) }

    /**
     * Removes the specified key and its corresponding value from this map in a thread-safe manner.
     *
     * @param key The key of the element to be removed.
     * @return The previous value associated with the key, or `null` if the key was not present in the map.
     */
    override suspend fun remove(key: String) = mutex.withLock { headers.remove(key) }

    /**
     * Updates this map with the key-value pairs from the specified [from] map in a thread-safe manner.
     * The key-value pairs from [from] will overwrite any existing mappings in this map.
     *
     * @param from The map containing the key-value pairs to be added.
     */
    override suspend fun putAll(from: Map<out String, String?>) = mutex.withLock { headers.putAll(from) }

    /**
     * Removes all elements from this map in a thread-safe manner, leaving it empty.
     */
    override suspend fun clear() = mutex.withLock { headers.clear() }

    /**
     * Returns a read-only [Set] of all keys in this map in a thread-safe manner.
     * The returned set is a copy to prevent concurrent modification issues.
     *
     * @return A new set containing the keys of this map.
     */
    override suspend fun keys() = mutex.withLock { headers.keys.toSet() }

    /**
     * Returns a read-only [Collection] of all values in this map in a thread-safe manner.
     * The returned collection is a copy to prevent concurrent modification issues.
     *
     * @return A new list containing the values of this map.
     */
    override suspend fun values() = mutex.withLock { headers.values.toList() }

    /**
     * Returns a read-only [Set] of all key-value pairs in this map in a thread-safe manner.
     * The returned set is a copy to prevent concurrent modification issues.
     *
     * @return A new set containing the key-value entries of this map.
     */
    override suspend fun entries() = mutex.withLock { headers.entries.toSet() }

    /**
     * Checks if this map contains a mapping for the specified [key] in a thread-safe manner.
     *
     * @param key The key whose presence in this map is to be tested.
     * @return `true` if this map contains a mapping for the specified key, `false` otherwise.
     */
    override suspend fun containsKey(key: String) = mutex.withLock { headers.containsKey(key) }

    /**
     * Returns the value corresponding to the given [key] in a thread-safe manner,
     * or `null` if such a key is not present in the map.
     *
     * @param key The key of the value to retrieve.
     * @return The value associated with the key, or `null` if the key is not in the map.
     */
    override suspend fun get(key: String) = mutex.withLock { headers[key] }

    /**
     * Checks if this map is empty in a thread-safe manner.
     *
     * @return `true` if this map contains no key-value mappings, `false` otherwise.
     */
    override suspend fun isEmpty() = mutex.withLock { headers.isEmpty() }

    /**
     * Returns the number of key-value pairs in this map in a thread-safe manner.
     *
     * @return The size of the map.
     */
    override suspend fun size() = mutex.withLock { headers.size }
}
