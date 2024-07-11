package com.kuro.chitchat.data.model.entity

import com.kuro.chitchat.data.conveter.KotlinLocalDateTimeSerializer
import domain.model.StatusUser
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class User(
    @Contextual
    @SerialName("_id")
    val id: String,
    val userId: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String? = null,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val lastActive: LocalDateTime,
    val status: StatusUser
)