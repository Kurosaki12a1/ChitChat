package data.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class HistoryChatRoomDto(
    val roomId: String,
    val messages: List<MessageDto>,
    @Transient
    val error: Exception? = null
)
