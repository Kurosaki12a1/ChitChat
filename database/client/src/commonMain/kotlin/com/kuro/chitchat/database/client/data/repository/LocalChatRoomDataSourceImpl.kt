package com.kuro.chitchat.database.client.data.repository

import com.kuro.chitchat.database.client.data.dao.ChatRoomDao
import com.kuro.chitchat.database.client.domain.repository.LocalChatRoomDataSource
import com.kuro.chitchat.database.client.entity.ChatRoomEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalChatRoomDataSourceImpl(
    private val chatRoomDao: ChatRoomDao
) : LocalChatRoomDataSource {
    override suspend fun createChatRoom(chatRoom: ChatRoomEntity) {
        withContext(Dispatchers.IO) {
            chatRoomDao.createChatRoom(chatRoom)
        }
    }

    override suspend fun updateChatRoom(chatRoom: ChatRoomEntity) {
        withContext(Dispatchers.IO) {
            chatRoomDao.updateChatRoom(chatRoom)
        }
    }

    override suspend fun deleteChatRoom(chatRoom: ChatRoomEntity) {
        withContext(Dispatchers.IO) {
            chatRoomDao.deleteChatRoom(chatRoom)
        }
    }

    override fun getChatRoomById(roomId: String): Flow<ChatRoomEntity?> {
        return chatRoomDao.getChatRoomById(roomId)
    }

    override fun getAllChatRooms(): Flow<List<ChatRoomEntity>> {
        return chatRoomDao.getAllChatRooms()
    }
}