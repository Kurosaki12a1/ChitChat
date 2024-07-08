package data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "User")
data class UserEntity(
    @PrimaryKey val userId: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String? = null,
    val lastActive: LocalDateTime
)
