package com.kuro.chitchat.database.server.data

import com.kuro.chitchat.database.server.domain.repository.RoomDataSource
import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import com.kuro.chitchat.database.server.utils.now
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.updateOne
import org.litote.kmongo.eq

/**
 * Implementation of RoomRepository for managing chat rooms in the MongoDB database.
 *
 * @property chatRooms The collection of chat rooms in the database.
 */
class RoomDataSourceImpl(database: CoroutineDatabase) : RoomDataSource {
    private val chatRooms = database.getCollection<ChatRoom>()

    /**
     * Creates a new chat room in the database.
     *
     * @param room The chat room to be created.
     * @return The created chat room.
     */
    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        chatRooms.insertOne(room)
        return room
    }

    override suspend fun updateChatRoom(room: ChatRoom): Boolean {
        return chatRooms.updateOneById(room.id, room).wasAcknowledged()
    }

    /**
     * Updates the last message of a chat room.
     *
     * @param lastMessage The new last message.
     * @param room The chat room to be updated.
     */
    override suspend fun updateLastMessageRoom(lastMessage: Message, room: ChatRoom) {
        val updatedRoom = room.copy(lastMessage = lastMessage, updatedTime = now())
        chatRooms.updateOneById(updatedRoom.id, updatedRoom)
    }

    /**
     * Finds a chat room by its ID.
     *
     * @param roomId The ID of the chat room.
     * @return The chat room with the specified ID, or null if not found.
     */
    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return chatRooms.findOne(ChatRoom::id eq roomId)
    }

    /**
     * Finds a chat room by a message's chat room ID.
     *
     * @param message The message whose chat room ID is used to find the chat room.
     * @return The chat room associated with the message, or null if not found.
     */
    override suspend fun findRoomByMessage(message: Message): ChatRoom? {
        return chatRooms.findOne(ChatRoom::id eq message.chatRoomId)
    }

    /**
     * Adds a participant to a chat room.
     *
     * @param roomId The ID of the chat room.
     * @param userId The ID of the user to be added as a participant.
     * @return The updated chat room, or null if the chat room is not found.
     */
    override suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom? {
        val room = chatRooms.findOne(ChatRoom::id eq roomId)
        return if (room != null) {
            val updatedRoom = room.copy(participants = room.participants + userId)
            chatRooms.updateOneById(updatedRoom.id, updatedRoom)
            updatedRoom
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
        return chatRooms.find(ChatRoom::participants contains userId).toList()
    }

    /**
     * Retrieves the participants of a chat room by its ID.
     *
     * @param roomId The ID of the chat room.
     * @return A list of participant IDs in the chat room, or an empty list if the chat room is not found.
     */
    override suspend fun getParticipantsByRoom(roomId: String): List<String> {
        return chatRooms.findOne(ChatRoom::id eq roomId)?.participants ?: emptyList()
    }
}