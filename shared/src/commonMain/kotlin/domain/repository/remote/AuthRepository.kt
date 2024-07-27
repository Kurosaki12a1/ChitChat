package domain.repository.remote

import domain.models.ApiRequest
import data.model.dto.ApiResponse
import domain.models.UserUpdate

interface AuthRepository {
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse

    suspend fun signIn() : ApiResponse

    suspend fun getUserInfo(userId : String?): ApiResponse

    suspend fun updateUser(request: UserUpdate): ApiResponse

    suspend fun deleteUser(): ApiResponse

    suspend fun clearSession(): ApiResponse
}