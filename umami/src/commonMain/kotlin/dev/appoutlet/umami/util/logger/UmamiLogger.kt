package dev.appoutlet.umami.util.logger

/**
 * A flexible logging interface for recording messages with various severity levels.
 *
 * This interface provides a standardized API for logging in the Umami library. It supports
 * multiple log levels, from detailed verbose messages to critical errors. Implementations can
 * direct log output to different destinations, such as the console, files, or remote logging
 * services, allowing for adaptable logging strategies depending on the environment (e.g.,
 * development, production).
 */
interface UmamiLogger {
    /**
     * Logs a verbose message, typically for detailed diagnostic information.
     *
     * Use this for fine-grained debugging that is usually not needed in production but can be
     * valuable for tracing complex logic.
     *
     * @param message The message string to be logged.
     */
    fun verbose(message: String) {
        log(Severity.Verbose, message)
    }

    /**
     * Logs a debug message, useful for general-purpose debugging and tracing code execution.
     *
     * These messages help developers understand the flow of the application and the state of
     * variables at specific points in time.
     *
     * @param message The message string to be logged.
     */
    fun debug(message: String) {
        log(Severity.Debug, message)
    }

    /**
     * Logs an informational message, highlighting high-level application progress.
     *
     * These messages are intended to provide a general overview of what the application is
     * doing, such as starting a service or completing a significant task.
     *
     * @param message The message string to be logged.
     */
    fun info(message: String) {
        log(Severity.Info, message)
    }

    /**
     * Logs a warning message, indicating a potential problem or an unexpected event.
     *
     * Warnings signify that something unusual has occurred, but the application can continue
     * to run. They should be reviewed to prevent future errors.
     *
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to include for additional context, such as a
     * stack trace.
     */
    fun warn(message: String, throwable: Throwable? = null) {
        log(Severity.Warn, message, throwable)
    }

    /**
     * Logs an error message, indicating a serious issue that may disrupt functionality.
     *
     * Errors represent significant problems that need attention, as they can prevent parts of
     * the application from working correctly.
     *
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to include for detailed error information.
     */
    fun error(message: String, throwable: Throwable? = null) {
        log(Severity.Error, message, throwable)
    }

    /**
     * The core logging function that all other logging methods delegate to.
     *
     * Implementations of [UmamiLogger] must provide the logic for handling log messages in this
     * function. This is where the actual output of the log (e.g., printing to the console)
     * should occur.
     *
     * @param severity The [Severity] level of the log message.
     * @param message The message string to be logged.
     * @param throwable An optional [Throwable] to accompany the log message.
     */
    fun log(severity: Severity, message: String, throwable: Throwable? = null)

    /**
     * Represents the severity level of a log message, from least to most critical.
     */
    enum class Severity {
        /** For detailed, fine-grained diagnostic information. */
        Verbose,

        /** For messages useful in debugging and tracing code. */
        Debug,

        /** For informational messages about application progress. */
        Info,

        /** For potential problems or unexpected, non-critical events. */
        Warn,

        /** For serious issues that may impact application functionality. */
        Error
    }
}
