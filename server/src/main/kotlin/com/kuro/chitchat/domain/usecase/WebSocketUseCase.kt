package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.json.Json
import org.bson.types.ObjectId

/**
 * Use case class for managing WebSocket connections and handling incoming messages.
 *
 * @property chatRepository The repository for managing chat-related data and operations.
 */
class WebSocketUseCase(private val chatRepository: ChatRepository) {
    /**
     * Adds a WebSocket session for a specific user.
     *
     * @param userId The ID of the user.
     * @param webSocketSession The WebSocket session to be added.
     */
    private fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession) {
        chatRepository.addWebSocketSession(userId, webSocketSession)
    }

    /**
     * Removes the WebSocket session for a specific user.
     *
     * @param userId The ID of the user whose session is to be removed.
     */
    private fun removeWebSocketSession(userId: String) {
        chatRepository.removeWebSocketSession(userId)
    }

    /**
     * Handles incoming messages for a WebSocket session.
     *
     * This function listens to incoming WebSocket frames, processes text frames, and
     * performs operations such as sending messages and broadcasting them to the room.
     *
     * @param userId The ID of the user associated with the WebSocket session.
     * @param webSocketSession The WebSocket session to handle incoming messages for.
     */
    suspend fun handleIncomingMessages(
        userId: String,
        webSocketSession: WebSocketSession
    ) {
        webSocketSession.apply {
            try {
                addWebSocketSession(userId, webSocketSession)
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val receivedText = frame.readText()
                    val message = Json.decodeFromString<Message>(receivedText)
                    val updatedMessage = message.copy(id = ObjectId().toHexString())
                    chatRepository.addRoomToMember(userId, updatedMessage.chatRoomId)
                    chatRepository.sendMessage(updatedMessage)
                    chatRepository.broadcastMessageToRoom(updatedMessage.chatRoomId, updatedMessage)
                }
            } catch (e: ClosedReceiveChannelException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                // If server does not get any respond from client, auto close connect
                // Ping every 15 seconds for check user, and time out 30 seconds.
                // More detail see at class Sockets Plugins
                removeWebSocketSession(userId)
            }
        }
    }
}