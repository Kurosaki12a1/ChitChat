package domain.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val userId: String,
    val name: String,
    val emailAddress: String,
    val profilePhoto: String,
    val lastActive: LocalDateTime
)