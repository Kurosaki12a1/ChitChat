package com.kuro.chitchat.domain.model

import com.kuro.chitchat.data.model.entity.Message
import domain.model.UserModel
import kotlinx.serialization.Serializable


@Serializable
data class PrivateChatRequest(
    val sender : UserModel,
    val receiver : UserModel,
    val firstMessage: Message? = null
)
