package com.kuro.chitchat.database.server.domain.repository

import com.kuro.chitchat.database.server.entity.Message

interface MessageDataSource {
    suspend fun sendMessage(message: Message)
    suspend fun getMessageForRoom(roomId: String): List<Message>
}