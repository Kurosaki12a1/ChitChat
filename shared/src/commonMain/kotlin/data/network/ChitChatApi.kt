package data.network

import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.path
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent

class ChitChatApi(
    private val client: HttpClient
) : KoinComponent {

    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return try {
            val response = client.post {
                url {
                    path("/token_verification")
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
            val responseBody: String = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            e.printStackTrace()
            ApiResponse(success = false, error = e)
        }
    }

    suspend fun getUserInfo(): ApiResponse {
        return try {
            val response = client.get {
                url { path("/get_user") }
            }
            val responseBody: String = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    suspend fun updateUser(request: UserUpdate): ApiResponse {
        return try {
            val response = client.put {
                url {
                    path("/update_user")
                    contentType(ContentType.Application.Json)
                    setBody(request)
                }
            }
            val responseBody: String = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    suspend fun deleteUser(): ApiResponse {
        return try {
            val response: HttpResponse = client.delete {
                url("/delete_user")
            }
            val responseBody = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    suspend fun clearSession(): ApiResponse {
        return try {
            val response: HttpResponse = client.get {
                url("/sign_out")
            }
            val responseBody = response.bodyAsText()
            Json.decodeFromString(responseBody)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}