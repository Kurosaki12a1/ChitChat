package domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class MessageModel(
    val id: String = "",
    val senderId: String,
    val content: String,
    @Contextual val timeStamp: LocalDateTime,
    val chatRoomId: String = "",
    val isRead: Boolean = false,
    val edited: Boolean = false,
    val reactions: Map<String, Int> = emptyMap()
)
