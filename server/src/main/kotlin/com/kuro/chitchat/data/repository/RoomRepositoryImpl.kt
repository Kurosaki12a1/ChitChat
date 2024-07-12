package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.RoomRepository
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import utils.now

class RoomRepositoryImpl(database: CoroutineDatabase) : RoomRepository {
    private val chatRooms = database.getCollection<ChatRoom>()

    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        chatRooms.insertOne(room)
        return room
    }

    override suspend fun updateLastMessageRoom(lastMessage: Message, room: ChatRoom) {
        val updatedRoom = room.copy(lastMessage = lastMessage, updatedTime = now())
        chatRooms.updateOneById(updatedRoom.id, updatedRoom)
    }

    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return chatRooms.findOne(ChatRoom::id eq roomId)
    }

    override suspend fun findRoomByMessage(message: Message): ChatRoom? {
        return chatRooms.findOne(ChatRoom::id eq message.chatRoomId)
    }

    override suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom? {
        val room = chatRooms.findOne(ChatRoom::id eq roomId)
        return if (room != null) {
            val updatedRoom = room.copy(participants = room.participants + userId)
            chatRooms.updateOneById(updatedRoom.id, updatedRoom)
            //     addRoomToMember(userId, roomId)
            updatedRoom
        } else {
            null
        }
    }

    override suspend fun getUserChatRooms(userId: String): List<ChatRoom> {
        return chatRooms.find(ChatRoom::participants contains userId).toList()
    }

    override suspend fun getParticipantsByRoom(roomId: String): List<String> {
        return chatRooms.findOne(ChatRoom::id eq roomId)?.participants ?: emptyList()
    }
}