package domain.usecase.auth

import domain.model.UserModel
import domain.repository.DataStoreOperations
import domain.repository.local.LocalUserDataSource
import kotlinx.coroutines.flow.firstOrNull

class GetUserInfoUseCase(
    private val localUserDataSource: LocalUserDataSource,
    private val dataStoreOperations: DataStoreOperations,
) {

    suspend operator fun invoke(): UserModel? {
        val userId = dataStoreOperations.getCurrentSignedIn().firstOrNull()
        return if (!userId.isNullOrEmpty()) {
            localUserDataSource.getUserById(userId).firstOrNull()
        } else {
            null
        }
    }
}