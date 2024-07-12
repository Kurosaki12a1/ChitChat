package com.kuro.chitchat.data.model.entity

import com.kuro.chitchat.common.conveter.KotlinLocalDateTimeSerializer
import domain.model.RoomType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import utils.now

@Serializable
data class ChatRoom(
    @Contextual
    @SerialName("_id")
    val id: String = ObjectId().toHexString(),
    val roomName: String,
    val participants: List<String>,
    val lastMessage: Message? = null,
    val unReadCount: Int = 0,
    val roomType: RoomType,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val createdTime: LocalDateTime = now(),
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val updatedTime: LocalDateTime = now(),
    val createdBy: String
)