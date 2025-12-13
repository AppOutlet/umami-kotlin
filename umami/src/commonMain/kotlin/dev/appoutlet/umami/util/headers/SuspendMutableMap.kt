package dev.appoutlet.umami.util.headers

/**
 * A mutable map with suspendable, non-blocking operations, designed for asynchronous access in
 * coroutine-based environments. This interface mirrors the standard [MutableMap], but its functions
 * are suspendable to allow for implementations that perform I/O, network requests, or other
 * long-running tasks without blocking a thread.
 *
 * Implementations of this interface are expected to be safe for concurrent use.
 *
 * @param K The type of keys maintained by this map.
 * @param V The type of mapped values.
 */
@Suppress("TooManyFunctions")
interface SuspendMutableMap<K, V> {
    /**
     * Associates the specified [value] with the specified [key] in this map.
     * If the map previously contained a mapping for the key, the old value is replaced.
     * This is a suspendable, non-blocking operation.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with [key], or `null` if there was no mapping for [key].
     */
    suspend fun put(key: K, value: V): V?

    /**
     * Removes the mapping for a [key] from this map if it is present.
     * This is a suspendable, non-blocking operation.
     *
     * @param key The key whose mapping is to be removed from the map.
     * @return The previous value associated with [key], or `null` if there was no mapping for [key].
     */
    suspend fun remove(key: K): V?

    /**
     * Copies all of the mappings from the specified map [from] to this map.
     * These mappings will replace any mappings that this map had for any of the keys that are
     * currently in the specified map. This is a suspendable, non-blocking operation.
     *
     * @param from The map whose mappings are to be stored in this map.
     */
    suspend fun putAll(from: Map<out K, V>)

    /**
     * Removes all of the mappings from this map.
     * The map will be empty after this call returns. This is a suspendable, non-blocking operation.
     */
    suspend fun clear()

    /**
     * Returns a read-only [Set] of all keys in this map.
     * The returned set may be a snapshot or a live view, depending on the implementation.
     * This is a suspendable, non-blocking operation.
     *
     * @return A [Set] of the keys contained in this map.
     */
    suspend fun keys(): Set<K>

    /**
     * Returns a read-only [Collection] of all values in this map.
     * The returned collection may be a snapshot or a live view, depending on the implementation.
     * This is a suspendable, non-blocking operation.
     *
     * @return A [Collection] of the values contained in this map.
     */
    suspend fun values(): Collection<V>

    /**
     * Returns a read-only [Set] of all key-value pairs in this map.
     * The returned set may be a snapshot or a live view, depending on the implementation.
     * This is a suspendable, non-blocking operation.
     *
     * @return A [Set] of the key-value entries contained in this map.
     */
    suspend fun entries(): Set<Map.Entry<K, V>>

    /**
     * Checks if this map contains a mapping for the specified [key].
     * This is a suspendable, non-blocking operation.
     *
     * @param key The key whose presence in this map is to be tested.
     * @return `true` if this map contains a mapping for the specified key, `false` otherwise.
     */
    suspend fun containsKey(key: K): Boolean

    /**
     * Returns the value to which the specified [key] is mapped, or `null` if this map contains
     * no mapping for the key. This is a suspendable, non-blocking operation.
     *
     * @param key The key whose associated value is to be returned.
     * @return The value to which the specified key is mapped, or `null` if this map contains no
     * mapping for the key.
     */
    suspend fun get(key: K): V?

    /**
     * Checks if this map contains no key-value mappings.
     * This is a suspendable, non-blocking operation.
     *
     * @return `true` if this map is empty, `false` otherwise.
     */
    suspend fun isEmpty(): Boolean

    /**
     * Returns the number of key-value mappings in this map.
     * This is a suspendable, non-blocking operation.
     *
     * @return The number of entries in this map.
     */
    suspend fun size(): Int
}
