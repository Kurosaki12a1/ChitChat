package com.kuro.chitchat.di

import com.kuro.chitchat.data.repository.ChatRepositoryImpl
import com.kuro.chitchat.data.repository.MessageRepositoryImpl
import com.kuro.chitchat.data.repository.RoomRepositoryImpl
import com.kuro.chitchat.data.repository.UserDataSourceImpl
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.domain.repository.MessageRepository
import com.kuro.chitchat.domain.repository.RoomRepository
import com.kuro.chitchat.domain.repository.UserDataSource
import com.kuro.chitchat.domain.usecase.CreateOrGetChatRoomUseCase
import com.kuro.chitchat.domain.usecase.CreatePublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.GetChatRoomUserUseCase
import com.kuro.chitchat.domain.usecase.JoinPublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.WebSocketUseCase
import com.kuro.chitchat.util.Constants.DATABASE_NAME
import org.koin.dsl.module
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val koinModule = module {
    single {
        KMongo.createClient().coroutine.getDatabase(DATABASE_NAME)
    }
    single<MessageRepository> { MessageRepositoryImpl(get()) }
    single<RoomRepository> { RoomRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get(), get()) }
    single<UserDataSource> { UserDataSourceImpl(get()) }
    factory { WebSocketUseCase(get()) }
    factory { CreateOrGetChatRoomUseCase(get()) }
    factory { CreatePublicChatRoomUseCase(get()) }
    factory { JoinPublicChatRoomUseCase(get()) }
    factory { GetChatRoomUserUseCase(get()) }
}