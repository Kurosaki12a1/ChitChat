package domain.repository.remote

import data.model.dto.MessageDto
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    fun connectToWebSocket(userId: String): Flow<WebSocketSession>
    suspend fun sendMessage(session: WebSocketSession, message: MessageDto)
    fun receiveMessages(session: WebSocketSession): Flow<MessageDto>
}