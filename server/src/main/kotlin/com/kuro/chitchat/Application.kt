package com.kuro.chitchat

import com.kuro.chitchat.plugins.configureAuth
import com.kuro.chitchat.plugins.configureKoin
import com.kuro.chitchat.plugins.configureMonitoring
import com.kuro.chitchat.plugins.configureRouting
import com.kuro.chitchat.plugins.configureSerialization
import com.kuro.chitchat.plugins.configureSession
import com.kuro.chitchat.plugins.configureSockets
import com.kuro.chitchat.plugins.configureStatusPage
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import utils.HOST
import utils.SERVER_PORT

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = HOST, module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureKoin()
    configureAuth()
    configureSockets()
    configureMonitoring()
    configureSession()
    configureStatusPage()
    configureRouting()
    configureSerialization()
}