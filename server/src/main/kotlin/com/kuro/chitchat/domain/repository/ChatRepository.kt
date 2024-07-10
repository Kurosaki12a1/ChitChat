package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.model.Member
import domain.model.RoomType

interface ChatRepository {
    suspend fun createChatRoom(
        roomName: String,
        roomType: RoomType,
        participants: List<String>
    ): ChatRoom

    suspend fun findChatRoomById(roomId: String): ChatRoom?
    suspend fun findChatRoomByName(roomName: String): ChatRoom?
    suspend fun sendMessage(message: Message)
    suspend fun updateLastMessage(roomId: String, message: Message)
    fun connectToSocket(member: Member)
    fun updateMemberRoom(senderId: String, roomId: String)
    fun disconnectFromSocket(senderId: String, roomId: String)
    fun getMembersForRoom(roomId: String): List<Member>
    fun findAllChatRoomsForUser(userId: String): List<String>
}