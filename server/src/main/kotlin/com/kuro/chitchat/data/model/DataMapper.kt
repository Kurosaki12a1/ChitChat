package com.kuro.chitchat.data.model

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.data.model.entity.User
import data.model.dto.ChatRoomDto
import data.model.dto.MessageDto
import data.model.dto.UserDto
import kotlinx.datetime.LocalDateTime
import org.bson.types.ObjectId

fun String.toObjectId(): ObjectId = ObjectId(this)

fun Message.toDTO() = MessageDto(
    id = this.id.toHexString(),
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead
)

fun User.toDTO() = UserDto(
    id = this.id.toHexString(),
    userId = this.userId,
    name = this.name,
    emailAddress = this.emailAddress,
    profilePhoto = this.profilePhoto,
    lastActive = this.lastActive
)

fun ChatRoom.toDTO() = ChatRoomDto(
    id = this.id.toHexString(),
    roomName = this.roomName,
    participants = this.participants,
    lastMessage = this.lastMessage?.toDTO(),
    unReadCount = this.unReadCount
)

fun MessageDto.toEntity() = Message(
    id = this.id.toObjectId(),
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: defaultLocalDateTime(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false
)

fun UserDto.toEntity() = User(
    id = this.id.toObjectId(),
    userId = this.userId ?: "",
    name = this.name ?: "",
    emailAddress = this.emailAddress ?: "",
    profilePhoto = this.profilePhoto,
    lastActive = this.lastActive ?: defaultLocalDateTime()
)

fun ChatRoomDto.toEntity() = ChatRoom(
    id = this.id.toObjectId(),
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toEntity(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType
)

fun defaultLocalDateTime(): LocalDateTime = LocalDateTime(1970, 1, 1, 0, 0, 0)