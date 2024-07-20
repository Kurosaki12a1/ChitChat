package com.kuro.chitchat.database.client.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "ChatRoom")
data class ChatRoomEntity(
    // Id of message
    @PrimaryKey val id: String,

    // Id from who sent message
    val roomName: String,

    // List of user Ids
    val participants: List<String>,

    // Last Message
    val lastMessage: MessageEntity?,
    val unReadCount: Int = 0,
    val roomType: String,
    val createdTime: LocalDateTime,
    val updatedTime: LocalDateTime,
    val createdBy: String
)
