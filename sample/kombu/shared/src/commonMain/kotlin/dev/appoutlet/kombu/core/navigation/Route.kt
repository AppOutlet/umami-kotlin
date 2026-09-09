package dev.appoutlet.kombu.core.navigation

import androidx.navigation3.runtime.NavKey

/**
 * A navigation destination. Implementations are typically [kotlinx.serialization.Serializable]
 * so the back stack can be persisted and restored, including across web browser history.
 */
interface Route : NavKey {
    /** Stable identifier used to key the route in the browser history. */
    val key: String

    /** Key/value parameters serialized into the browser history fragment for this route. */
    fun toMap(): Map<String, String?>
}