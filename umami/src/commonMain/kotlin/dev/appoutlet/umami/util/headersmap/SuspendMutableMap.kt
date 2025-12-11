package dev.appoutlet.umami.util.headersmap

/**
 * A mutable map interface with suspendable operations, allowing for asynchronous implementations.
 * This interface mirrors the standard [MutableMap] but is designed for use in coroutine-based environments
 * where map operations might involve I/O or other long-running tasks.
 *
 * @param K The type of the keys in the map.
 * @param V The type of the values in the map.
 */
@Suppress("TooManyFunctions")
interface SuspendMutableMap<K, V> {
    /**
     * Associates the specified [value] with the specified [key] in this map.
     * If the map previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key The key with which the specified value is to be associated.
     * @param value The value to be associated with the specified key.
     * @return The previous value associated with the key, or `null` if the key was not present in the map.
     */
    suspend fun put(key: K, value: V?): V?

    /**
     * Removes the specified key and its corresponding value from this map.
     *
     * @param key The key of the element to be removed.
     * @return The previous value associated with the key, or `null` if the key was not present in the map.
     */
    suspend fun remove(key: K): V?

    /**
     * Updates this map with the key-value pairs from the specified [from] map.
     * The key-value pairs from [from] will overwrite any existing mappings in this map.
     *
     * @param from The map containing the key-value pairs to be added.
     */
    suspend fun putAll(from: Map<out K, V?>)

    /**
     * Removes all elements from this map, leaving it empty.
     */
    suspend fun clear()

    /**
     * Returns a read-only [Set] of all keys in this map.
     *
     * @return A set of the keys contained in this map.
     */
    suspend fun keys(): Set<K>

    /**
     * Returns a read-only [Collection] of all values in this map.
     *
     * @return A collection of the values contained in this map.
     */
    suspend fun values(): Collection<V>

    /**
     * Returns a read-only [Set] of all key-value pairs in this map.
     *
     * @return A set of the key-value entries contained in this map.
     */
    suspend fun entries(): Set<Map.Entry<K, V>>

    /**
     * Checks if this map contains a mapping for the specified [key].
     *
     * @param key The key whose presence in this map is to be tested.
     * @return `true` if this map contains a mapping for the specified key, `false` otherwise.
     */
    suspend fun containsKey(key: K): Boolean

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in the map.
     *
     * @param key The key of the value to retrieve.
     * @return The value associated with the key, or `null` if the key is not in the map.
     */
    suspend fun get(key: K): V?

    /**
     * Checks if this map is empty.
     *
     * @return `true` if this map contains no key-value mappings, `false` otherwise.
     */
    suspend fun isEmpty(): Boolean

    /**
     * Returns the number of key-value pairs in this map.
     *
     * @return The size of the map.
     */
    suspend fun size(): Int
}
