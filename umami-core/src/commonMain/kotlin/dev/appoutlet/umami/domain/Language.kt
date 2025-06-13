package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

@JvmInline
value class Language(val value: String) {
    init {
        require(value.isNotBlank()) { "Language cannot be blank" }
        require(value.length <= 35) { "Language code should not exceed 10 characters" }
    }

    override fun toString(): String = value
}