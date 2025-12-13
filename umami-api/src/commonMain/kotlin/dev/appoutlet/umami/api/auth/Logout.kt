package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.Api
import dev.appoutlet.umami.util.headers
import io.ktor.client.plugins.resources.post
import io.ktor.http.HttpHeaders

/**
 * Logs out the current user.
 *
 * This function sends a request to the logout endpoint to invalidate the current session on the server.
 * It then removes the locally stored Authorization header, effectively logging the user out on the client side as well.
 *
 * After calling this function, subsequent requests will be unauthenticated until a new login is performed.
 */
suspend fun Umami.logout() {
    httpClient.post(Api.Auth.Logout())
    headers.remove(HttpHeaders.Authorization)
}
