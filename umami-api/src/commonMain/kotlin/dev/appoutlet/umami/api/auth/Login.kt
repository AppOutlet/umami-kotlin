package dev.appoutlet.umami.api.auth

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.api.Api
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.util.headers
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Logs in to the Umami API.
 * After a successful login, the authentication token is stored and used for subsequent requests.
 *
 * @param request The login request containing the username and password.
 * @return The login response containing the token and user information.
 */
suspend fun Umami.login(request: Login.Request): Login.Response {
    val response: Login.Response = httpClient.post(Api.Auth.Login()) {
        setBody(request)
    }.body()

    headers.put(HttpHeaders.Authorization, "Bearer ${response.token}")

    return response
}

/**
 * Logs in to the Umami API.
 * After a successful login, the authentication token is stored and used for subsequent requests.
 * This is a convenience function that creates a [Login.Request] object.
 *
 * @param username The username for authentication.
 * @param password The password for authentication.
 * @return The login response containing the token and user information.
 */
suspend fun Umami.login(
    username: String,
    password: String,
): Login.Response {
    val request = Login.Request(
        username = username,
        password = password,
    )

    return login(request)
}

/**
 * Namespace for the login API request and response data classes.
 */
interface Login {
    /**
     * Represents the request body for the login API call.
     * @property username The username of the user.
     * @property password The password of the user.
     */
    @Serializable
    data class Request(
        @SerialName("username") val username: String,
        @SerialName("password") val password: String,
    )

    /**
     * Represents the response from a successful login API call.
     * @property token The JWT token for authenticating subsequent requests.
     * @property user The details of the logged-in user.
     */
    @Serializable
    data class Response(
        @SerialName("token") val token: String,
        @SerialName("user") val user: User
    )
}
