package com.kuro.chitchat.database.server.data

import com.kuro.chitchat.database.server.domain.repository.MessageDataSource
import com.kuro.chitchat.database.server.entity.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MessageDataSourceImpl(database: CoroutineDatabase) : MessageDataSource {
    private val messages = database.getCollection<Message>()

    override suspend fun sendMessage(message: Message) {
        messages.insertOne(message)
    }

    override suspend fun getMessageForRoom(roomId: String): List<Message> {
        return messages.find(Message::chatRoomId eq roomId).toList()
    }
}