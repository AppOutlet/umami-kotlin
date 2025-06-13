package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline

@JvmInline
value class ScreenSize(val value: String) {

    init {
        require(value.isNotBlank()) { "Screen size cannot be blank" }
        require(value.length <= 11) { "Screen size should not exceed 11 characters" }
    }

    constructor(width: Int, height: Int) : this("$width x $height")

    override fun toString(): String = value

    companion object {
        val `1920x1080` = ScreenSize("1920x1080")
        val `1280x720` = ScreenSize("1280x720")
        val `1366x768` = ScreenSize("1366x768")
    }
}
