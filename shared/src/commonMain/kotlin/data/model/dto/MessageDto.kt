package data.model.dto

import domain.models.Reaction
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import utils.KotlinLocalDateTimeSerializer

@Serializable
data class MessageDto(
    val id: String,
    val senderId: String? = null,
    val content: String? = null,
    @Serializable(with = KotlinLocalDateTimeSerializer::class)
    val timeStamp: LocalDateTime? = null,
    val chatRoomId: String? = null,
    val isRead: Boolean? = false,
    val edited: Boolean? = false,
    @Contextual val reactions: List<Reaction>? = null
)
