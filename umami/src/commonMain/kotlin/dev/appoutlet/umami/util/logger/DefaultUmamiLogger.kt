@file:Suppress("ForbiddenImport")

package dev.appoutlet.umami.util.logger

import co.touchlab.kermit.Logger

/**
 * A default, internal implementation of [UmamiLogger] that leverages the Kermit logging library.
 *
 * This class acts as an adapter, translating calls from the [UmamiLogger] interface into the
 * corresponding methods of a [co.touchlab.kermit.Logger] instance. It provides a ready-to-use
 * logging solution for the Umami library, abstracting the underlying logging framework.
 *
 * @property logger The underlying [Logger] instance from the Kermit library, configured with the
 * tag "Umami" by default.
 */
internal class DefaultUmamiLogger(
    private val logger: Logger = Logger.withTag("Umami")
) : UmamiLogger {
    /**
     * Logs a verbose message by delegating to the underlying Kermit logger's `v` method.
     *
     * @param message The message string to be logged.
     */
    override fun verbose(message: String) {
        logger.v(messageString = message)
    }

    /**
     * Logs a debug message by delegating to the underlying Kermit logger's `d` method.
     *
     * @param message The message string to be logged.
     */
    override fun debug(message: String) {
        logger.d(messageString = message)
    }

    /**
     * Logs an informational message by delegating to the underlying Kermit logger's `i` method.
     *
     * @param message The message string to be logged.
     */
    override fun info(message: String) {
        logger.i(messageString = message)
    }

    /**
     * Logs a warning message by delegating to the underlying Kermit logger's `w` method.
     *
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to include for additional context.
     */
    override fun warn(message: String, throwable: Throwable?) {
        logger.w(messageString = message, throwable = throwable)
    }

    /**
     * Logs an error message by delegating to the underlying Kermit logger's `e` method.
     *
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to include for detailed error information.
     */
    override fun error(message: String, throwable: Throwable?) {
        logger.e(messageString = message, throwable = throwable)
    }

    /**
     * The core logging function that maps [UmamiLogger.Severity] to the appropriate method in
     * the underlying Kermit logger.
     *
     * This implementation ensures that calls to the generic `log` method are dispatched to the
     * correct severity-specific function (e.g., `verbose`, `debug`).
     *
     * @param severity The [UmamiLogger.Severity] level of the log message.
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to accompany the log message.
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
