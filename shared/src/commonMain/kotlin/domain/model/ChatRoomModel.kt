package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ChatRoomModel(
    val id: String,
    val roomName: String,
    val participants: List<String> = listOf(),
    val lastMessage: MessageModel? = null,
    val unReadCount: Int = 0,
    val roomType: RoomType
)
