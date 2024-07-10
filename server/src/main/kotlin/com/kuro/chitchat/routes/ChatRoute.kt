package com.kuro.chitchat.routes

import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.usecase.WebSocketUseCase
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.close
import org.koin.java.KoinJavaComponent.inject
import utils.AUTH_SESSION

fun Route.chatRoute() {
    authenticate(AUTH_SESSION) {
        post(Endpoint.CreateChat.path) {

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
    }

}