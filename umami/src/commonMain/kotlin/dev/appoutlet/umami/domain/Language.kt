package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_LANGUAGE_LENGTH = 35

/**
 * Represents a language code.
 *
 * @property value The string representation of the language code (e.g., "en", "pt-BR").
 * @throws IllegalArgumentException if the language code is blank or exceeds [MAX_LANGUAGE_LENGTH] characters.
 */
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
