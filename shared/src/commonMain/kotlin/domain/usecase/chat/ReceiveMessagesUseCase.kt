package domain.usecase.chat

import data.model.dto.MessageDto
import domain.repository.remote.SessionChatRepository
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

class ReceiveMessagesUseCase(private val repository: SessionChatRepository) {
    operator fun invoke(): Flow<MessageDto> {
        return repository.receiveMessages()
    }
}