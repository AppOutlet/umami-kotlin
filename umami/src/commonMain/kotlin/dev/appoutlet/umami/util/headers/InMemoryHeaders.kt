package dev.appoutlet.umami.util.headers

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * A thread-safe, in-memory implementation of [SuspendMutableMap] for storing string key-value pairs,
 * typically used for managing headers in a concurrent environment.
 *
 * This class ensures that all operations on the underlying map are atomic and safe to call from
 * multiple coroutines simultaneously. It uses a [Mutex] to protect access to the internal
 * [MutableMap]. The suspendable functions provide a non-blocking API for asynchronous access.
 *
 * The collections returned by [keys], [values], and [entries] are immutable snapshots of the
 * map's state at the time of the call, ensuring thread safety when iterating over them.
 */
@Suppress("TooManyFunctions")
class InMemoryHeaders : SuspendMutableMap<String, String> {
    private val mutex = Mutex()
    private val headers: MutableMap<String, String> = mutableMapOf()

    /**
     * Associates the specified [value] with the specified [key] in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * This operation is performed atomically and is thread-safe.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with [key], or `null` if there was no mapping for [key].
     */
    override suspend fun put(key: String, value: String) = mutex.withLock { headers.put(key, value) }

    /**
     * Removes the mapping for a [key] from this map if it is present.
     * This operation is performed atomically and is thread-safe.
     *
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with [key], or `null` if there was no mapping for [key].
     */
    override suspend fun remove(key: String) = mutex.withLock { headers.remove(key) }

    /**
     * Copies all of the mappings from the specified map [from] to this map.
     * These mappings will replace any mappings that this map had for any of the keys that are
     * currently in the specified map. This operation is performed atomically and is thread-safe.
     *
     * @param from The map whose mappings are to be stored in this map.
     */
    override suspend fun putAll(from: Map<out String, String>) = mutex.withLock { headers.putAll(from) }

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns. This operation is performed atomically and is
     * thread-safe.
     */
    override suspend fun clear() = mutex.withLock { headers.clear() }

    /**
     * Returns an immutable snapshot of all keys in this map.
     * The returned set is a copy and will not reflect subsequent changes to the map.
     * This operation is thread-safe.
     *
     * @return An immutable [Set] of the keys contained in this map.
     */
    override suspend fun keys() = mutex.withLock { headers.keys.toSet() }

    /**
     * Returns an immutable snapshot of all values in this map.
     * The returned list is a copy and will not reflect subsequent changes to the map.
     * This operation is thread-safe.
     *
     * @return An immutable [List] of the values contained in this map.
     */
    override suspend fun values() = mutex.withLock { headers.values.toList() }

    /**
     * Returns an immutable snapshot of all key-value pairs in this map.
     * The returned set is a copy and will not reflect subsequent changes to the map.
     * This operation is thread-safe.
     *
     * @return An immutable [Set] of map entries.
     */
    override suspend fun entries() = mutex.withLock { headers.entries.toSet() }

    /**
     * Checks if this map contains a mapping for the specified [key].
     * This operation is thread-safe.
     *
     * @param key The key whose presence in this map is to be tested.
     * @return `true` if this map contains a mapping for the specified key, `false` otherwise.
     */
    override suspend fun containsKey(key: String) = mutex.withLock { headers.containsKey(key) }

    /**
     * Returns the value to which the specified [key] is mapped, or `null` if this map contains
     * no mapping for the key. This operation is thread-safe.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or `null` if this map contains no
     * mapping for the key.
     */
    override suspend fun get(key: String) = mutex.withLock { headers[key] }

    /**
     * Checks if this map contains no key-value mappings.
     * This operation is thread-safe.
     *
     * @return `true` if this map is empty, `false` otherwise.
     */
    override suspend fun isEmpty() = mutex.withLock { headers.isEmpty() }

    /**
     * Returns the number of key-value mappings in this map.
     * This operation is thread-safe.
     *
     * @return The number of entries in this map.
     */
    override suspend fun size() = mutex.withLock { headers.size }
}
