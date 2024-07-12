package data.repository.remote

import data.model.dto.ChatRoomDto
import data.model.dto.HistoryChatRoomDto
import domain.model.ChatRoomModel
import domain.model.PrivateChatRequest
import domain.repository.remote.ChatRoomRemoteRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import io.ktor.http.path

class ChatRoomRemoteRepositoryImpl(private val client: HttpClient) : ChatRoomRemoteRepository {
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

    override suspend fun createPublicChatRoom(
        room: ChatRoomModel,
        creatorId: String
    ): ChatRoomDto? {
        return try {
            val response = client.post {
                url {
                    path("/chat/public/start")
                    parameter("creatorId", creatorId)
                    setBody(room)
                }
            }
            response.body<ChatRoomDto>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

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