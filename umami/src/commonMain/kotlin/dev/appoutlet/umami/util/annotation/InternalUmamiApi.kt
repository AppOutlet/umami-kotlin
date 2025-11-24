package dev.appoutlet.umami.util.annotation

/**
 * Marks properties that are part of the internal API of the Umami library.
 *
 * These APIs are not intended for public use and are not covered by semantic versioning guarantees.
 * They can be changed or removed at any time without notice.
 *
 * Accessing these properties requires an explicit opt-in with `@OptIn(InternalUmamiApi::class)`
 * or by annotating the calling code with `@InternalUmamiApi`. This will produce a compile-time error
 * if not respected.
 */
@RequiresOptIn(
    level = RequiresOptIn.Level.ERROR,
    message = "This API is internal to Umami and should not be used directly from outside the library."
)
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CONSTRUCTOR)
annotation class InternalUmamiApi
