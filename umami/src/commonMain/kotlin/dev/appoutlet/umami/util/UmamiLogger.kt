package dev.appoutlet.umami.util

interface UmamiLogger {
    fun verbose(message: String) {
        log(Severity.Verbose, message)
    }

    fun debug(message: String) {
        log(Severity.Debug, message)
    }

    fun info(message: String) {
        log(Severity.Info, message)
    }

    fun warn(message: String, throwable: Throwable? = null) {
        log(Severity.Warn, message, throwable)
    }

    fun error(message: String, throwable: Throwable? = null) {
        log(Severity.Error, message, throwable)
    }

    fun log(severity: Severity, message: String, throwable: Throwable? = null)

    enum class Severity {
        Verbose,
        Debug,
        Info,
        Warn,
        Error
    }
}
