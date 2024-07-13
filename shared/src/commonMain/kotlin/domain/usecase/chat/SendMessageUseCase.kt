package domain.usecase.chat

import data.model.dto.MessageDto
import domain.model.MessageModel
import domain.repository.remote.SessionChatRepository

class SendMessageUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(message: MessageModel) {
        repository.sendMessage(message)
    }
}