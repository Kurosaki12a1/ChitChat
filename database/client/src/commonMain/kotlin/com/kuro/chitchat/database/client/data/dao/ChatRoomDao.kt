package com.kuro.chitchat.database.client.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuro.chitchat.database.client.entity.ChatRoomEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createChatRoom(chatRoom: ChatRoomEntity)

    @Insert
    suspend fun updateChatRoom(chatRoom: ChatRoomEntity)

    @Delete
    suspend fun deleteChatRoom(chatRoom: ChatRoomEntity)

    @Query("SELECT * FROM ChatRoom where id = :roomId")
    fun getChatRoomById(roomId: String): Flow<ChatRoomEntity?>

    @Query("SELECT * FROM ChatRoom")
    fun getAllChatRooms(): Flow<List<ChatRoomEntity>>
}