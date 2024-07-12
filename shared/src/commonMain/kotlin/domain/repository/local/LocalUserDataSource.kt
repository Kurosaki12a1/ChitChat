package domain.repository.local

import domain.model.UserModel
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    suspend fun insertUser(user: UserModel)

    suspend fun updateUser(user: UserModel)

    fun getUserById(userId: String): Flow<UserModel?>

    fun getUserByEmail(email: String): Flow<UserModel?>

    fun getAllUser(): Flow<List<UserModel>>
}