package data.model.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomDto(
    val id: String,
    val roomName: String? = null,
    val participants: List<String>? = null,
    val lastMessage: MessageDto? = null,
    val unReadCount: Int? = 0,
    val roomPhoto: String? = null,
    val roomType: String? = null,
    val createdTime: LocalDateTime?,
    val updatedTime: LocalDateTime?,
    val createdBy: String?
)
