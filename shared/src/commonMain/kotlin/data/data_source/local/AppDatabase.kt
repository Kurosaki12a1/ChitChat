package data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import data.data_source.local.dao.ChatRoomDao
import data.data_source.local.dao.MessageDao
import data.data_source.local.dao.UserDao
import data.model.entity.ChatRoomEntity
import data.model.entity.MessageEntity
import data.model.entity.UserEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Database(entities = [ChatRoomEntity::class, MessageEntity::class, UserEntity::class], version = 1)
@TypeConverters(
    LocalDateTimeConverter::class,
    ListStringConverter::class,
    MessageConverter::class,
    MapStringIntConverter::class
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

class MapStringIntConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Int> {
        return Json.decodeFromString<Map<String, Int>>(value)
    }

    @TypeConverter
    fun fromMap(data: Map<String, Int>?): String {
        return Json.encodeToString(data)
    }
}


internal const val dbFileName = "chit-chat.db"

// Class 'AppDatabase_Impl' is not abstract and does not implement abstract base class member 'clearAllTables'.
interface DB {
    fun clearAllTables() {}
}
