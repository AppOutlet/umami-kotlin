package dev.appoutlet.umami.api

/**
 * Represents the type of the event.
 *
 * @property value The string representation of the event type.
 */
enum class EventType(val value: String) {
    /** Represents a standard event. */
    Event("event"),

    /** Represents an identify event. */
    Identify("identify"),
}
