package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "Message")
data class MessageEntity(
    // Id of message
    @PrimaryKey val id: String,

    // Id from who sent message
    val senderId: String,
    val content: String,
    val timeStamp: LocalDateTime,
    val chatRoomId: String,
    val isRead: Boolean,
    val edited: Boolean,
    val reactions: Map<String, Int>
)
