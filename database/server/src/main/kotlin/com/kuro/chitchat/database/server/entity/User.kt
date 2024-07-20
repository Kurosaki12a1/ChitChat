package com.kuro.chitchat.database.server.entity

import com.kuro.chitchat.database.server.converter.KotlinLocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val status: String
)