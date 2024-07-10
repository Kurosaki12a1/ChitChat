package domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class RoomType {
    NORMAL, SECRET, BROADCAST
}