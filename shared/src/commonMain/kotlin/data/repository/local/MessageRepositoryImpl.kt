package data.repository.local

import data.data_source.local.dao.MessageDao
import data.model.toEntity
import data.model.toModel
import domain.model.MessageModel
import domain.repository.local.MessageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class MessageRepositoryImpl(
    private val messageDao: MessageDao
) : MessageRepository {
    override suspend fun sendMessage(message: MessageModel) {
        withContext(Dispatchers.IO) {
            messageDao.sendMessage(message.toEntity())
        }
    }

    override suspend fun updateMessage(message: MessageModel) {
        withContext(Dispatchers.IO) {
            messageDao.updateMessage(message.toEntity())
        }
    }

    override suspend fun deleteMessage(message: MessageModel) {
        withContext(Dispatchers.IO) {
            messageDao.deleteMessage(message.toEntity())
        }
    }

    override fun getMessageById(messageId: String): Flow<MessageModel?> {
        return messageDao.getMessageById(messageId).map { it?.toModel() }
    }

    override fun getMessageByChatRoom(chatRoomId: String): Flow<List<MessageModel>> {
        return messageDao.getMessageByChatRoom(chatRoomId)
            .map { list -> list.map { item -> item.toModel() } }
    }
}