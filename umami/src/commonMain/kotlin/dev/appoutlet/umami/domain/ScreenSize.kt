package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_SCREEN_SIZE_LENGTH = 11

@JvmInline
value class ScreenSize(val value: String) {

    init {
        require(value.isNotBlank()) { "Screen size cannot be blank" }
        require(value.length <= MAX_SCREEN_SIZE_LENGTH) {
            "Screen size should not exceed $MAX_SCREEN_SIZE_LENGTH characters"
        }
    }

    constructor(width: Int, height: Int) : this("$width x $height")

    override fun toString(): String = value
}
