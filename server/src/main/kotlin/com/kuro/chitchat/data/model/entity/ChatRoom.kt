package com.kuro.chitchat.data.model.entity

import domain.model.RoomType
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class ChatRoom(
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    val roomName: String,
    val participants: List<String>,
    val lastMessage: Message? = null,
    val unReadCount: Int = 0,
    val roomType: RoomType
)
