package com.kuro.chitchat

import com.kuro.chitchat.plugins.configureAuth
import com.kuro.chitchat.plugins.configureKoin
import com.kuro.chitchat.plugins.configureMonitoring
import com.kuro.chitchat.plugins.configureRouting
import com.kuro.chitchat.plugins.configureSerialization
import com.kuro.chitchat.plugins.configureSession
import utils.SERVER_PORT
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import utils.HOST

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = HOST, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureAuth()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSession()
}