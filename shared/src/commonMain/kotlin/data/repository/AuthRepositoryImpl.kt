package data.repository

import data.network.ChitChatApi
import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.UserUpdate
import domain.repository.AuthRepository
import domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent

class AuthRepositoryImpl(
    private val dataStoreOperations: DataStoreOperations,
    private val chitChatApi: ChitChatApi
) : AuthRepository, KoinComponent {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSignedInState(signedIn = signedIn)
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignedInState()
    }

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