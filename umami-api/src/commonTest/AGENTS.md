# AGENTS.md: Instructions for AI Agents Contributing to `umami-api` Tests

This document provides guidance for AI agents writing tests for the `:umami-api` module. The tests in this module ensure that the API client correctly interacts with the Umami REST API endpoints.

## Core Testing Philosophy

The goal is to verify that the client-side API calls are constructed correctly and that responses are parsed as expected. This involves:
- Ensuring the correct HTTP method and endpoint URL are used.
- Verifying that request bodies are serialized correctly.
- Confirming that response bodies are deserialized into the correct domain objects.
- Checking that headers (like `Authorization`) are managed properly.

To achieve this without making real network calls, we use a `MockEngine` from Ktor, which is managed by a set of utility functions.

## Key Libraries and Patterns

- **`kotlinx.coroutines.test.runTest`**: All tests involving coroutines must be wrapped in `runTest`.
- **Kotest (`shouldBe`)**: Used for assertions due to its readable syntax.
- **Mocking**: We use Ktor's `MockEngine` to simulate the Umami API server. All mocking logic is abstracted away in helper functions.

## Core Utility Functions

All essential testing utilities are located in the `dev.appoutlet.umami.testing` package.

### `getUmamiInstance(...)`

This is the most important helper function for setting up a test. It creates an `Umami` instance configured with a Ktor `MockEngine`.

**Signature:**
```kotlin
fun TestScope.getUmamiInstance(
    vararg requests: Pair<String, MockRequestHandleScope.(HttpRequestData) -> HttpResponseData>,
): Umami
```

**Usage:**
You pass it a series of `Pair` objects. Each pair maps an API endpoint path (e.g., `/api/auth/login`) to a handler lambda. The handler lambda defines how the mock server should respond when it receives a request for that endpoint.

- **`enableEventQueue` is automatically set to `false`** to ensure API calls are made directly and not queued.
- The `coroutineScope` is automatically configured from the `runTest` scope.

### `respond<T>(content: T)`

This inline function is used inside the request handler lambda of `getUmamiInstance` to create a JSON response.

**Usage:**
It takes a data object `content`, serializes it to a JSON string, and wraps it in an `HttpResponseData` with `HttpStatusCode.OK` and a `Content-Type: application/json` header.

### `HttpRequestData.body<T>()`

This extension function is used inside the request handler to deserialize the incoming request's JSON body into an object of type `T`. This allows you to assert that the client is sending the correct data.

## Writing a Test: Step-by-Step Guide

Follow these steps to write a new API test.

**1. Set up the Test Function:**
   - Annotate the function with `@Test`.
   - Use `runTest` as the test body.

**2. Define Fixtures:**
   - Create variables for any request data and the expected response object. This makes the test clean and easy to read.

**3. Configure the Mock Response with `getUmamiInstance`:**
   - Call `getUmamiInstance` and provide the endpoint path you are testing.
   - In the handler lambda:
     - (Optional) If the request has a body, use `request.body<YourRequestType>()` to deserialize it and assert that its contents are correct.
     - Use `respond(yourFixtureResponse)` to return the mock response.

**4. Execute the API Call:**
   - Call the method on the `umami` instance that you are testing (e.g., `umami.login(...)`).

**5. Assert the Results:**
   - Use `shouldBe` to assert that the result of the API call matches your expected response fixture.
   - Assert any expected side effects, such as the `Authorization` header being added to `umami.headers`.

### Example: Testing the Login Endpoint

```kotlin
import dev.appoutlet.umami.api.auth.UmamiLogin
import dev.appoutlet.umami.domain.User
import dev.appoutlet.umami.testing.body
import dev.appoutlet.umami.testing.getUmamiInstance
import dev.appoutlet.umami.testing.respond
import io.kotest.matchers.shouldBe
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LoginTest {
    @Test
    fun `should login using username and password`() = runTest {
        // 1. Define Fixtures
        val fixtureUsername = "testuser"
        val fixturePassword = "testpassword"
        val fixtureToken = "some-jwt-token"
        val fixtureResponse = UmamiLogin.Response(
            token = fixtureToken,
            user = User(id = "123", username = fixtureUsername, ...)
        )

        // 2. Configure Mock Response
        val umami = getUmamiInstance(
            "/api/auth/login" to { request ->
                // 2a. (Optional) Assert the request body
                val loginRequest = request.body<UmamiLogin.Request>()
                loginRequest.username shouldBe fixtureUsername
                loginRequest.password shouldBe fixturePassword

                // 2b. Return the mock response
                respond(fixtureResponse)
            }
        )

        // 3. Execute API Call
        val response = umami.login(
            username = fixtureUsername,
            password = fixturePassword
        )

        // 4. Assert Results
        response shouldBe fixtureResponse
        umami.headers[HttpHeaders.Authorization] shouldBe "Bearer $fixtureToken"
    }
}
```
