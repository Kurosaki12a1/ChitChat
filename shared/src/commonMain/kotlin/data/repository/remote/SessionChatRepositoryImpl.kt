package data.repository.remote

import data.model.dto.ChatRoomDto
import data.model.dto.HistoryChatRoomDto
import data.model.dto.MessageDto
import domain.model.ChatRoomModel
import domain.model.MessageModel
import domain.model.PrivateChatRequest
import domain.repository.remote.ChatRoomRemoteRepository
import domain.repository.remote.SessionChatRepository
import domain.repository.remote.SocketRepository
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.Flow

class SessionChatRepositoryImpl(
    private val chatRoomRemoteRepository: ChatRoomRemoteRepository,
    private val socketRepository: SocketRepository
) : SessionChatRepository {
    override suspend fun getUserChatRooms(userId: String): List<ChatRoomDto> {
        return chatRoomRemoteRepository.getUserChatRooms(userId)
    }

    override suspend fun createPublicChatRoom(
        room: ChatRoomModel,
        creatorId: String
    ): ChatRoomDto? {
        return chatRoomRemoteRepository.createPublicChatRoom(room, creatorId)
    }

    override suspend fun startPrivateChat(request: PrivateChatRequest): ChatRoomDto? {
        return chatRoomRemoteRepository.startPrivateChat(request)
    }

    override suspend fun joinPublicChatRoom(roomId: String, userId: String): ChatRoomDto? {
        return chatRoomRemoteRepository.joinPublicChatRoom(roomId, userId)
    }

    override suspend fun getChatHistory(roomId: String): HistoryChatRoomDto {
        return chatRoomRemoteRepository.getChatHistory(roomId)
    }

    override fun connectToWebSocket(userId: String): Flow<WebSocketSession> {
        return socketRepository.connectToWebSocket(userId)
    }

    override suspend fun sendMessage(message: MessageModel) {
        return socketRepository.sendMessage(message)
    }

    override fun receiveMessages(): Flow<MessageDto> {
        return socketRepository.receiveMessages()
    }

}