package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.domain.repository.ChatRepository

class JoinPublicChatRoomUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(roomId: String, userId: String): ChatRoom? {
        return chatRepository.addParticipantToRoom(roomId, userId)
    }
}