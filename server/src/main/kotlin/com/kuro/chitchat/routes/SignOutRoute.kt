package com.kuro.chitchat.routes

import com.kuro.chitchat.data.model.dto.ApiResponse
import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.model.UserSession
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.sessions
import utils.AUTH_SESSION

fun Route.signOutRoute() {
    authenticate(AUTH_SESSION) {
        get(Endpoint.SignOut.path) {
            call.sessions.clear<UserSession>()
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }
    }
}