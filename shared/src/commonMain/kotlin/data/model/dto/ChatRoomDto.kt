package data.model.dto

import domain.model.RoomType
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import utils.now

@Serializable
data class ChatRoomDto(
    val id: String,
    val roomName: String? = null,
    val participants: List<String>? = null,
    val lastMessage: MessageDto? = null,
    val unReadCount: Int? = 0,
    val roomType: RoomType = RoomType.NORMAL,
    val createdTime: LocalDateTime?,
    val updatedTime: LocalDateTime?,
    val createdBy: String?
)
