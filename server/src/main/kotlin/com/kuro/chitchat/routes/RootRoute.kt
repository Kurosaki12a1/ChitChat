package com.kuro.chitchat.routes

import com.kuro.chitchat.domain.model.Endpoint
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.rootRoute() {
    get(Endpoint.Root.path) {
        call.respondText("Welcome to Ktor Server!")
    }
}