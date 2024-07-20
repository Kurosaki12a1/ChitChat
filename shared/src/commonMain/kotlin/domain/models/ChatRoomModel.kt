package domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomModel(
    val id: String = "", // Don't need declare id
    val roomName: String,
    val participants: List<String> = listOf(),
    val lastMessage: MessageModel? = null,
    val unReadCount: Int = 0,
    val roomType: String,
    @Contextual val createdTime: LocalDateTime,
    @Contextual val updatedTime: LocalDateTime,
    val createdBy: String
)
