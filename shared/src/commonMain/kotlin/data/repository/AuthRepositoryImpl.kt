package data.repository

import data.network.ChitChatApi
import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate
import domain.repository.AuthRepository
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val chitChatApi: ChitChatApi
) : AuthRepository, KoinComponent {
    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return chitChatApi.verifyTokenOnBackend(request)
    }

    override suspend fun getUserInfo(): ApiResponse {
        return chitChatApi.getUserInfo()
    }

    override suspend fun updateUser(request: UserUpdate): ApiResponse {
        return chitChatApi.updateUser(request)
    }

    override suspend fun deleteUser(): ApiResponse {
        return chitChatApi.deleteUser()
    }

    override suspend fun clearSession(): ApiResponse {
        return chitChatApi.clearSession()
    }
}