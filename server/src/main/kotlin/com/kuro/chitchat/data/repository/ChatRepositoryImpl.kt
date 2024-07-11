package com.kuro.chitchat.data.repository

import com.kuro.chitchat.data.model.entity.ChatRoom
import com.kuro.chitchat.data.model.entity.Message
import com.kuro.chitchat.domain.model.Member
import com.kuro.chitchat.domain.repository.ChatRepository
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import utils.now

class ChatRepositoryImpl(database: CoroutineDatabase) : ChatRepository {
    private val chatRooms = database.getCollection<ChatRoom>()
    private val messages = database.getCollection<Message>()
    private val members = mutableMapOf<String, Member>()

    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        chatRooms.insertOne(room)
        return room
    }

    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return chatRooms.findOne(ChatRoom::id eq roomId)
    }

    override suspend fun getMessageForRoom(roomId: String): List<Message> {
        return messages.find(Message::chatRoomId eq roomId).toList()
    }

    override suspend fun sendMessage(message: Message) {
        messages.insertOne(message)
        val room = chatRooms.findOne(ChatRoom::id eq message.chatRoomId)
        if (room != null) {
            val updatedRoom = room.copy(lastMessage = message, updatedTime = now())
            chatRooms.updateOneById(updatedRoom.id, updatedRoom)
        }
    }

    override suspend fun addWebSocketSession(userId: String, webSocketSession: WebSocketSession) {
        val member = Member(sender = userId, webSocket = webSocketSession)
        members[userId] = member
    }

    override suspend fun removeWebSocketSession(userId: String) {
        members.remove(userId)
    }

    override suspend fun getWebSocketSession(userId: String): WebSocketSession? {
        return members[userId]?.webSocket
    }

    override suspend fun broadcastMessageToRoom(roomId: String, message: Message) {
        val participants = chatRooms.findOne(ChatRoom::id eq roomId)?.participants ?: return
        for (participant in participants) {
            members[participant]?.webSocket?.send(Frame.Text(message.content))
        }
    }

    override suspend fun addRoomToMember(userId: String, roomId: String) {
        members[userId]?.chatRooms?.add(roomId)
    }

    override suspend fun addRoomToMembers(roomId: String, memberIds: List<String>) {
        memberIds.forEach { userId ->
            members[userId]?.chatRooms?.add(roomId)
        }
    }

    override suspend fun removeRoomFromMember(userId: String, roomId: String) {
        members[userId]?.chatRooms?.remove(roomId)
    }

    override suspend fun addParticipantToRoom(roomId: String, userId: String): ChatRoom? {
        val room = chatRooms.findOne(ChatRoom::id eq roomId)
        return if (room != null) {
            val updatedRoom = room.copy(participants = room.participants + userId)
            chatRooms.updateOneById(updatedRoom.id, updatedRoom)
            addRoomToMember(userId, roomId)
            updatedRoom
        } else {
            null
        }
    }
}


/*
class ChatRepositoryImpl(
    database: CoroutineDatabase
) : ChatRepository {
    private val chatRoomCollection = database.getCollection<ChatRoom>()
    private val messageCollection = database.getCollection<Message>()
    private val memberSessions = mutableListOf<Member>()

    override suspend fun createChatRoom(room: ChatRoom): ChatRoom {
        return withContext(Dispatchers.IO) {
            chatRoomCollection.insertOne(document = room).wasAcknowledged()
            room.participants.forEach { id ->
                val member = memberSessions.find { it.sender == id }
                member?.chatRooms?.add(room.id.toHexString())
            }
            room
        }
    }

    override suspend fun findChatRoomById(roomId: String): ChatRoom? {
        return chatRoomCollection.findOne(ChatRoom::id eq ObjectId(roomId))
    }

    override suspend fun findChatRoomByName(roomName: String): ChatRoom? {
        return chatRoomCollection.findOne(ChatRoom::roomName eq roomName)
    }

    override suspend fun sendMessage(message: Message) {
        messageCollection.insertOne(message)

        memberSessions.filter { message.chatRoomId in it.chatRooms }.forEach { member ->
            // Encoding message into json string.
            val broadcastMessage = Json.encodeToString(message)
            // Sending message to other socket subscribers.
            member.webSocket.send(Frame.Text(broadcastMessage))
        }
    }

    override suspend fun getMessageForRoom(roomId: String): Flow<List<Message>> = flow {
        try {
            val result =
                messageCollection.find().descendingSort(Message::timeStamp).toList()
                    .filter { it.chatRoomId == roomId }
            emit(result)
        } catch (e: Exception) {
            e.printStackTrace()
            emit(emptyList())
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun updateLastMessage(roomId: String, message: Message) {
        chatRoomCollection.updateOne(
            filter = ChatRoom::id eq ObjectId(roomId),
            update = setValue(ChatRoom::lastMessage, message)
        )
    }

    override suspend fun updateMemberRoom(senderId: String, roomId: String): ChatRoom? {
        val room = chatRoomCollection.findOne(ChatRoom::id eq ObjectId(roomId))
        if (room != null) {
            val updatedParticipants = mutableListOf<String>()
            updatedParticipants.addAll(room.participants)
            updatedParticipants.add(senderId)
            // Update DB
            chatRoomCollection.updateOne(
                filter = ChatRoom::id eq ObjectId(roomId),
                update = setValue(ChatRoom::participants, updatedParticipants)
            )

            // Update Session
            val member = memberSessions.find { it.sender == senderId }
            member?.chatRooms?.add(roomId)
        }
        return room
    }

    override fun connectToSocket(member: Member) {
        if (!memberSessions.contains(member)) {
            memberSessions.add(member)
        }
    }

    override fun disconnectFromSocket(senderId: String, roomId: String) {
        val member = memberSessions.find { it.sender == senderId }
        member?.chatRooms?.remove(roomId)
        if (member?.chatRooms.isNullOrEmpty()) {
            memberSessions.remove(member)
        }
    }

    override fun getMembersForRoom(roomId: String): List<Member> {
        return memberSessions.filter { roomId in it.chatRooms }
    }

    override fun findAllChatRoomsForUser(userId: String): List<String> {
        return memberSessions.find { it.sender == userId }?.chatRooms?.toList() ?: emptyList()
    }

}*/
