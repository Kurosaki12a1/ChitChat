package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import com.kuro.chitchat.util.generateChatRoomId
import com.kuro.chitchat.util.generateChatRoomName
import domain.model.RoomType
import domain.model.UserModel

class CreateOrGetChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        sender: UserModel,
        receiver: UserModel,
        firstMessage: Message?
    ): ChatRoom {
        val roomId = generateChatRoomId(sender.userId, receiver.userId)
        val existingRoom = chatRepository.findChatRoomById(roomId)

        return if (existingRoom != null) {
            chatRepository.addRoomToMembers(existingRoom.id, existingRoom.participants)
            existingRoom
        } else {
            val newRoom = ChatRoom(
                id = roomId,
                roomName = generateChatRoomName(sender.name, receiver.name),
                participants = listOf(sender.userId, receiver.userId),
                lastMessage = firstMessage,
                createdBy = sender.userId,
                roomType = RoomType.NORMAL
            )
            chatRepository.createChatRoom(newRoom)
            chatRepository.addRoomToMembers(newRoom.id, newRoom.participants)
            if (firstMessage != null) {
                chatRepository.sendMessage(firstMessage.copy(chatRoomId = roomId))
            }
            newRoom
        }
    }
}