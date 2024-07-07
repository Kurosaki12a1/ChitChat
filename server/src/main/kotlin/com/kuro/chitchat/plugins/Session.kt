package com.kuro.chitchat.plugins

import com.kuro.chitchat.domain.model.UserSession
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.sessions.SessionTransportTransformerEncrypt
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.directorySessionStorage
import io.ktor.util.hex
import utils.DOMAIN
import utils.USER_SESSION
import java.io.File
import java.time.Duration

fun Application.configureSession() {
    install(Sessions) {
        val secretEncryptKey = hex("d6e8cc160c9b7f5e2f1f1aa4b33cb8d7")
        val secretAuthKey = hex("a7f99cb90000b710b5cb305d085096e9")
        cookie<UserSession>(
            name = USER_SESSION,
            storage = directorySessionStorage(File(".sessions"))
        ) {
            cookie.maxAgeInSeconds = Duration.ofDays(1).seconds
            cookie.httpOnly = true
            cookie.domain = DOMAIN
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretAuthKey))
        }
    }
}