package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.Api
import dev.appoutlet.umami.domain.User
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post

/**
 * Verifies the current authentication state of the [Umami] client.
 *
 * This function sends a POST request to the authentication verification endpoint using the configured [httpClient].
 * If the authentication is valid, it returns the authenticated [User] object.
 *
 * @receiver [Umami] The Umami client instance used to perform the verification.
 * @return [User] The authenticated user information if verification is successful.
 * @throws io.ktor.client.plugins.ClientRequestException if the verification fails or the server returns an error.
 */
suspend fun Umami.verify(): User {
    return httpClient.post(Api.Auth.Verify()).body()
}
