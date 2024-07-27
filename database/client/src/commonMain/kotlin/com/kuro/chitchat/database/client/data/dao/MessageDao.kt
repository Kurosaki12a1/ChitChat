package com.kuro.chitchat.database.client.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuro.chitchat.database.client.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun sendMessage(message: MessageEntity)

    @Insert
    suspend fun updateMessage(message: MessageEntity)

    @Delete
    suspend fun deleteMessage(message: MessageEntity)

    @Query("SELECT * FROM Message where id = :messageId")
    fun getMessageById(messageId: String): Flow<MessageEntity?>

    @Query("SELECT * FROM Message where chatRoomId= :chatRoomId")
    fun getMessageByChatRoom(chatRoomId: String): Flow<List<MessageEntity>>
}