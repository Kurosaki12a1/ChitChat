package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.model.Member
import com.kuro.chitchat.domain.repository.ChatRepository
import domain.model.RoomType
import domain.model.WebSocketModel
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import utils.now

class WebSocketUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(userId: String, session: WebSocketSession) {
        session.apply {
            val member = Member(sender = userId, webSocket = session)
            chatRepository.connectToSocket(member)
            try {
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val receivedText = frame.readText()
                        when (val action = Json.decodeFromString<WebSocketModel>(receivedText)) {
                            is WebSocketModel.SendMessageToUser -> {
                                handleSendMessageToUser(
                                    userId,
                                    action.receiverId,
                                    action.messageContent
                                )
                            }

                            is WebSocketModel.SendMessageToRoom -> {
                                handleSendMessageToRoom(
                                    userId,
                                    action.roomId,
                                    action.messageContent
                                )
                            }

                            is WebSocketModel.CreateRoomAndInvite -> {
                                handleCreateRoomAndInvite(
                                    userId,
                                    action.roomName,
                                    action.roomType,
                                    action.participants
                                )
                            }

                            is WebSocketModel.JoinRoom -> {
                                handleJoinRoom(userId, action.roomId)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                val chatRooms = chatRepository.findAllChatRoomsForUser(userId)
                chatRooms.forEach { chatRepository.disconnectFromSocket(userId, it) }
            }
        }
    }

    private suspend fun handleSendMessageToUser(
        senderId: String,
        receiverId: String,
        messageContent: String
    ) {
        val roomName = listOf(senderId, receiverId).sorted().joinToString("-")
        var chatRoom = chatRepository.findChatRoomByName(roomName)

        if (chatRoom == null) {
            chatRoom = chatRepository.createChatRoom(
                roomName = roomName,
                roomType = RoomType.NORMAL,
                participants = listOf(senderId, receiverId),
            )
        }

        chatRepository.updateMemberRoom(senderId, chatRoom.id.toString())
        chatRepository.updateMemberRoom(receiverId, chatRoom.id.toString())

        val message = Message(
            senderId = senderId,
            content = messageContent,
            chatRoomId = chatRoom.id.toString(),
            timeStamp = now()
        )

        chatRepository.sendMessage(message)
        chatRepository.updateLastMessage(chatRoom.id.toString(), message)

        val members = chatRepository.getMembersForRoom(chatRoom.id.toString())
        members.forEach { member ->
            member.webSocket.send(Frame.Text(Json.encodeToString(Message.serializer(), message)))
        }
    }

    private suspend fun handleSendMessageToRoom(
        senderId: String,
        roomId: String,
        messageContent: String
    ) {
        
        val message = Message(
            senderId = senderId,
            content = messageContent,
            chatRoomId = roomId,
            timeStamp = now()
        )

        chatRepository.sendMessage(message)
        chatRepository.updateLastMessage(roomId, message)

        val members = chatRepository.getMembersForRoom(roomId)
        members.forEach { member ->
            member.webSocket.send(Frame.Text(Json.encodeToString(Message.serializer(), message)))
        }
    }

    private suspend fun handleCreateRoomAndInvite(
        senderId: String,
        roomName: String,
        roomType: RoomType,
        participantIds: List<String>
    ) {
        val chatRoom = chatRepository.createChatRoom(
            roomName = roomName,
            roomType = roomType,
            participants = participantIds + senderId
        )
        val welcomeMessage = Message(
            senderId = "System",
            content = "Room $roomName created by $senderId",
            chatRoomId = chatRoom.id.toString(),
            timeStamp = now()
        )
        chatRepository.sendMessage(welcomeMessage)
        chatRepository.updateLastMessage(chatRoom.id.toString(), welcomeMessage)

        participantIds.forEach { participantId ->
            chatRepository.updateMemberRoom(participantId, chatRoom.id.toString())
        }

        val members = chatRepository.getMembersForRoom(chatRoom.id.toString())
        members.forEach { member ->
            member.webSocket.send(
                Frame.Text(
                    Json.encodeToString(
                        Message.serializer(),
                        welcomeMessage
                    )
                )
            )
        }
    }

    private fun handleJoinRoom(id: String, roomId: String) {
        chatRepository.updateMemberRoom(id, roomId)
    }
}