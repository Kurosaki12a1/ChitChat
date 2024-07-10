package com.kuro.chitchat.domain.model

import io.ktor.websocket.WebSocketSession

data class Member(
    // userId of sent member
    val sender: String,
    val webSocket: WebSocketSession,
    val chatRooms: MutableSet<String> = mutableSetOf()
)