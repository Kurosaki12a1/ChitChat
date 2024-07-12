package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.model.Member
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.domain.repository.MessageRepository
import com.kuro.chitchat.domain.repository.RoomRepository
import com.kuro.chitchat.util.Constants.ADMIN_ID
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.now

class ChatRepositoryImpl(
    private val roomRepository: RoomRepository,
    private val messageRepository: MessageRepository
) : ChatRepository {
    private val members = mutableMapOf<String, Member>()

    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        return roomRepository.createChatRoom(room)
    }

    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return roomRepository.findChatRoomById(roomId)
    }

    override suspend fun getMessageForRoom(roomId: String): List<Message> {
        return messageRepository.getMessageForRoom(roomId)
    }

    override suspend fun sendMessage(message: Message) {
        messageRepository.sendMessage(message)
        val room = roomRepository.findRoomByMessage(message)
        if (room != null) {
            roomRepository.updateLastMessageRoom(message, room)
        }
    }

    override fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession) {
        val member = Member(sender = userId, webSocket = webSocketSession)
        members[userId] = member
    }

    override fun removeWebSocketSession(userId: String) {
        members.remove(userId)
    }

    override fun getWebSocketSession(userId: String): WebSocketSession? {
        return members[userId]?.webSocket
    }

    override suspend fun broadcastMessageToRoom(roomId: String, message: Message) {
        val participants = roomRepository.getParticipantsByRoom(roomId)
        for (participant in participants) {
            members[participant]?.webSocket?.send(Frame.Text(Json.encodeToString<Message>(message)))
        }
    }

    override suspend fun addRoomToMember(userId: String, roomId: String) {
        if (members[userId] != null
            && members[userId]?.webSocket != null
            && members[userId]?.chatRooms?.contains(roomId) == false
        ) {
            members[userId]?.chatRooms?.add(roomId)
            getWebSocketSession(userId)?.send(
                // This is for notify user room created only (does not add to history chat)
                Frame.Text(
                    Json.encodeToString<Message>(
                        Message(
                            senderId = ADMIN_ID,
                            content = "You have joined the room: $roomId",
                            chatRoomId = roomId,
                            timeStamp = now(),
                            isRead = true
                        )
                    )
                )
            )
        }
    }

    override suspend fun addRoomToMembers(roomId: String, memberIds: List<String>) {
        memberIds.forEach { userId ->
            addRoomToMember(userId = userId, roomId = roomId)
        }
    }

    override suspend fun removeRoomFromMember(userId: String, roomId: String) {
        members[userId]?.chatRooms?.remove(roomId)
    }

    override suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom? {
        val room = roomRepository.addParticipantToRoom(roomId, userId)
        return if (room != null) {
            addRoomToMember(userId, roomId)
            room
        } else {
            null
        }
    }

    override suspend fun getUserChatRooms(userId: String): List<ChatRoom> {
        return roomRepository.getUserChatRooms(userId)
    }
}
