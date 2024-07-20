package com.kuro.chitchat.database.client.data.repository

import com.kuro.chitchat.database.client.data.dao.MessageDao
import com.kuro.chitchat.database.client.domain.repository.LocalMessageDataSource
import com.kuro.chitchat.database.client.entity.MessageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class LocalMessageDataSourceImpl(
    private val messageDao: MessageDao
) : LocalMessageDataSource {
    override suspend fun sendMessage(message: MessageEntity) {
        withContext(Dispatchers.IO) {
            messageDao.sendMessage(message)
        }
    }

    override suspend fun updateMessage(message: MessageEntity) {
        withContext(Dispatchers.IO) {
            messageDao.updateMessage(message)
        }
    }

    override suspend fun deleteMessage(message: MessageEntity) {
        withContext(Dispatchers.IO) {
            messageDao.deleteMessage(message)
        }
    }

    override fun getMessageById(messageId: String): Flow<MessageEntity?> {
        return messageDao.getMessageById(messageId)
    }

    override fun getMessageByChatRoom(chatRoomId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessageByChatRoom(chatRoomId)
    }
}