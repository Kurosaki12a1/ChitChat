package com.kuro.chitchat.database.server.domain.repository

import com.kuro.chitchat.database.server.entity.User

interface UserDataSource {
    suspend fun getUserInfo(userId: String): User?
    suspend fun searchUsers(name: String): List<User>
    suspend fun saveUserInfo(user: User): Boolean
    suspend fun deleteUser(userId: String): Boolean
    suspend fun updateUserName(
        userId: String,
        firstName: String,
        lastName: String
    ): Boolean

    suspend fun updateUserLastActive(userId: String): User?
}