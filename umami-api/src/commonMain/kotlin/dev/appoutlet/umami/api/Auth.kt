package dev.appoutlet.umami.api

import dev.appoutlet.umami.domain.Session
import dev.appoutlet.umami.domain.User
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val UmamiApiKeyHeader = "x-umami-api-key"

/**
 * Provides authentication functionalities for interacting with the Umami API.
 * This class handles user login, logout, and token verification.
 *
 * @param api The [UmamiApi] instance used for making HTTP requests.
 */
class Auth(private val api: UmamiApi) {

    /**
     * Logs in a user with the provided username and password.
     * This function constructs a [Login.Request] and then calls the overloaded login function.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return A [Session] containing the JWT token and user details.
     */
    suspend fun login(
        username: String,
        password: String,
    ): Session {
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
     * @return A [Session] containing the JWT token and user details.
     */
    suspend fun login(request: Login.Request): Session {
        val response: Session = api.httpClient.post(Api.Auth.Login()) {
            setBody(request)
        }.body()

        api.headers.put(HttpHeaders.Authorization, "Bearer ${response.token}")

        return response
    }

    /**
     * Logs in a user using an API key.
     * This method is intended for use with Umami Cloud or instances where API key authentication is enabled.
     *
     * @param apiKey The API key to use for authentication.
     * @return A [Session] object containing the user's session details.
     */
    suspend fun login(apiKey: String): Session {
        api.headers.put(UmamiApiKeyHeader, apiKey)

        return try {
            api.me().getSession()
        } catch (exception: Throwable) {
            api.headers.remove(UmamiApiKeyHeader)
            throw exception
        }
    }

    /**
     * Logs out the current user.
     *
     * If an authorization token is present, it sends a logout request to the Umami API
     * and removes both the authorization and API key headers.
     *
     * If no authorization token is present (e.g., when using an API key for Umami Cloud),
     * it only removes the API key header without calling the logout endpoint.
     */
    suspend fun logout() {
        if (api.headers.get(HttpHeaders.Authorization) != null) {
            api.httpClient.post(Api.Auth.Logout())
            api.headers.remove(HttpHeaders.Authorization)
        }

        api.headers.remove(UmamiApiKeyHeader)
    }

    /**
     * Verifies the current authentication token and retrieves the details of the logged-in user.
     *
     * @return The [User] object of the authenticated user.
     */
    suspend fun verify(): User {
        return api.httpClient
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
    }
}

/**
 * Extension function to provide easy access to [Auth] functionalities from an [UmamiApi] instance.
 *
 * @return An instance of [Auth].
 */
fun UmamiApi.auth(): Auth = Auth(this)
