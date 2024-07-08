package domain.repository.remote

import domain.model.ApiRequest
import data.model.dto.ApiResponse
import domain.model.UserUpdate

interface ApiRepository {
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse

    suspend fun getUserInfo(): ApiResponse

    suspend fun signIn() : ApiResponse

    suspend fun updateUser(request: UserUpdate): ApiResponse

    suspend fun deleteUser(): ApiResponse

    suspend fun clearSession(): ApiResponse
}