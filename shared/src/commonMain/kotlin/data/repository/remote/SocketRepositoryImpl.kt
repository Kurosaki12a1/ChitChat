package data.repository.remote

import data.model.dto.MessageDto
import domain.repository.remote.SocketRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.HOST
import utils.SERVER_PORT

class SocketRepositoryImpl(private val client: HttpClient) : SocketRepository {
    override fun connectToWebSocket(userId: String): Flow<WebSocketSession> = channelFlow {
        client.webSocketSession {
            url("ws://$HOST:$SERVER_PORT/connect")
            parameter("userId", userId)
        }.let { session ->
            send(session)
        }
    }

    override suspend fun sendMessage(session: WebSocketSession, message: MessageDto) {
        session.send(Frame.Text(Json.encodeToString(message)))
    }

    override fun receiveMessages(session: WebSocketSession): Flow<MessageDto> = channelFlow {
        while (isActive) {
            val frame = session.incoming.receive()
            if (frame is Frame.Text) {
                val message = Json.decodeFromString<MessageDto>(frame.readText())
                send(message)
            }
        }
    }
}