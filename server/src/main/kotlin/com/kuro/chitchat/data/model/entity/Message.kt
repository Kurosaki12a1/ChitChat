package com.kuro.chitchat.data.model.entity

import com.kuro.chitchat.data.conveter.KotlinLocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.BsonDateTime
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Message(
    @Contextual
    @SerialName("_id")
    val id: ObjectId = ObjectId(),
    val senderId: String,
    val content: String,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val timeStamp: LocalDateTime,
    val chatRoomId: String,
    val isRead: Boolean = false
)
