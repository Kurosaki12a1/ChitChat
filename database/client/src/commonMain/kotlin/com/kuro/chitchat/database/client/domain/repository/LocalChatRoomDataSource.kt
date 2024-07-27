package com.kuro.chitchat.database.client.domain.repository

import com.kuro.chitchat.database.client.entity.ChatRoomEntity
import kotlinx.coroutines.flow.Flow

interface LocalChatRoomDataSource {
    suspend fun createChatRoom(chatRoom: ChatRoomEntity)

    suspend fun updateChatRoom(chatRoom: ChatRoomEntity)

    suspend fun deleteChatRoom(chatRoom: ChatRoomEntity)

    fun getChatRoomById(roomId: String): Flow<ChatRoomEntity?>

    fun getAllChatRooms(): Flow<List<ChatRoomEntity>>
}