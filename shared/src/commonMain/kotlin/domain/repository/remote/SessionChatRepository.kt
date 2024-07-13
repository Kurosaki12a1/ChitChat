package domain.repository.remote

import data.model.dto.ChatRoomDto
import data.model.dto.HistoryChatRoomDto
import data.model.dto.MessageDto
import domain.model.ChatRoomModel
import domain.model.MessageModel
import domain.model.PrivateChatRequest
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

interface SessionChatRepository {
    suspend fun getUserChatRooms(userId: String): List<ChatRoomDto>
    suspend fun createPublicChatRoom(room: ChatRoomModel, creatorId: String): ChatRoomDto?
    suspend fun startPrivateChat(request: PrivateChatRequest): ChatRoomDto?
    suspend fun joinPublicChatRoom(roomId: String, userId: String): ChatRoomDto?
    suspend fun getChatHistory(roomId: String): HistoryChatRoomDto

    fun connectToWebSocket(userId: String): Flow<WebSocketSession>
    suspend fun sendMessage(message: MessageModel)
    fun receiveMessages(): Flow<MessageDto>
}