package com.kuro.chitchat.database.client.domain.repository

import com.kuro.chitchat.database.client.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

interface LocalMessageDataSource {
    suspend fun sendMessage(message: MessageEntity)

    suspend fun updateMessage(message: MessageEntity)

    suspend fun deleteMessage(message: MessageEntity)

    fun getMessageById(messageId: String): Flow<MessageEntity?>

    fun getMessageByChatRoom(chatRoomId: String): Flow<List<MessageEntity>>
}