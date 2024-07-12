package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.MessageRepository
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MessageRepositoryImpl(database: CoroutineDatabase) : MessageRepository {
    private val messages = database.getCollection<Message>()

    override suspend fun sendMessage(message: Message) {
        messages.insertOne(message)
    }

    override suspend fun getMessageForRoom(roomId: String): List<Message> {
        return messages.find(Message::chatRoomId eq roomId).toList()
    }
}