package com.kuro.chitchat.routes

import com.kuro.chitchat.domain.model.Endpoint
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.unauthorizedRoute() {
    get(Endpoint.Unauthorized.path) {
        call.respond(
            message = "Not Authorized.",
            status = HttpStatusCode.Unauthorized
        )
    }
}