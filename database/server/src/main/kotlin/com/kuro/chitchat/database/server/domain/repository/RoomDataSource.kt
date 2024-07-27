package com.kuro.chitchat.database.server.domain.repository

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message

interface RoomDataSource {
    suspend fun createChatRoom(room: ChatRoom): ChatRoom
    suspend fun updateChatRoom(room: ChatRoom) : Boolean
    suspend fun updateLastMessageRoom(lastMessage: Message, room: ChatRoom)
    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun findRoomByMessage(message: Message): ChatRoom?
    suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom?
    suspend fun getUserChatRooms(userId: String): List<ChatRoom>
    suspend fun getParticipantsByRoom(roomId: String): List<String>
}