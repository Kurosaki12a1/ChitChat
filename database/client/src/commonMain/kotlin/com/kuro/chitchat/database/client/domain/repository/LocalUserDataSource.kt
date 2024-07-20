package com.kuro.chitchat.database.client.domain.repository

import com.kuro.chitchat.database.client.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    suspend fun insertUser(user: UserEntity)

    suspend fun updateUser(user: UserEntity)

    fun getUserById(userId: String): Flow<UserEntity?>

    fun getUserByEmail(email: String): Flow<UserEntity?>

    fun getAllUser(): Flow<List<UserEntity>>
}