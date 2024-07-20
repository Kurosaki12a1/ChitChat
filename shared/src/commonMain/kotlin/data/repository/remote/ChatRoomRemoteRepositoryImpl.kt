package data.repository.remote

import data.model.dto.ChatRoomDto
import data.model.dto.HistoryChatRoomDto
import domain.models.ChatRoomModel
import domain.models.PrivateChatRequest
import domain.repository.remote.ChatRoomRemoteRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.path

/**
 * Implementation of ChatRoomRemoteRepository for handling chat room-related remote operations.
 *
 * @property client The HttpClient used for making HTTP requests.
 */
class ChatRoomRemoteRepositoryImpl(private val client: HttpClient) : ChatRoomRemoteRepository {
    /**
     * Retrieves chat rooms for a specific user.
     *
     * @param userId The ID of the user.
     * @return A list of ChatRoomDto representing the chat rooms of the user.
     */
    override suspend fun getUserChatRooms(userId: String): List<ChatRoomDto> {
        return try {
            val response = client.get {
                url {
                    path("/chat/rooms")
                    parameter("userId", userId)
                }
            }
            response.body<List<ChatRoomDto>>()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    /**
     * Creates a new public chat room.
     *
     * @param room The ChatRoomModel representing the room to be created.
     * @param creatorId The ID of the user creating the room.
     * @return The created ChatRoomDto, or null if the operation failed.
     */
    override suspend fun createPublicChatRoom(
        room: ChatRoomModel,
        creatorId: String
    ): ChatRoomDto? {
        return try {
            val response = client.post {
                url {
                    path("/chat/public/start")
                    parameter("creatorId", creatorId)
                    contentType(ContentType.Application.Json)
                    setBody(room)
                }
            }
            response.body<ChatRoomDto>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Starts a private chat between users.
     *
     * @param request The PrivateChatRequest containing the necessary information to start the chat.
     * @return The created ChatRoomDto, or null if the operation failed.
     */
    override suspend fun startPrivateChat(request: PrivateChatRequest): ChatRoomDto? {
        return try {
            val response = client.post {
                url {
                    path("/chat/private/start")
                    setBody(request)
                }
            }
            if (response.status == HttpStatusCode.OK) {
                response.body<ChatRoomDto>()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Joins a public chat room.
     *
     * @param roomId The ID of the room to join.
     * @param userId The ID of the user joining the room.
     * @return The joined ChatRoomDto, or null if the operation failed.
     */
    override suspend fun joinPublicChatRoom(roomId: String, userId: String): ChatRoomDto? {
        return try {
            val response = client.post {
                url {
                    path("/chat/public/join")
                    parameter("roomId", roomId)
                    parameter("userId", userId)
                }
            }
            response.body<ChatRoomDto>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    /**
     * Retrieves chat history for a specific chat room.
     *
     * @param roomId The ID of the chat room.
     * @return A HistoryChatRoomDto containing the chat history.
     */
    override suspend fun getChatHistory(roomId: String): HistoryChatRoomDto {
        return try {
            val response = client.get {
                url {
                    path("/chat/history")
                    parameter("roomId", roomId)
                }
            }
            response.body<HistoryChatRoomDto>()
        } catch (e: Exception) {
            e.printStackTrace()
            HistoryChatRoomDto(roomId, emptyList(), e)
        }
    }
}