package com.kuro.chitchat.data.model

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.data.model.entity.User
import data.model.dto.ChatRoomDto
import data.model.dto.MessageDto
import data.model.dto.UserDto
import domain.model.StatusUser
import utils.now

fun Message.toDTO() = MessageDto(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    edited = this.edited,
    reactions = this.reactions
)

fun User.toDTO() = UserDto(
    id = this.id,
    userId = this.userId,
    name = this.name,
    emailAddress = this.emailAddress,
    profilePhoto = this.profilePhoto,
    lastActive = this.lastActive,
    status = this.status
)

fun ChatRoom.toDTO() = ChatRoomDto(
    id = this.id,
    roomName = this.roomName,
    participants = this.participants,
    lastMessage = this.lastMessage?.toDTO(),
    unReadCount = this.unReadCount,
    createdTime = this.createdTime,
    updatedTime = this.updatedTime,
    createdBy = this.createdBy,
    roomType = this.roomType
)

fun MessageDto.toEntity() = Message(
    id = this.id,
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: now(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false,
    edited = this.edited ?: false,
    reactions = this.reactions ?: emptyMap()
)

fun UserDto.toEntity() = User(
    id = this.id,
    userId = this.userId ?: "",
    name = this.name ?: "",
    emailAddress = this.emailAddress ?: "",
    profilePhoto = this.profilePhoto,
    lastActive = this.lastActive ?: now(),
    status = StatusUser.ONLINE
)

fun ChatRoomDto.toEntity() = ChatRoom(
    id = this.id,
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toEntity(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType,
    createdBy = "",
    createdTime = this.createdTime ?: now(),
    updatedTime = this.updatedTime ?: now()
)
