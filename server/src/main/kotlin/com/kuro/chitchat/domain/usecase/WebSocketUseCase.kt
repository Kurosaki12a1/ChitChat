package com.kuro.chitchat.domain.usecase

import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.repository.ChatRepository
import io.ktor.server.application.ApplicationCall
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.serialization.json.Json
import utils.now

class WebSocketUseCase(private val chatRepository: ChatRepository) {
    private suspend fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession) {
        chatRepository.addWebSocketSession(userId, webSocketSession)
    }

    private suspend fun removeWebSocketSession(userId: String) {
        chatRepository.removeWebSocketSession(userId)
    }

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
                    chatRepository.addRoomToMember(userId, message.chatRoomId)
                    chatRepository.sendMessage(message)
                    chatRepository.broadcastMessageToRoom(message.chatRoomId, message)
                }
            } catch (e: ClosedReceiveChannelException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                removeWebSocketSession(userId)
            }
        }
    }
}