package data.repository.local

import data.data_source.local.dao.UserDao
import data.model.toEntity
import data.model.toModel
import domain.model.UserModel
import domain.repository.local.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: UserModel) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user.toEntity())
        }
    }

    override suspend fun updateUser(user: UserModel) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user.toEntity())
        }
    }

    override fun getUserById(userId: String): Flow<UserModel?> {
        return userDao.getUserById(userId).map { it?.toModel() }
    }

    override fun getUserByEmail(email: String): Flow<UserModel?> {
        return userDao.getUserByEmail(email).map { it?.toModel() }
    }

    override fun getAllUser(): Flow<List<UserModel>> {
        return userDao.getAllUser().map { list -> list.map { item -> item.toModel() } }
    }
}