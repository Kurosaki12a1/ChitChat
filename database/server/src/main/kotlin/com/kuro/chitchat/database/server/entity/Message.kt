package com.kuro.chitchat.database.server.entity

import com.kuro.chitchat.database.server.converter.KotlinLocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Message(
    @Contextual
    @SerialName("_id")
    val id: String = ObjectId().toHexString(),
    val senderId: String,
    val content: String,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val timeStamp: LocalDateTime,
    val chatRoomId: String,
    val isRead: Boolean = false,
    val edited: Boolean = false,
    @Contextual val reactions: List<Reaction> = listOf()
)

@Serializable
data class Reaction(
    val userId: String,
    val emoCode: String
)
