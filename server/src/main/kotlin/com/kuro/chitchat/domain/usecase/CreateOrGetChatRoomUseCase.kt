package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.util.generateChatRoomId
import com.kuro.chitchat.util.generateChatRoomName
import domain.model.MessageModel
import domain.model.RoomType
import domain.model.UserModel

class CreateOrGetChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        sender: UserModel,
        receiver: UserModel,
        firstMessage: MessageModel?
    ): ChatRoom {
        val roomId = generateChatRoomId(sender.userId, receiver.userId)
        val existingRoom = chatRepository.findChatRoomById(roomId)

        return if (existingRoom != null) {
            chatRepository.addRoomToMembers(existingRoom.id, existingRoom.participants)
            existingRoom
        } else {
            val message = convertMessageModelToMessage(firstMessage)
            val newRoom = ChatRoom(
                id = roomId,
                roomName = generateChatRoomName(sender.name, receiver.name),
                participants = listOf(sender.userId, receiver.userId),
                lastMessage = message,
                createdBy = sender.userId,
                roomType = RoomType.NORMAL
            )
            chatRepository.createChatRoom(newRoom)
            chatRepository.addRoomToMembers(newRoom.id, newRoom.participants)
            if (message != null) {
                chatRepository.sendMessage(message.copy(chatRoomId = roomId))
            }
            newRoom
        }
    }

    private fun convertMessageModelToMessage(messageModel: MessageModel?): Message? {
        return if (messageModel != null) {
            Message(
                id = messageModel.id,
                content = messageModel.content,
                senderId = messageModel.senderId,
                chatRoomId = messageModel.chatRoomId,
                timeStamp = messageModel.timeStamp,
                isRead = messageModel.isRead,
                edited = messageModel.edited,
                reactions = messageModel.reactions
            )
        } else null
    }
}