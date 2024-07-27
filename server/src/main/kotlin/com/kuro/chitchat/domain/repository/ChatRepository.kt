package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import io.ktor.websocket.WebSocketSession

interface ChatRepository {
    suspend fun createChatRoom(room: ChatRoom): ChatRoom
    suspend fun updateChatRoom(room: ChatRoom) : Boolean
    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun getMessageForRoom(roomId: String): List<Message>
    suspend fun sendMessage(message: Message)
    fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession)
    fun removeWebSocketSession(userId: String)
    fun getWebSocketSession(userId: String): WebSocketSession?
    suspend fun broadcastMessageToRoom(roomId: String, message: Message)
    suspend fun addRoomToMember(userId: String, roomId: String)
    suspend fun addRoomToMembers(roomId: String, memberIds: List<String>)
    suspend fun removeRoomFromMember(userId: String, roomId: String)
    suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom?
    suspend fun getUserChatRooms(userId: String): List<ChatRoom>
}
