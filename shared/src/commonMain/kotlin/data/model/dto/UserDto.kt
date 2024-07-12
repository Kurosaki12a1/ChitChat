package data.model.dto

import domain.model.StatusUser
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val userId: String? = null,
    val name: String? = null,
    val emailAddress: String? = null,
    val profilePhoto: String? = null,
    val lastActive: LocalDateTime? = null,
    val status: StatusUser? = null
)
