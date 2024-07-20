package domain.models

import kotlinx.serialization.Serializable


@Serializable
data class PrivateChatRequest(
    val sender: UserModel,
    val receiver: UserModel,
    val firstMessage: MessageModel? = null
)
