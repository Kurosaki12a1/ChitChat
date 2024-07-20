package domain.usecase.auth

import com.kuro.chitchat.database.client.domain.repository.LocalUserDataSource
import data.model.toModel
import domain.models.UserModel
import domain.repository.DataStoreOperations
import kotlinx.coroutines.flow.firstOrNull

class GetUserInfoUseCase(
    private val localUserDataSource: LocalUserDataSource,
    private val dataStoreOperations: DataStoreOperations,
) {

    suspend operator fun invoke(): UserModel? {
        val userId = dataStoreOperations.getCurrentSignedIn().firstOrNull()
        return if (!userId.isNullOrEmpty()) {
            localUserDataSource.getUserById(userId).firstOrNull()?.toModel()
        } else {
            null
        }
    }
}