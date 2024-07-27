package com.kuro.chitchat.di

import com.kuro.chitchat.data.repository.ChatRepositoryImpl
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.domain.usecase.CreateOrGetChatRoomUseCase
import com.kuro.chitchat.domain.usecase.CreatePublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.GetChatRoomUserUseCase
import com.kuro.chitchat.domain.usecase.JoinPublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.WebSocketUseCase
import org.koin.dsl.module

val koinModule = module {
    single<ChatRepository> { ChatRepositoryImpl(get(), get()) }
    factory { WebSocketUseCase(get()) }
    factory { CreateOrGetChatRoomUseCase(get()) }
    factory { CreatePublicChatRoomUseCase(get()) }
    factory { JoinPublicChatRoomUseCase(get()) }
    factory { GetChatRoomUserUseCase(get()) }
}