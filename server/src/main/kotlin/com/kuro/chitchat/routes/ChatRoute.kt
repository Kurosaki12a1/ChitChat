package com.kuro.chitchat.routes

import com.kuro.chitchat.data.mapper.toDTO
import com.kuro.chitchat.domain.usecase.CreateOrGetChatRoomUseCase
import com.kuro.chitchat.domain.usecase.CreatePublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.GetChatHistoryUseCase
import com.kuro.chitchat.domain.usecase.GetChatRoomUserUseCase
import com.kuro.chitchat.domain.usecase.JoinPublicChatRoomUseCase
import com.kuro.chitchat.domain.usecase.WebSocketUseCase
import data.model.dto.HistoryChatRoomDto
import domain.models.ChatRoomModel
import domain.models.PrivateChatRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import org.koin.java.KoinJavaComponent.inject
import utils.AUTH_SESSION

fun Route.chatRoute() {
    authenticate(AUTH_SESSION) {

        webSocket("/connect") {
            val userId = call.parameters["userId"] ?: return@webSocket close(
                CloseReason(
                    CloseReason.Codes.VIOLATED_POLICY,
                    "User ID is required"
                )
            )
            val useCase by inject<WebSocketUseCase>(WebSocketUseCase::class.java)
            useCase.handleIncomingMessages(userId, this)
        }

        post("/chat/private/start") {
            val request = call.receive<PrivateChatRequest>()
            if (request.sender.userId.isBlank() || request.receiver.userId.isBlank()) {
                call.respond(
                    status = HttpStatusCode.BadRequest,
                    message = "Sender or Receiver ID is missing"
                )
                return@post
            }
            val useCase by inject<CreateOrGetChatRoomUseCase>(CreateOrGetChatRoomUseCase::class.java)
            val chatRoom = useCase(
                sender = request.sender,
                receiver = request.receiver,
                type = request.roomType,
                firstMessage = request.firstMessage
            )
            call.respond(
                status = HttpStatusCode.OK,
                message = chatRoom.toDTO()
            )
        }

        post("/chat/public/start") {
            val request = call.receive<ChatRoomModel>()
            val creatorId = call.parameters["creatorId"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "Creator ID is required"
            )
            val useCase by inject<CreatePublicChatRoomUseCase>(CreatePublicChatRoomUseCase::class.java)
            val room = useCase(request, creatorId)
            call.respond(
                status = HttpStatusCode.OK,
                message = room.toDTO()
            )
        }

        post("/chat/public/join") {
            val roomId = call.parameters["roomId"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "Room ID is required"
            )
            val userId = call.parameters["userId"] ?: return@post call.respond(
                HttpStatusCode.BadRequest,
                "User ID is required"
            )
            val useCase by inject<JoinPublicChatRoomUseCase>(JoinPublicChatRoomUseCase::class.java)
            val roomJoined = useCase(roomId, userId)
            if (roomJoined != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = roomJoined.toDTO()
                )
            } else {
                call.response.status(HttpStatusCode.BadGateway)
            }
        }

        get("/chat/history") {
            val roomId = call.parameters["roomId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Room ID is required"
            )
            val useCase by inject<GetChatHistoryUseCase>(GetChatHistoryUseCase::class.java)
            val messages = useCase(roomId)
            call.respond(
                status = HttpStatusCode.OK,
                message = HistoryChatRoomDto(
                    roomId = roomId,
                    messages = messages.map { it.toDTO() })
            )
        }

        get("/chat/rooms") {
            val userId = call.parameters["userId"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "User ID is required"
            )
            val useCase by inject<GetChatRoomUserUseCase>(GetChatRoomUserUseCase::class.java)
            val listRooms = useCase(userId)
            call.respond(
                status = HttpStatusCode.OK,
                message = listRooms.map { it.toDTO() }
            )
        }

    }

}

/****
authenticate(AUTH_SESSION) {

get(Endpoint.ChatRoom.path) {
val request = call.receive<CheckRoomRequest>()
if (request.sender.userId.isEmpty()) {
call.respond(status = HttpStatusCode.BadRequest, message = ChatRoomDto(id = ""))
return@get
}
if (request.receiver.userId.isEmpty()) {
call.respond(status = HttpStatusCode.BadRequest, message = ChatRoomDto(id = ""))
return@get
}

val useCase by inject<CheckAndGetChatRoomUseCase>(CheckAndGetChatRoomUseCase::class.java)
useCase(request.sender, request.receiver).collectLatest { response ->
call.respond(
status = HttpStatusCode.OK,
message = response
)
}
}
 */
/**
 * This is for create chat room with multi people
 *//*

    post(Endpoint.ChatRoom.path) {
        val request = call.receive<ChatRoomModel>()
        if (request.roomName.isBlank()) {
            call.response.status(HttpStatusCode.BadRequest)
            return@post
        }
        val useCase by inject<CreateChatRoomUseCase>(CreateChatRoomUseCase::class.java)
        useCase(room = request).collectLatest { response ->
            call.respond(
                status = HttpStatusCode.OK,
                message = response
            )
        }
    }
    */
/**
 * This is for get history of room
 *//*

    get(Endpoint.GetChatHistory.path) {
        val roomId = call.parameters["roomId"]
        if (roomId == null) {
            call.response.status(HttpStatusCode.BadRequest)
            return@get
        }
        val useCase by inject<GetChatHistoryUseCase>(GetChatHistoryUseCase::class.java)
        useCase(roomId = roomId).collectLatest { response ->
            call.respond(
                status = HttpStatusCode.OK,
                message = response
            )
        }
    }
    */
/**
 * This is for join room only
 *//*

    post(Endpoint.JoinRoom.path) {
        val roomId = call.parameters["roomId"]
        if (roomId == null) {
            call.response.status(HttpStatusCode.BadRequest)
            return@post
        }
        val request = call.receive<UserModel>()
        val useCase by inject<JoinRoomUseCase>(JoinRoomUseCase::class.java)
        useCase(userModel = request, roomId = roomId).collectLatest { response ->
            if (response != null) {
                call.respond(
                    status = HttpStatusCode.OK,
                    message = response
                )
            } else {
                call.respond(status = HttpStatusCode.BadRequest, message = ChatRoomDto(id = ""))
            }
        }
    }
    webSocket(Endpoint.Chat.path) {
        val userId = call.request.queryParameters["userId"] ?: return@webSocket close(
            CloseReason(
                CloseReason.Codes.CANNOT_ACCEPT, "No userId provided"
            )
        )
        val useCase by inject<WebSocketUseCase>(WebSocketUseCase::class.java)
        useCase.invoke(userId = userId, session = this)
    }
}***/
