package com.kuro.chitchat.routes

import com.kuro.chitchat.domain.model.Endpoint
import data.model.dto.ApiResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import utils.AUTH_SESSION

fun Route.authorizedRoute() {
    authenticate(AUTH_SESSION) {
        get(Endpoint.Authorized.path) {
            call.respond(
                message = ApiResponse(success = true),
                status = HttpStatusCode.OK
            )
        }
    }
}