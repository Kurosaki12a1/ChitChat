package domain.repository.remote

import data.model.dto.MessageDto
import domain.model.MessageModel
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    fun connectToWebSocket(userId: String): Flow<WebSocketSession>
    suspend fun sendMessage(message: MessageModel)
    fun receiveMessages(): Flow<MessageDto>
    suspend fun disconnectToWebsocket()
}