package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message

interface RoomRepository {
    suspend fun createChatRoom(room: ChatRoom): ChatRoom
    suspend fun updateLastMessageRoom(lastMessage: Message, room: ChatRoom)
    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun findRoomByMessage(message: Message): ChatRoom?
    suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom?
    suspend fun getUserChatRooms(userId: String): List<ChatRoom>
    suspend fun getParticipantsByRoom(roomId: String): List<String>
}