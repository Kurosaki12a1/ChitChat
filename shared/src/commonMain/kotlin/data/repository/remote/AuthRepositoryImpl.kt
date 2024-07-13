package data.repository.remote

import data.model.dto.ApiResponse
import domain.model.ApiRequest
import domain.model.UserUpdate
import domain.repository.remote.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import utils.DELETE_USER
import utils.GET_USER
import utils.SIGN_IN
import utils.TOKEN_VERIFICATION
import utils.UPDATE_USER


/**
 * Implementation of AuthRepository for handling authentication-related operations.
 * If you want use like Retrofit Android, can replace to KtorFit
 * https://foso.github.io/Ktorfit/
 * @property client The HttpClient used for making HTTP requests.
 */
class AuthRepositoryImpl(
    private val client: HttpClient
) : AuthRepository {


    /**
     * Verifies the token on the backend.
     *
     * @param request The API request containing the token to be verified.
     * @return The API response indicating the result of the token verification.
     */
    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return try {
            val response = client.post {
                url {
                    path(TOKEN_VERIFICATION)
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(success = false, error = e)
        }
    }

    /**
     * Retrieves user information.
     *
     * @return The API response containing the user information.
     */
    override suspend fun getUserInfo(): ApiResponse {
        return try {
            val response = client.get {
                url { path(GET_USER) }
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    /**
     * Signs in the user.
     *
     * @return The API response indicating the result of the sign-in attempt.
     */
    override suspend fun signIn(): ApiResponse {
        return try {
            val response = client.get {
                url { path(SIGN_IN) }
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    /**
     * Updates user information.
     *
     * @param request The request containing the user information to be updated.
     * @return The API response indicating the result of the update attempt.
     */

    override suspend fun updateUser(request: UserUpdate): ApiResponse {
        return try {
            val response = client.put {
                url {
                    path(UPDATE_USER)
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    /**
     * Deletes the user.
     *
     * @return The API response indicating the result of the delete attempt.
     */
    override suspend fun deleteUser(): ApiResponse {
        return try {
            val response: HttpResponse = client.delete {
                url(DELETE_USER)
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    /**
     * Clears the user session.
     *
     * @return The API response indicating the result of the session clearing attempt.
     */
    override suspend fun clearSession(): ApiResponse {
        return try {
            val response: HttpResponse = client.get {
                url("/sign_out")
            }
            response.body<ApiResponse>()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}