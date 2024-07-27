package com.kuro.chitchat.data.mapper

import com.kuro.chitchat.database.server.entity.ChatRoom
import com.kuro.chitchat.database.server.entity.Message
import com.kuro.chitchat.database.server.entity.User
import data.model.dto.ChatRoomDto
import data.model.dto.MessageDto
import data.model.dto.UserDto
import domain.models.Reaction
import domain.models.RoomType
import domain.models.StatusUser
import utils.now
import com.kuro.chitchat.database.server.entity.Reaction as ReactionServer

fun ReactionServer.toDto() = Reaction(
    userId = this.userId,
    emoCode = this.emoCode
)

fun Reaction.toServer() = ReactionServer(
    userId = this.userId,
    emoCode = this.emoCode
)

fun Message.toDTO() = MessageDto(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    edited = this.edited,
    reactions = this.reactions.map { it.toDto() }
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
    roomType = this.roomType,
    roomPhoto = this.roomPhoto
)

fun MessageDto.toEntity() = Message(
    id = this.id,
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: now(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false,
    edited = this.edited ?: false,
    reactions = this.reactions?.map { it.toServer() } ?: listOf()
)

fun UserDto.toEntity() = User(
    id = this.id ?: "",
    userId = this.userId ?: "",
    name = this.name ?: "",
    emailAddress = this.emailAddress ?: "",
    profilePhoto = this.profilePhoto,
    lastActive = this.lastActive ?: now(),
    status = this.status ?: StatusUser.ONLINE.status
)

fun ChatRoomDto.toEntity() = ChatRoom(
    id = this.id,
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toEntity(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType ?: RoomType.NORMAL.type,
    createdBy = "",
    createdTime = this.createdTime ?: now(),
    updatedTime = this.updatedTime ?: now(),
    roomPhoto = this.roomPhoto
)
