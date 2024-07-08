package com.kuro.chitchat.data.model.entity

import com.kuro.chitchat.data.conveter.KotlinLocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    @BsonId
    @Contextual
    val id: ObjectId = ObjectId(),
    val userId: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String? = null,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val lastActive: LocalDateTime
)