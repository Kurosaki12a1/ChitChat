package domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import utils.KotlinLocalDateTimeSerializer

@Serializable
data class MessageModel(
    val id: String = "",
    val senderId: String,
    val content: String,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val timeStamp: LocalDateTime,
    val chatRoomId: String = "",
    val isRead: Boolean = false,
    val edited: Boolean = false,
    @Contextual val reactions: List<Reaction> = listOf()
)
