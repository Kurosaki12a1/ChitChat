package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository

class GetChatHistoryUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(roomId: String): List<Message> {
        return repository.getMessageForRoom(roomId)
    }
}