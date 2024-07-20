package com.kuro.chitchat.database.server.entity

import com.kuro.chitchat.database.server.converter.KotlinLocalDateTimeSerializer
import com.kuro.chitchat.database.server.utils.now
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class ChatRoom(
    @Contextual
    @SerialName("_id")
    val id: String = ObjectId().toHexString(),
    val roomName: String,
    val participants: List<String>,
    val lastMessage: Message? = null,
    val unReadCount: Int = 0,
    val roomType: String,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val createdTime: LocalDateTime = now(),
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val updatedTime: LocalDateTime = now(),
    val createdBy: String
)