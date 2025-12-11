package dev.appoutlet.umami.util.logger

/**
 * A logging interface for recording messages with various severity levels.
 * This interface provides a set of functions to log messages at different levels of importance,
 * from verbose to error, and can be implemented to direct logs to different outputs like the console,
 * a file, or a remote service.
 */
interface UmamiLogger {
    /**
     * Logs a verbose message.
     * These messages are typically used for detailed diagnostic information.
     *
     * @param message The message to be logged.
     */
    fun verbose(message: String) {
        log(Severity.Verbose, message)
    }

    /**
     * Logs a debug message.
     * These messages are useful for debugging and tracing code execution.
     *
     * @param message The message to be logged.
     */
    fun debug(message: String) {
        log(Severity.Debug, message)
    }

    /**
     * Logs an informational message.
     * These messages are used to highlight the progress of the application at a high level.
     *
     * @param message The message to be logged.
     */
    fun info(message: String) {
        log(Severity.Info, message)
    }

    /**
     * Logs a warning message.
     * These messages indicate a potential problem or an unexpected event that does not prevent
     * the application from continuing to run.
     *
     * @param message The message to be logged.
     * @param throwable An optional [Throwable] to be logged with the message.
     */
    fun warn(message: String, throwable: Throwable? = null) {
        log(Severity.Warn, message, throwable)
    }

    /**
     * Logs an error message.
     * These messages indicate a serious issue that may prevent the application from functioning correctly.
     *
     * @param message The message to be logged.
     * @param throwable An optional [Throwable] to be logged with the message.
     */
    fun error(message: String, throwable: Throwable? = null) {
        log(Severity.Error, message, throwable)
    }

    /**
     * The core logging function that all other logging methods in this interface delegate to.
     * Implementations of this interface should provide the logic for handling the log message here.
     *
     * @param severity The severity level of the log message.
     * @param message The message to be logged.
     * @param throwable An optional [Throwable] to be logged with the message.
     */
    fun log(severity: Severity, message: String, throwable: Throwable? = null)

    /**
     * Represents the severity level of a log message.
     */
    enum class Severity {
        /** For detailed diagnostic information. */
        Verbose,

        /** For debugging and tracing code execution. */
        Debug,

        /** For informational messages about application progress. */
        Info,

        /** For potential problems or unexpected events. */
        Warn,

        /** For serious issues that may impact application functionality. */
        Error
    }
}
