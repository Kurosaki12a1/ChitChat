package com.kuro.chitchat.plugins

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.pingPeriod
import io.ktor.server.websocket.timeout
import java.time.Duration


/**
 * Configures the WebSockets feature for the Ktor application.
 */
fun Application.configureSockets() {
    install(WebSockets) {
        /**
         * Sets the interval at which ping frames are sent to the client.
         * This is used to keep the WebSocket connection alive.
         */
        pingPeriod = Duration.ofSeconds(15)
        /**
         * Sets the timeout duration for the connection.
         * If a pong frame is not received within this period after sending a ping, the connection is closed.
         */
        timeout = Duration.ofSeconds(30)
        /**
         * Sets the maximum size of a frame that can be received.
         * Any frame larger than this size will be discarded.
         */
        maxFrameSize = Long.MAX_VALUE
        /**
         * Specifies whether masking is enabled for frames sent to the client.
         * Masking is typically enabled for security reasons, but can be disabled for performance improvements.
         */
        masking = false
    }
}