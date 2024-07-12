package data.model.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val id: String,
    val senderId: String? = null,
    val content: String? = null,
    val timeStamp: LocalDateTime? = null,
    val chatRoomId: String? = null,
    val isRead: Boolean? = false,
    val edited: Boolean? = false,
    val reactions: Map<String, Int>? = emptyMap()
)
