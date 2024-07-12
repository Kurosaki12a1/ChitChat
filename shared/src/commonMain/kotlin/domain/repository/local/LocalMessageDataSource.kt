package domain.repository.local

import domain.model.MessageModel
import kotlinx.coroutines.flow.Flow

interface LocalMessageDataSource {
    suspend fun sendMessage(message: MessageModel)

    suspend fun updateMessage(message: MessageModel)

    suspend fun deleteMessage(message: MessageModel)

    fun getMessageById(messageId: String): Flow<MessageModel?>

    fun getMessageByChatRoom(chatRoomId: String): Flow<List<MessageModel>>
}