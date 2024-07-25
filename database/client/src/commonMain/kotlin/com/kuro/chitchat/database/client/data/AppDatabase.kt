package com.kuro.chitchat.database.client.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.kuro.chitchat.database.client.data.dao.ChatRoomDao
import com.kuro.chitchat.database.client.data.dao.MessageDao
import com.kuro.chitchat.database.client.data.dao.UserDao
import com.kuro.chitchat.database.client.entity.ChatRoomEntity
import com.kuro.chitchat.database.client.entity.MessageEntity
import com.kuro.chitchat.database.client.entity.ReactionEntity
import com.kuro.chitchat.database.client.entity.UserEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(entities = [ChatRoomEntity::class, MessageEntity::class, UserEntity::class], version = 1)
@TypeConverters(
    LocalDateTimeConverter::class,
    ListStringConverter::class,
    MessageConverter::class,
    ReactionConverter::class
)
abstract class AppDatabase : RoomDatabase(), DB {
    abstract fun chatRoomDao(): ChatRoomDao
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao

    override fun clearAllTables() {
        super.clearAllTables()
    }

}

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}

class ListStringConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString<List<String>>(value)
    }

    @TypeConverter
    fun fromList(data: List<String>): String {
        return Json.encodeToString(data)
    }
}

class MessageConverter {
    @TypeConverter
    fun fromString(value: String): MessageEntity {
        return Json.decodeFromString<MessageEntity>(value)
    }

    @TypeConverter
    fun fromMessage(data: MessageEntity?): String {
        return Json.encodeToString(data)
    }
}

class ReactionConverter {
    @TypeConverter
    fun fromString(value: String?): List<ReactionEntity> {
        if (value == null) return emptyList()
        return Json.decodeFromString<List<ReactionEntity>>(value)
    }

    @TypeConverter
    fun fromList(data: List<ReactionEntity>?): String {
        if (data == null) return ""
        return Json.encodeToString(data)
    }
}


internal const val dbFileName = "chit-chat.db"

// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}
