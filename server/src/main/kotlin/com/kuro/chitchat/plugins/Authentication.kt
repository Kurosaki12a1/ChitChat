package com.kuro.chitchat.plugins

import com.kuro.chitchat.domain.model.Endpoint
import com.kuro.chitchat.domain.model.UserSession
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.session
import io.ktor.server.response.respondRedirect
import utils.AUTH_SESSION

fun Application.configureAuth() {
    install(Authentication) {
        session<UserSession>(name = AUTH_SESSION) {
            validate { session -> session }
            challenge { call.respondRedirect(Endpoint.Unauthorized.path) }
        }
    }
}