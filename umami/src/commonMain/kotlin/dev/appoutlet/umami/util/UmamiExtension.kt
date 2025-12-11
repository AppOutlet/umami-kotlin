package dev.appoutlet.umami.util

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.util.annotation.InternalUmamiApi
import dev.appoutlet.umami.util.headers.SuspendMutableMap
import dev.appoutlet.umami.util.logger.UmamiLogger

/**
 * Provides convenient access to the [UmamiLogger] instance from the [Umami] object.
 * This extension property allows for easy logging throughout the library by accessing `umami.logger`.
 *
 * @receiver The [Umami] instance.
 * @return The [UmamiLogger] configured in the [Umami.options].
 */
@InternalUmamiApi
val Umami.logger: UmamiLogger
    get() = this.options.logger

/**
 * Provides convenient access to the [SuspendMutableMap] for headers from the [Umami] object.
 * This allows for easy manipulation of headers that will be sent with requests.
 *
 * @receiver The [Umami] instance.
 * @return The [SuspendMutableMap] for headers configured in the [Umami.options].
 */
@InternalUmamiApi
val Umami.headers: SuspendMutableMap<String, String?>
    get() = this.options.headers
