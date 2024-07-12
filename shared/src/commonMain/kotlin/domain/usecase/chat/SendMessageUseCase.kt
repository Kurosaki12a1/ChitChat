package domain.usecase.chat

import data.model.dto.MessageDto
import domain.repository.remote.SessionChatRepository
import io.ktor.websocket.WebSocketSession

class SendMessageUseCase(private val repository: SessionChatRepository) {
    suspend operator fun invoke(session: WebSocketSession, message: MessageDto) {
        repository.sendMessage(session, message)
    }
}