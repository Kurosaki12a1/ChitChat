package domain.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserModel(
    val userId: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String,
    val lastActive: LocalDateTime,
    val status: String,

    @Transient
    val error: Exception? = null
)