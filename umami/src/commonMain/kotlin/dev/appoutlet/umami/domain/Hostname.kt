package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_HOSTNAME_LENGTH = 100

/**
 * Represents a hostname.
 *
 * @property value The string representation of the hostname.
 * @throws IllegalArgumentException if the hostname is longer than [MAX_HOSTNAME_LENGTH] characters.
 */
@JvmInline
value class Hostname(val value: String) {
    init {
        require(value.length < MAX_HOSTNAME_LENGTH) {
            "Hostname should not have more than $MAX_HOSTNAME_LENGTH characters"
        }
    }

    override fun toString(): String = value
}
