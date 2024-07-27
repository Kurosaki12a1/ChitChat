package domain.models

import kotlinx.serialization.Serializable

@Serializable
enum class RoomType(val type: String) {
    NORMAL("normal"),
    SECRET("secret"),
    BROADCAST("broadcast");
}