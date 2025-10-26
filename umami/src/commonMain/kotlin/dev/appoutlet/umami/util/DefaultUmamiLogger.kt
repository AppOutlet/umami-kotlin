@file:Suppress("ForbiddenImport")

package dev.appoutlet.umami.util

import co.touchlab.kermit.Logger

val logger = Logger.withTag("Umami")

internal class DefaultUmamiLogger : UmamiLogger {
    override fun verbose(message: String) {
        logger.v { message }
    }

    override fun debug(message: String) {
        logger.d { message }
    }

    override fun info(message: String) {
        logger.i { message }
    }

    override fun warn(message: String, throwable: Throwable?) {
        logger.w(throwable) { message }
    }

    override fun error(message: String, throwable: Throwable?) {
        logger.e(throwable) { message }
    }

    override fun log(severity: UmamiLogger.Severity, message: String, throwable: Throwable?) {
        when (severity) {
            UmamiLogger.Severity.Verbose -> verbose(message)
            UmamiLogger.Severity.Debug -> debug(message)
            UmamiLogger.Severity.Info -> info(message)
            UmamiLogger.Severity.Warn -> warn(message, throwable)
            UmamiLogger.Severity.Error -> error(message, throwable)
        }
    }
}
