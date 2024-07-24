package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.util.generateChatRoomId
import com.kuro.chitchat.util.generateChatRoomName
import domain.models.MessageModel
import domain.models.RoomType
import domain.models.UserModel
import org.bson.types.ObjectId

/**
 * Use case class for creating or retrieving a chat room.
 *
 * @property chatRepository The repository for managing chat-related data and operations.
 */
class CreateOrGetChatRoomUseCase(private val chatRepository: ChatRepository) {

    /**
     * Creates a new chat room or retrieves an existing one based on the sender and receiver.
     *
     * This function generates a chat room ID based on the sender and receiver IDs,
     * checks if a chat room with that ID already exists, and either retrieves the existing
     * room or creates a new one. If a new room is created, it also sends the first message
     * if provided.
     *
     * @param sender The user model of the sender.
     * @param receiver The user model of the receiver.
     * @param firstMessage The first message to be sent in the chat room, if any.
     * @return The existing or newly created chat room.
     */
    suspend operator fun invoke(
        sender: UserModel,
        receiver: UserModel,
        type : String,
        firstMessage: MessageModel?
    ): ChatRoom {
        val roomId = generateChatRoomId(sender.userId, receiver.userId)
        val existingRoom = chatRepository.findChatRoomById(roomId)

        return if (existingRoom != null) {
            chatRepository.addRoomToMembers(existingRoom.id, existingRoom.participants)
            existingRoom
        } else {
            val message = convertMessageModelToMessage(firstMessage, roomId)
            val newRoom = ChatRoom(
                id = roomId,
                roomName = generateChatRoomName(sender.name, receiver.name),
                participants = listOf(sender.userId, receiver.userId),
                lastMessage = message,
                createdBy = sender.userId,
                roomType = type
            )
            chatRepository.createChatRoom(newRoom)
            chatRepository.addRoomToMembers(newRoom.id, newRoom.participants)
            if (message != null) {
                chatRepository.sendMessage(message.copy(chatRoomId = roomId))
            }
            newRoom
        }
    }

    /**
     * Converts a MessageModel to a Message.
     *
     * This function takes a MessageModel and converts it to a Message entity that
     * can be used within the chat room.
     *
     * @param messageModel The message model to be converted.
     * @return The converted Message, or null if the input message model is null.
     */
    private fun convertMessageModelToMessage(
        messageModel: MessageModel?,
        roomId: String
    ): Message? {
        return if (messageModel != null) {
            Message(
                id = ObjectId().toHexString(),
                content = messageModel.content,
                senderId = messageModel.senderId,
                chatRoomId = roomId,
                timeStamp = messageModel.timeStamp,
                isRead = messageModel.isRead,
                edited = messageModel.edited,
                reactions = messageModel.reactions
            )
        } else null
    }
}