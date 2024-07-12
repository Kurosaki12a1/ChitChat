package domain.repository.remote

import data.model.dto.ChatRoomDto
import data.model.dto.HistoryChatRoomDto
import domain.model.ChatRoomModel
import domain.model.PrivateChatRequest

interface ChatRoomRemoteRepository {
    suspend fun getUserChatRooms(userId: String): List<ChatRoomDto>
    suspend fun createPublicChatRoom(room: ChatRoomModel, creatorId: String): ChatRoomDto?
    suspend fun startPrivateChat(request: PrivateChatRequest): ChatRoomDto?
    suspend fun joinPublicChatRoom(roomId: String, userId: String): ChatRoomDto?
    suspend fun getChatHistory(roomId: String): HistoryChatRoomDto
}