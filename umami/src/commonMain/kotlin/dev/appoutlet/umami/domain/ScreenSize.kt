package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

private const val MAX_SCREEN_SIZE_LENGTH = 11

/**
 * Represents a screen size in the format "width x height" (e.g., "1920x1080").
 *
 * @property value The string representation of the screen size.
 * @throws IllegalArgumentException if the screen size string is blank or exceeds [MAX_SCREEN_SIZE_LENGTH] characters.
 */
@JvmInline
value class ScreenSize(val value: String) {

    init {
        require(value.isNotBlank()) { "Screen size cannot be blank" }
        require(value.length <= MAX_SCREEN_SIZE_LENGTH) {
            "Screen size should not exceed $MAX_SCREEN_SIZE_LENGTH characters"
        }
    }

    /**
     * Creates a ScreenSize object from width and height.
     *
     * @param width The screen width in pixels.
     * @param height The screen height in pixels.
     */
    constructor(width: Int, height: Int) : this("$width x $height")

    override fun toString(): String = value
}
