package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MaxHostnameLength = 100

@JvmInline
value class Hostname(val value: String) {
    init {
        require(value.length < MaxHostnameLength) {
            "Hostname should not have more than $MaxHostnameLength characters"
        }
    }

    override fun toString(): String = value
}
