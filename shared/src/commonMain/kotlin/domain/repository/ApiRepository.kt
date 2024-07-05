package domain.repository

import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate

interface ApiRepository {
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse

    suspend fun getUserInfo(): ApiResponse

    suspend fun updateUser(request: UserUpdate): ApiResponse

    suspend fun deleteUser(): ApiResponse

    suspend fun clearSession(): ApiResponse
}