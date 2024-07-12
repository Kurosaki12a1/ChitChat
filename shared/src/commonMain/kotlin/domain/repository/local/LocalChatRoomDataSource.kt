package domain.repository.local

import domain.model.ChatRoomModel
import kotlinx.coroutines.flow.Flow

interface LocalChatRoomDataSource {
    suspend fun createChatRoom(chatRoom: ChatRoomModel)

    suspend fun updateChatRoom(chatRoom: ChatRoomModel)

    suspend fun deleteChatRoom(chatRoom: ChatRoomModel)

    fun getChatRoomById(roomId: String): Flow<ChatRoomModel?>

    fun getAllChatRooms(): Flow<List<ChatRoomModel>>
}