package data.repository.remote

import data.model.dto.MessageDto
import domain.models.MessageModel
import domain.repository.remote.SocketRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import utils.HOST
import utils.SERVER_PORT


/**
 * Implementation of SocketRepository for managing WebSocket connections and messaging.
 *
 * @property client The HttpClient used for WebSocket connections.
 */
class SocketRepositoryImpl(private val client: HttpClient) : SocketRepository {
    private var session: WebSocketSession? = null

    /**
     * Connects to the WebSocket server and establishes a session for the specified user.
     *
     * @param userId The ID of the user connecting to the WebSocket.
     * @return A Flow emitting the WebSocket session.
     */
    override fun connectToWebSocket(userId: String): Flow<WebSocketSession> = channelFlow {
        if (session == null) {
            session = client.webSocketSession {
                url("ws://$HOST:$SERVER_PORT/connect")
                parameter("userId", userId)
            }
        }
        session?.let { send(it) }
    }

    /**
     * Sends a message through the established WebSocket session.
     *
     * @param message The message to be sent.
     */
    override suspend fun sendMessage(message: MessageModel) {
        session?.send(Frame.Text(Json.encodeToString(message)))
    }

    /**
     * Receives messages from the WebSocket server.
     *
     * This function listens for incoming messages on the WebSocket session and
     * emits them as a Flow of MessageDto objects.
     *
     * @return A Flow emitting incoming messages.
     */
    override fun receiveMessages(): Flow<MessageDto> = channelFlow {
        while (isActive) {
            val frame = session?.incoming?.receive()
            if (frame != null && frame is Frame.Text) {
                val message = Json.decodeFromString<MessageDto>(frame.readText())
                send(message)
            }
        }
    }

    /**
     * Disconnects from the WebSocket server and closes the session.
     *
     * The server will automatically close the session after a timeout period (30 seconds).
     */
    override suspend fun disconnectToWebsocket() {
        // Don't need check at server because server auto close after TIMEOUT = 30 seconds
        session?.close()
        session = null
    }
}