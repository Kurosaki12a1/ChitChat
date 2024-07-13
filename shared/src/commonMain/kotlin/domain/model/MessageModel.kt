package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val id: String = "",
    val senderId: String,
    val content: String,
    val timeStamp: LocalDateTime,
    val chatRoomId: String,
    val isRead: Boolean = false,
    val edited: Boolean = false,
    val reactions: Map<String, Int> = emptyMap()
)
