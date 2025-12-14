package dev.appoutlet.umami.api

import dev.appoutlet.umami.Umami
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.util.headers
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Provides authentication functionalities for interacting with the Umami API.
 * This class handles user login, logout, and token verification.
 *
 * @param umami The [Umami] instance used for making HTTP requests.
 */
class Auth(private val umami: Umami) {

    /**
     * Logs in a user with the provided username and password.
     * This function constructs a [Login.Request] and then calls the overloaded login function.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A [Login.Response] containing the JWT token and user details.
     */
    suspend fun login(
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
     * Logs in a user with the provided [Login.Request] object.
     * On successful login, the received JWT token is stored for subsequent authenticated requests.
     *
     * @param request The [Login.Request] object containing username and password.
     * @return A [Login.Response] containing the JWT token and user details.
     */
    suspend fun login(request: Login.Request): Login.Response {
        val response: Login.Response = umami.httpClient.post(Api.Auth.Login()) {
            setBody(request)
        }.body()

        umami.headers.put(HttpHeaders.Authorization, "Bearer ${response.token}")

        return response
    }

    /**
     * Logs out the current user by sending a logout request to the Umami API
     * and removing the authorization header.
     */
    suspend fun logout() {
        umami.httpClient.post(Api.Auth.Logout())
        umami.headers.remove(HttpHeaders.Authorization)
    }

    /**
     * Verifies the current authentication token and retrieves the details of the logged-in user.
     *
     * @return The [User] object of the authenticated user.
     */
    suspend fun verify(): User {
        return umami.httpClient
            .post(Api.Auth.Verify())
            .body()
    }

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
}

/**
 * Extension function to provide easy access to [Auth] functionalities from an [Umami] instance.
 *
 * @return An instance of [Auth].
 */
fun Umami.auth(): Auth = Auth(this)
