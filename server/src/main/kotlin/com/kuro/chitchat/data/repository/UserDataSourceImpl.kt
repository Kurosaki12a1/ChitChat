package com.kuro.chitchat.data.repository

import com.kuro.chitchat.ServerDatabaseQueries
import com.kuro.chitchat.domain.model.User
import com.kuro.chitchat.domain.repository.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserDataSourceImpl(
    private val serverDatabaseQueries: ServerDatabaseQueries
) : UserDataSource {
    override suspend fun getUserInfo(userId: String): User? {
        return withContext(Dispatchers.IO) {
            serverDatabaseQueries.getUserInfo(userId).executeAsOneOrNull()?.let {
                User(
                    id = it.id,
                    name = it.name,
                    emailAddress = it.emailAddress,
                    profilePhoto = it.profilePhoto ?: ""
                )
            }
        }
    }

    override suspend fun saveUserInfo(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val existingUser = serverDatabaseQueries.getUserInfo(user.id).executeAsOneOrNull()
                if (existingUser == null) {
                    serverDatabaseQueries.insertUser(
                        id = user.id,
                        name = user.name,
                        emailAddress = user.emailAddress,
                        profilePhoto = user.profilePhoto
                    )
                }
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return withContext(Dispatchers.IO) {
            serverDatabaseQueries.deleteUser(userId)
            true
        }
    }

    override suspend fun updateUserName(
        userId: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                serverDatabaseQueries.updateUserName(
                    id = userId,
                    name = "$firstName $lastName",
                )
                true
            } catch (e: Exception) {
                e.printStackTrace()
                false
            }
        }
    }
}
