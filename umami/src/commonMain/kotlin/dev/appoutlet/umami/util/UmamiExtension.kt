package dev.appoutlet.umami.util

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.UmamiOptions
import dev.appoutlet.umami.util.annotation.InternalUmamiApi
import dev.appoutlet.umami.util.headers.SuspendMutableMap
import dev.appoutlet.umami.util.logger.UmamiLogger

/**
 * Provides convenient, internal access to the configured [UmamiLogger] instance.
 *
 * This extension property simplifies logging within the library by exposing the logger
 * directly on the [Umami] object. It retrieves the logger from the centrally managed
 * [UmamiOptions]. Marked as [InternalUmamiApi] to indicate it is intended for use
 * only within the Umami library.
 *
 * @receiver The [Umami] instance from which to get the logger.
 * @return The [UmamiLogger] instance configured for this [Umami] client.
 */
@InternalUmamiApi
val Umami.logger: UmamiLogger
    get() = this.options.logger

/**
 * Provides convenient, internal access to the suspendable map of custom HTTP headers.
 *
 * This extension property allows for easy, asynchronous manipulation of headers that will be
 * included in all outgoing requests made by the [Umami] client. It retrieves the header map
 * from the centrally managed [UmamiOptions]. Marked as [InternalUmamiApi] to indicate it is
 * intended for use only within the Umami library.
 *
 * @receiver The [Umami] instance from which to get the header map.
 * @return The [SuspendMutableMap] instance used for managing custom headers.
 */
@InternalUmamiApi
val Umami.headers: SuspendMutableMap<String, String>
    get() = this.options.headers
