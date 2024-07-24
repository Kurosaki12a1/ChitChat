package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import domain.models.ChatRoomModel
import domain.models.MessageModel
import org.bson.types.ObjectId

class CreatePublicChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        room: ChatRoomModel,
        creatorId: String
    ): ChatRoom {
        val roomId = ObjectId().toHexString()
        val message = convertMessageModelToMessage(room.lastMessage, roomId)
        val newRoom = ChatRoom(
            id = roomId,
            roomName = room.roomName,
            participants = room.participants,
            createdBy = creatorId,
            roomType = room.roomType,
            lastMessage = message
        )
        val chatRoom = chatRepository.createChatRoom(newRoom)
        chatRepository.addRoomToMembers(newRoom.id, newRoom.participants)
        return chatRoom
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