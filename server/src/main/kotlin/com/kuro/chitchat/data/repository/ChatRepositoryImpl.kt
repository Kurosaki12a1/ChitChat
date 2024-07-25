package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.mapper.toDTO
import com.kuro.chitchat.database.server.domain.repository.MessageDataSource
import com.kuro.chitchat.database.server.domain.repository.RoomDataSource
import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import com.kuro.chitchat.domain.model.Member
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.util.Constants.ADMIN_ID
import data.model.dto.MessageDto
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.now

/**
 * Implementation of ChatRepository for managing chat rooms and messages.
 *
 * @property roomRepository The repository for managing chat room data and operations.
 * @property messageRepository The repository for managing message data and operations.
 */
class ChatRepositoryImpl(
    private val roomRepository: RoomDataSource,
    private val messageRepository: MessageDataSource
) : ChatRepository {
    private val members = mutableMapOf<String, Member>()

    /**
     * Creates a new chat room.
     *
     * @param room The chat room to be created.
     * @return The created chat room.
     */
    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        return roomRepository.createChatRoom(room)
    }

    override suspend fun updateChatRoom(room: ChatRoom): Boolean {
        return roomRepository.updateChatRoom(room)
    }

    /**
     * Finds a chat room by its ID.
     *
     * @param roomId The ID of the chat room.
     * @return The chat room with the specified ID, or null if not found.
     */
    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return roomRepository.findChatRoomById(roomId)
    }

    /**
     * Retrieves messages for a specific chat room.
     *
     * @param roomId The ID of the chat room.
     * @return A list of messages for the chat room.
     */
    override suspend fun getMessageForRoom(roomId: String): List<Message> {
        return messageRepository.getMessageForRoom(roomId)
    }

    /**
     * Sends a message and updates the chat room's last message.
     *
     * @param message The message to be sent.
     */
    override suspend fun sendMessage(message: Message) {
        messageRepository.sendMessage(message)
        val room = roomRepository.findRoomByMessage(message)
        if (room != null) {
            roomRepository.updateLastMessageRoom(message, room)
        }
    }

    /**
     * Adds a WebSocket session for a specific user.
     *
     * @param userId The ID of the user.
     * @param webSocketSession The WebSocket session to be added.
     */
    override fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession) {
        val member = Member(sender = userId, webSocket = webSocketSession)
        members[userId] = member
    }

    /**
     * Removes the WebSocket session for a specific user.
     *
     * @param userId The ID of the user whose session is to be removed.
     */
    override fun removeWebSocketSession(userId: String) {
        members.remove(userId)
    }

    /**
     * Retrieves the WebSocket session for a specific user.
     *
     * @param userId The ID of the user.
     * @return The WebSocket session for the user, or null if not found.
     */
    override fun getWebSocketSession(userId: String): WebSocketSession? {
        return members[userId]?.webSocket
    }

    /**
     * Broadcasts a message to all participants in a chat room.
     *
     * @param roomId The ID of the chat room.
     * @param message The message to be broadcasted.
     */
    override suspend fun broadcastMessageToRoom(roomId: String, message: Message) {
        val participants = roomRepository.getParticipantsByRoom(roomId)
        for (participant in participants) {
            members[participant]?.webSocket?.send(Frame.Text(Json.encodeToString<MessageDto>(message.toDTO())))
        }
    }

    /**
     * Adds a chat room to a member's list of chat rooms.
     *
     * @param userId The ID of the user.
     * @param roomId The ID of the chat room.
     */
    override suspend fun addRoomToMember(userId: String, roomId: String) {
        if (members[userId] != null
            && members[userId]?.webSocket != null
            && members[userId]?.chatRooms?.contains(roomId) == false
        ) {
            members[userId]?.chatRooms?.add(roomId)
            getWebSocketSession(userId)?.send(
                // This is for notify user room created only (does not add to history chat)
                Frame.Text(
                    Json.encodeToString<MessageDto>(
                        Message(
                            senderId = ADMIN_ID,
                            content = "You have joined the room: $roomId",
                            chatRoomId = roomId,
                            timeStamp = now(),
                            isRead = true
                        ).toDTO()
                    )
                )
            )
        }
    }

    /**
     * Adds a chat room to multiple members' lists of chat rooms.
     *
     * @param roomId The ID of the chat room.
     * @param memberIds The list of user IDs to be added to the chat room.
     */
    override suspend fun addRoomToMembers(roomId: String, memberIds: List<String>) {
        memberIds.forEach { userId ->
            addRoomToMember(userId = userId, roomId = roomId)
        }
    }

    /**
     * Removes a chat room from a member's list of chat rooms.
     *
     * @param userId The ID of the user.
     * @param roomId The ID of the chat room.
     */
    override suspend fun removeRoomFromMember(userId: String, roomId: String) {
        members[userId]?.chatRooms?.remove(roomId)
    }

    /**
     * Adds a participant to a chat room.
     *
     * @param roomId The ID of the chat room.
     * @param userId The ID of the user to be added as a participant.
     * @return The updated chat room, or null if the chat room is not found.
     */
    override suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom? {
        val room = roomRepository.addParticipantToRoom(roomId, userId)
        return if (room != null) {
            addRoomToMember(userId, roomId)
            room
        } else {
            null
        }
    }


    /**
     * Retrieves all chat rooms a user is a participant of.
     *
     * @param userId The ID of the user.
     * @return A list of chat rooms the user is a participant of.
     */
    override suspend fun getUserChatRooms(userId: String): List<ChatRoom> {
        return roomRepository.getUserChatRooms(userId)
    }
}
