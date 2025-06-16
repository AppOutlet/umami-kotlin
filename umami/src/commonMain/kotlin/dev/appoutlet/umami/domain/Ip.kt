package dev.appoutlet.umami.domain

import kotlin.jvm.JvmInline
@Suppress("MaxLineLength")
private val ipv4Regex = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9]).){3}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$".toRegex()

@Suppress("MaxLineLength")
private val ipv6Regex = "^(([0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9])?[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9])?[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9])?[0-9]).){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9])?[0-9]))$".toRegex()

/**
 * Represents an IP address (IPv4 or IPv6).
 *
 * @property value The string representation of the IP address.
 * @throws IllegalArgumentException if the provided string is not a valid IPv4 or IPv6 address.
 */
@JvmInline
value class Ip(val value: String) {
    init {
        require(ipv4Regex.matches(value) || ipv6Regex.matches(value)) {
            "Invalid IP address format: $value"
        }
    }
}
