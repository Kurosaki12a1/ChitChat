package com.kuro.chitchat.util

fun generateChatRoomId(senderId: String, receivedId: String): String {
    return listOf(senderId, receivedId).sorted().joinToString("-")
}

fun generateChatRoomName(senderName: String, receiverName: String): String {
    return listOf(senderName, receiverName).sorted().joinToString(", ")
}

fun generateChatRoomName(participants: List<String>): String {
    return participants.joinToString(", ")
}
