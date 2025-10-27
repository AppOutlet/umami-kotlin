@file:Suppress("ForbiddenImport")

package dev.appoutlet.umami.util

import co.touchlab.kermit.Logger

internal class DefaultUmamiLogger(
    private val logger: Logger = Logger.withTag("Umami")
) : UmamiLogger {
    override fun verbose(message: String) {
        logger.v(messageString = message)
    }

    override fun debug(message: String) {
        logger.d(messageString = message)
    }

    override fun info(message: String) {
        logger.i(messageString = message)
    }

    override fun warn(message: String, throwable: Throwable?) {
        logger.w(messageString = message, throwable = throwable)
    }

    override fun error(message: String, throwable: Throwable?) {
        logger.e(messageString = message, throwable = throwable)
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
