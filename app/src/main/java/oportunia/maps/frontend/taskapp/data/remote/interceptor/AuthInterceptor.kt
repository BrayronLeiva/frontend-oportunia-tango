package oportunia.maps.frontend.taskapp.data.remote.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import oportunia.maps.frontend.taskapp.data.local.AuthPreferences
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor that adds authentication token to API requests.
 *
 * This interceptor automatically adds the authentication token to the
 * Authorization header for requests that require authentication.
 */
@Singleton
class AuthInterceptor @Inject constructor(
    private val authPreferences: AuthPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Skip authentication for login and registration endpoints
        if (originalRequest.url.encodedPath.contains("v1/users/login") ||
            originalRequest.url.encodedPath.contains("v1/users/register")
        ) {
            return chain.proceed(originalRequest)
        }

        // Get the token from AuthPreferences
        val token = runBlocking {
            authPreferences.getAuthToken()
        }

        // If no token is available, proceed with the original request
        if (token.isNullOrBlank()) {
            return chain.proceed(originalRequest)
        }

        // Add the token to the request header
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authenticatedRequest)
    }
}