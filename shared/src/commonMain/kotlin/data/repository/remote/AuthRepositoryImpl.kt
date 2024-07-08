package data.repository.remote

import domain.model.ApiRequest
import data.model.dto.ApiResponse
import domain.model.UserUpdate
import domain.repository.remote.ApiRepository
import domain.repository.remote.AuthRepository
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val repository: ApiRepository
) : AuthRepository, KoinComponent {
    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return repository.verifyTokenOnBackend(request)
    }

    override suspend fun getUserInfo(): ApiResponse {
        return repository.getUserInfo()
    }

    override suspend fun updateUser(request: UserUpdate): ApiResponse {
        return repository.updateUser(request)
    }

    override suspend fun deleteUser(): ApiResponse {
        return repository.deleteUser()
    }

    override suspend fun clearSession(): ApiResponse {
        return repository.clearSession()
    }
}