package data.repository

import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate
import domain.repository.ApiRepository
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
import utils.TOKEN_VERIFICATION
import utils.UPDATE_USER

class ApiRepositoryImpl(
    private val client: HttpClient
) : ApiRepository {

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