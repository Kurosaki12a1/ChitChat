package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.User
import com.kuro.chitchat.domain.repository.UserDataSource
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.or
import org.litote.kmongo.regex
import org.litote.kmongo.setValue

class UserDataSourceImpl(
    database: CoroutineDatabase
) : UserDataSource {

    private val users = database.getCollection<User>()


    override suspend fun getUserInfo(userId: String): User? {
        return users.findOne(filter = User::userId eq userId)
    }

    override suspend fun searchUsers(name: String): List<User> {
        val regexQuery = ".*$name.*".toRegex(RegexOption.IGNORE_CASE)
        return users.find(or(User::name regex regexQuery, User::emailAddress regex regexQuery))
            .toList()
    }

    override suspend fun saveUserInfo(user: User): Boolean {
        val existingUserDto = users.findOne(filter = User::userId eq user.userId)
        return if (existingUserDto == null) {
            users.insertOne(document = user).wasAcknowledged()
        } else {
            true
        }
    }

    override suspend fun deleteUser(userId: String): Boolean {
        return users.deleteOne(filter = User::userId eq userId).wasAcknowledged()
    }

    override suspend fun updateUserName(
        userId: String,
        firstName: String,
        lastName: String
    ): Boolean {
        return users.updateOne(
            filter = User::userId eq userId,
            update = setValue(
                property = User::name,
                value = "$firstName $lastName"
            )
        ).wasAcknowledged()
    }
}