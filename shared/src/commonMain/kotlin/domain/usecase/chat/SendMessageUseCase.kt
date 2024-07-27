package domain.usecase.chat

import domain.models.MessageModel
import domain.repository.remote.SessionChatRepository

class SendMessageUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(message: MessageModel) {
        repository.sendMessage(message)
    }
}