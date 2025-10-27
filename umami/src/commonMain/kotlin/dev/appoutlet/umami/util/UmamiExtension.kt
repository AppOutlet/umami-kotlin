package dev.appoutlet.umami.util

import dev.appoutlet.umami.Umami

val Umami.logger: UmamiLogger
    get() = this.options.logger
