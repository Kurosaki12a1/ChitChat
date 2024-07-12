package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomModel(
    val id: String,
    val roomName: String,
    val participants: List<String> = listOf(),
    val lastMessage: MessageModel? = null,
    val unReadCount: Int = 0,
    val roomType: RoomType,
    val createdTime: LocalDateTime,
    val updatedTime: LocalDateTime,
    val createdBy: String
)
