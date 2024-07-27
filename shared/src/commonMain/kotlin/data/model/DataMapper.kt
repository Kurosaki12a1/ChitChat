package data.model

import com.kuro.chitchat.database.client.entity.ChatRoomEntity
import com.kuro.chitchat.database.client.entity.MessageEntity
import com.kuro.chitchat.database.client.entity.ReactionEntity
import com.kuro.chitchat.database.client.entity.UserEntity
import data.model.dto.ChatRoomDto
import data.model.dto.MessageDto
import data.model.dto.UserDto
import domain.models.ChatRoomModel
import domain.models.MessageModel
import domain.models.Reaction
import domain.models.RoomType
import domain.models.StatusUser
import domain.models.UserModel
import utils.now

fun Reaction.toEntity() = ReactionEntity(
    userId = this.userId,
    emoCode = this.emoCode
)

fun ReactionEntity.toReaction() = Reaction(
    userId = this.userId,
    emoCode = this.emoCode
)

fun MessageModel.toEntity() = MessageEntity(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    reactions = this.reactions.map { it.toEntity() },
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
    updatedTime = this.updatedTime,
    roomPhoto = this.roomPhoto
)

fun MessageEntity.toModel() = MessageModel(
    id = this.id,
    senderId = this.senderId,
    content = this.content,
    timeStamp = this.timeStamp,
    chatRoomId = this.chatRoomId,
    isRead = this.isRead,
    edited = this.edited,
    reactions = this.reactions.map { it.toReaction() }
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
    updatedTime = this.updatedTime,
    roomPhoto = this.roomPhoto
)


fun MessageDto.toModel() = MessageModel(
    id = this.id,
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: now(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false,
    reactions = this.reactions ?: listOf()
)

fun UserDto.toModel() = UserModel(
    userId = this.userId ?: "",
    name = this.name ?: "",
    profilePhoto = this.profilePhoto ?: "",
    emailAddress = this.emailAddress ?: "",
    lastActive = this.lastActive ?: now(),
    status = this.status ?: StatusUser.ONLINE.status
)

fun ChatRoomDto.toModel() = ChatRoomModel(
    id = this.id,
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toModel(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType ?: RoomType.NORMAL.type,
    createdTime = this.createdTime ?: now(),
    updatedTime = this.updatedTime ?: now(),
    createdBy = this.createdBy ?: "",
    roomPhoto = this.roomPhoto
)

fun MessageDto.toMessage() = MessageEntity(
    id = this.id,
    senderId = this.senderId ?: "",
    content = this.content ?: "",
    timeStamp = this.timeStamp ?: now(),
    chatRoomId = this.chatRoomId ?: "",
    isRead = this.isRead ?: false,
    edited = this.edited ?: false,
    reactions = this.reactions?.map { it.toEntity() } ?: listOf()
)

fun UserDto.toUser() = UserEntity(
    userId = this.userId ?: "",
    name = this.name ?: "",
    profilePhoto = this.profilePhoto ?: "",
    emailAddress = this.emailAddress ?: "",
    lastActive = this.lastActive ?: now(),
    status = this.status ?: StatusUser.ONLINE.status
)

fun ChatRoomDto.toChatRoom() = ChatRoomEntity(
    id = this.id,
    roomName = this.roomName ?: "",
    participants = this.participants ?: listOf(),
    lastMessage = this.lastMessage?.toMessage(),
    unReadCount = this.unReadCount ?: 0,
    roomType = this.roomType ?: RoomType.NORMAL.type,
    createdTime = this.createdTime ?: now(),
    updatedTime = this.updatedTime ?: now(),
    createdBy = this.createdBy ?: "",
    roomPhoto = this.roomPhoto
)