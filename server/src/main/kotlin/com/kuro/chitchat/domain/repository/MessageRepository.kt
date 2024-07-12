package com.kuro.chitchat.domain.repository

import com.kuro.chitchat.data.model.entity.Message

interface MessageRepository {
    suspend fun sendMessage(message: Message)
    suspend fun getMessageForRoom(roomId: String): List<Message>
}