@file:Suppress("ForbiddenImport")

package dev.appoutlet.umami.util.logger

import co.touchlab.kermit.Logger

/**
 * A default implementation of [UmamiLogger] that uses the Kermit logging library.
 * This class wraps a [Logger] instance and delegates the logging calls to it.
 *
 * @property logger The underlying [Logger] instance used for logging. Defaults to a logger with the tag "Umami".
 */
internal class DefaultUmamiLogger(
    private val logger: Logger = Logger.withTag("Umami")
) : UmamiLogger {
    /**
     * Logs a verbose message using the underlying Kermit logger.
     *
     * @param message The message to be logged.
     */
    override fun verbose(message: String) {
        logger.v(messageString = message)
    }

    /**
     * Logs a debug message using the underlying Kermit logger.
     *
     * @param message The message to be logged.
     */
    override fun debug(message: String) {
        logger.d(messageString = message)
    }

    /**
     * Logs an info message using the underlying Kermit logger.
     *
     * @param message The message to be logged.
     */
    override fun info(message: String) {
        logger.i(messageString = message)
    }

    /**
     * Logs a warning message with an optional throwable using the underlying Kermit logger.
     *
     * @param message The message to be logged.
     * @param throwable The optional throwable to be logged.
     */
    override fun warn(message: String, throwable: Throwable?) {
        logger.w(messageString = message, throwable = throwable)
    }

    /**
     * Logs an error message with an optional throwable using the underlying Kermit logger.
     *
     * @param message The message to be logged.
     * @param throwable The optional throwable to be logged.
     */
    override fun error(message: String, throwable: Throwable?) {
        logger.e(messageString = message, throwable = throwable)
    }

    /**
     * Logs a message with the specified severity.
     * This method delegates to the specific logging methods based on the severity level.
     *
     * @param severity The severity of the log message.
     * @param message The message to be logged.
     * @param throwable The optional throwable to be logged.
     */
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
