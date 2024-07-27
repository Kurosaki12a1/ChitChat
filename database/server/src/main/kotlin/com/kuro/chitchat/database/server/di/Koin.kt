package com.kuro.chitchat.database.server.di

import com.kuro.chitchat.database.server.data.MessageDataSourceImpl
import com.kuro.chitchat.database.server.data.RoomDataSourceImpl
import com.kuro.chitchat.database.server.data.UserDataSourceImpl
import com.kuro.chitchat.database.server.domain.repository.MessageDataSource
import com.kuro.chitchat.database.server.domain.repository.RoomDataSource
import com.kuro.chitchat.database.server.domain.repository.UserDataSource
import com.kuro.chitchat.database.server.utils.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val databaseServerModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase(DATABASE_NAME)
    }
    single<MessageDataSource> { MessageDataSourceImpl(get()) }
    single<RoomDataSource> { RoomDataSourceImpl(get()) }
    single<UserDataSource> { UserDataSourceImpl(get()) }
}