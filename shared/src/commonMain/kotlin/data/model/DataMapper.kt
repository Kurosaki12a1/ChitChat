package data.model

import data.model.dto.ChatRoomDto
import data.model.dto.MessageDto
import data.model.dto.UserDto
import data.model.entity.ChatRoomEntity
import data.model.entity.MessageEntity
import data.model.entity.UserEntity
import domain.model.ChatRoomModel
import domain.model.MessageModel
import domain.model.StatusUser
import domain.model.UserModel
import utils.now

fun MessageModel.toEntity() = MessageEntity(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    reactions = this.reactions,
    edited = this.edited
)

fun UserModel.toEntity() = UserEntity(
    userId = this.userId,
    name = this.name,
    profilePhoto = this.profilePhoto,
    emailAddress = this.emailAddress,
    lastActive = this.lastActive,
    status = this.status
)

fun ChatRoomModel.toEntity() = ChatRoomEntity(
    id = this.id,
    roomName = this.roomName,
    participants = this.participants,
    lastMessage = this.lastMessage?.toEntity(),
    unReadCount = this.unReadCount,
    roomType = this.roomType,
    createdBy = this.createdBy,
    createdTime = this.createdTime,
    updatedTime = this.updatedTime
)

fun MessageEntity.toModel() = MessageModel(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    edited = this.edited,
    reactions = this.reactions
)

fun UserEntity.toModel() = UserModel(
    userId = this.userId,
    name = this.name,
    profilePhoto = profilePhoto ?: "",
    emailAddress = this.emailAddress,
    lastActive = this.lastActive,
    status = this.status
)

fun ChatRoomEntity.toModel() = ChatRoomModel(
    id = this.id,
    roomName = this.roomName,
    participants = this.participants,
    lastMessage = this.lastMessage?.toModel(),
    unReadCount = this.unReadCount,
    roomType = this.roomType,
    createdBy = this.createdBy,
    createdTime = this.createdTime,
    updatedTime = this.updatedTime
)


fun MessageDto.toModel() = MessageModel(
    id = this.id,
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: now(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false
)

fun UserDto.toModel() = UserModel(
    userId = this.userId ?: "",
    name = this.name ?: "",
    profilePhoto = this.profilePhoto ?: "",
    emailAddress = this.emailAddress ?: "",
    lastActive = this.lastActive ?: now(),
    status = this.status ?: StatusUser.OFFLINE
)

fun ChatRoomDto.toModel() = ChatRoomModel(
    id = this.id,
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toModel(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType,
    createdTime = this.createdTime ?: now(),
    updatedTime = this.updatedTime ?: now(),
    createdBy = this.createdBy ?: ""
)