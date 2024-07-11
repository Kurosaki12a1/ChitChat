package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import io.ktor.websocket.WebSocketSession

/*
interface ChatRepository {
    suspend fun createChatRoom(room : ChatRoom): ChatRoom
    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun findChatRoomByName(roomName: String): ChatRoom?
    suspend fun sendMessage(message: Message)
    suspend fun getMessageForRoom(roomId: String) : Flow<List<Message>>
    suspend fun updateLastMessage(roomId: String, message: Message)
    suspend fun updateMemberRoom(senderId: String, roomId: String) : ChatRoom?
    fun connectToSocket(member: Member)
    fun disconnectFromSocket(senderId: String, roomId: String)
    fun getMembersForRoom(roomId: String): List<Member>
    fun findAllChatRoomsForUser(userId: String): List<String>
}*/

interface ChatRepository {
    suspend fun createChatRoom(room: ChatRoom): ChatRoom
    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun getMessageForRoom(roomId: String): List<Message>
    suspend fun sendMessage(message: Message)
    suspend fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession)
    suspend fun removeWebSocketSession(userId: String)
    suspend fun getWebSocketSession(userId: String): WebSocketSession?
    suspend fun broadcastMessageToRoom(roomId: String, message: Message)
    suspend fun addRoomToMember(userId: String, roomId: String)
    suspend fun addRoomToMembers(roomId: String, memberIds: List<String>)
    suspend fun removeRoomFromMember(userId: String, roomId: String)
    suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom?
}
