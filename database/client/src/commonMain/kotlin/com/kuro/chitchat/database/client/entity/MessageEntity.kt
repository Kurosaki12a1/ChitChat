package com.kuro.chitchat.database.client.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Entity(tableName = "Message")
data class MessageEntity(
    // Id of message
    @PrimaryKey val id: String,

    // Id from who sent message
    val senderId: String,
    val content: String,
    val timeStamp: LocalDateTime,
    val chatRoomId: String,
    val isRead: Boolean,
    val edited: Boolean,
    val reactions: List<ReactionEntity>
)

@Serializable
data class ReactionEntity(
    val userId: String,
    val emoCode: String
)

