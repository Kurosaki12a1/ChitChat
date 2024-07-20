package com.kuro.chitchat.database.client.di

import com.kuro.chitchat.database.client.data.AppDatabase
import com.kuro.chitchat.database.client.data.dao.ChatRoomDao
import com.kuro.chitchat.database.client.data.dao.MessageDao
import com.kuro.chitchat.database.client.data.dao.UserDao
import com.kuro.chitchat.database.client.data.repository.LocalChatRoomDataSourceImpl
import com.kuro.chitchat.database.client.data.repository.LocalMessageDataSourceImpl
import com.kuro.chitchat.database.client.data.repository.LocalUserDataSourceImpl
import com.kuro.chitchat.database.client.databasePlatformModule
import com.kuro.chitchat.database.client.domain.repository.LocalChatRoomDataSource
import com.kuro.chitchat.database.client.domain.repository.LocalMessageDataSource
import com.kuro.chitchat.database.client.domain.repository.LocalUserDataSource
import org.koin.dsl.module

val databaseModule = module {
    single<UserDao> { createUserDao(get()) }
    single<ChatRoomDao> { createChatRoomDao(get()) }
    single<MessageDao> { createMessageDao(get()) }

    single<LocalUserDataSource> { LocalUserDataSourceImpl(get()) }
    single<LocalChatRoomDataSource> { LocalChatRoomDataSourceImpl(get()) }
    single<LocalMessageDataSource> { LocalMessageDataSourceImpl(get()) }
}

val databaseClientModule = listOf(databasePlatformModule(), databaseModule)

fun createChatRoomDao(appDatabase: AppDatabase): ChatRoomDao = appDatabase.chatRoomDao()
fun createMessageDao(appDatabase: AppDatabase): MessageDao = appDatabase.messageDao()
fun createUserDao(appDatabase: AppDatabase): UserDao = appDatabase.userDao()