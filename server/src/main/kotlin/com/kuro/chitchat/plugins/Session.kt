package com.kuro.chitchat.plugins

import com.kuro.chitchat.domain.model.UserSession
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.directorySessionStorage
import io.ktor.util.hex
import java.io.File

fun Application.configureSession() {
    install(Sessions) {
        val secretEncryptKey = hex("chit-chat-sessions-ktor-server")
        val secretAuthKey = hex("230719972502200327042009")
        cookie<UserSession>(
            name = "USER_SESSION",
            storage = directorySessionStorage(File(".sessions"))
        ) {
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }
}