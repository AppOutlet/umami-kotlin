package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_HOSTNAME_LENGTH = 100

@JvmInline
value class Hostname(val value: String) {
    init {
        require(value.length < MAX_HOSTNAME_LENGTH) {
            "Hostname should not have more than $MAX_HOSTNAME_LENGTH characters"
        }
    }

    override fun toString(): String = value
}
