package domain.usecase.chat

import domain.repository.remote.SessionChatRepository
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

class ConnectToWebSocketUseCase(private val repository: SessionChatRepository) {
    operator fun invoke(userId: String): Flow<WebSocketSession> {
        return repository.connectToWebSocket(userId)
    }
}