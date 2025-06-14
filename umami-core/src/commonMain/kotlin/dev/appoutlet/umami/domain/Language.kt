package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_LANGUAGE_LENGTH = 35

@JvmInline
value class Language(val value: String) {
    init {
        require(value.isNotBlank()) { "Language cannot be blank" }
        require(value.length <= MAX_LANGUAGE_LENGTH) {
            "Language code should not exceed $MAX_LANGUAGE_LENGTH characters"
        }
    }

    override fun toString(): String = value
}
