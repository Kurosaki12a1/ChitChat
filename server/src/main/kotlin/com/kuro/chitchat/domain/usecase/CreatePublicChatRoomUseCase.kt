package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.domain.repository.ChatRepository
import domain.models.ChatRoomModel
import org.bson.types.ObjectId

class CreatePublicChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(
        room: ChatRoomModel,
        creatorId: String
    ): ChatRoom {
        val newRoom = ChatRoom(
            id = ObjectId().toHexString(),
            roomName = room.roomName,
            participants = room.participants,
            createdBy = creatorId,
            roomType = room.roomType
        )
        val chatRoom = chatRepository.createChatRoom(newRoom)
        chatRepository.addRoomToMembers(newRoom.id, newRoom.participants)
        return chatRoom
    }
}