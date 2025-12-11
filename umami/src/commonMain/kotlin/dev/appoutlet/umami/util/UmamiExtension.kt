package dev.appoutlet.umami.util

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.util.logger.UmamiLogger

val Umami.logger: UmamiLogger
    get() = this.options.logger
