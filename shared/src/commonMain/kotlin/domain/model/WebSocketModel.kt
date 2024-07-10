package domain.model

import kotlinx.serialization.Serializable

@Serializable
sealed class WebSocketModel {
    @Serializable
    data class SendMessageToUser(val receiverId: String, val messageContent: String) : WebSocketModel()

    @Serializable
    data class SendMessageToRoom(val roomId: String, val messageContent: String) : WebSocketModel()

    @Serializable
    data class JoinRoom(val roomId: String) : WebSocketModel()

    @Serializable
    data class CreateRoomAndInvite(
        val roomName: String,
        val roomType: RoomType,
        val participants: List<String>
    ) : WebSocketModel()
}