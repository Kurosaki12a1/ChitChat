package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.data.model.entity.User

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
}