package data.repository.local

import data.data_source.local.dao.ChatRoomDao
import data.model.toEntity
import data.model.toModel
import domain.model.ChatRoomModel
import domain.repository.local.LocalChatRoomDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocalChatRoomDataSourceImpl(
    private val chatRoomDao: ChatRoomDao
) : LocalChatRoomDataSource {
    override suspend fun createChatRoom(chatRoom: ChatRoomModel) {
        withContext(Dispatchers.IO) {
            chatRoomDao.createChatRoom(chatRoom.toEntity())
        }
    }

    override suspend fun updateChatRoom(chatRoom: ChatRoomModel) {
        withContext(Dispatchers.IO) {
            chatRoomDao.updateChatRoom(chatRoom.toEntity())
        }
    }

    override suspend fun deleteChatRoom(chatRoom: ChatRoomModel) {
        withContext(Dispatchers.IO) {
            chatRoomDao.deleteChatRoom(chatRoom.toEntity())
        }
    }

    override fun getChatRoomById(roomId: String): Flow<ChatRoomModel?> {
        return chatRoomDao.getChatRoomById(roomId).map { it?.toModel() }
    }

    override fun getAllChatRooms(): Flow<List<ChatRoomModel>> {
        return chatRoomDao.getAllChatRooms().map { list -> list.map { item -> item.toModel() } }
    }
}