package com.kuro.chitchat.database.client.data.repository

import com.kuro.chitchat.database.client.data.dao.UserDao
import com.kuro.chitchat.database.client.domain.repository.LocalUserDataSource
import com.kuro.chitchat.database.client.entity.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalUserDataSourceImpl(
    private val userDao: UserDao
) : LocalUserDataSource {
    override suspend fun insertUser(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    override suspend fun updateUser(user: UserEntity) {
        withContext(Dispatchers.IO) {
            userDao.updateUser(user)
        }
    }

    override fun getUserById(userId: String): Flow<UserEntity?> {
        return userDao.getUserById(userId)
    }

    override fun getUserByEmail(email: String): Flow<UserEntity?> {
        return userDao.getUserByEmail(email)
    }

    override fun getAllUser(): Flow<List<UserEntity>> {
        return userDao.getAllUser()
    }
}