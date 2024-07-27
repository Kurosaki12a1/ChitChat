package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.domain.repository.ChatRepository

class GetChatRoomUserUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(userId: String): List<ChatRoom> {
        return chatRepository.getUserChatRooms(userId)
    }
}