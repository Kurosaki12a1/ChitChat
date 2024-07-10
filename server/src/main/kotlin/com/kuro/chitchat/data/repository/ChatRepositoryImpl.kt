package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.model.Member
import com.kuro.chitchat.domain.repository.ChatRepository
import domain.model.RoomType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class ChatRepositoryImpl(
    database: CoroutineDatabase
) : ChatRepository {
    private val chatRoomCollection = database.getCollection<ChatRoom>()
    private val messageCollection = database.getCollection<Message>()
    private val memberSessions = mutableListOf<Member>()

    override suspend fun createChatRoom(
        roomName: String,
        roomType: RoomType,
        participants: List<String>
    ): ChatRoom {
        val chatRoom = ChatRoom(
            roomName = roomName,
            participants = participants,
            roomType = roomType
        )
        return withContext(Dispatchers.IO) {
            if (chatRoomCollection.findOne(ChatRoom::roomName eq roomName) != null) {
                println("Cannot create chat room same name")
                chatRoomCollection.insertOne(document = chatRoom.copy(roomName = chatRoom.roomName + "-copy"))
                chatRoom.copy(roomName = chatRoom.roomName + "-copy")
            } else {
                chatRoomCollection.insertOne(document = chatRoom)
                chatRoom
            }
        }
    }

    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return chatRoomCollection.findOne(ChatRoom::id eq ObjectId(roomId))
    }

    override suspend fun findChatRoomByName(roomName: String): ChatRoom? {
        return chatRoomCollection.findOne(ChatRoom::roomName eq roomName)
    }

    override suspend fun sendMessage(message: Message) {
        messageCollection.insertOne(message)
    }

    override suspend fun updateLastMessage(roomId: String, message: Message) {
        chatRoomCollection.updateOne(
            filter = ChatRoom::id eq ObjectId(roomId),
            update = setValue(ChatRoom::lastMessage, message)
        )
    }

    override fun connectToSocket(member: Member) {
        if (!memberSessions.contains(member)) {
            memberSessions.add(member)
        }
    }

    override fun updateMemberRoom(senderId: String, roomId: String) {
        val member = memberSessions.find { it.sender == senderId }
        member?.chatRooms?.add(roomId)
    }

    override fun disconnectFromSocket(senderId: String, roomId: String) {
        val member = memberSessions.find { it.sender == senderId }
        member?.chatRooms?.remove(roomId)
        if (member?.chatRooms.isNullOrEmpty()) {
            memberSessions.remove(member)
        }
    }

    override fun getMembersForRoom(roomId: String): List<Member> {
        return memberSessions.filter { roomId in it.chatRooms }
    }

    override fun findAllChatRoomsForUser(userId: String): List<String> {
        return memberSessions.find { it.sender == userId }?.chatRooms?.toList() ?: emptyList()
    }

}