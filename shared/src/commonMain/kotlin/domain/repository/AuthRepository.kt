package domain.repository

import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun saveSignedInState(signedIn: Boolean)

    fun readSignedInState(): Flow<Boolean>

    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse

    suspend fun getUserInfo(): ApiResponse

    suspend fun updateUser(request: UserUpdate): ApiResponse

    suspend fun deleteUser(): ApiResponse

    suspend fun clearSession(): ApiResponse
}