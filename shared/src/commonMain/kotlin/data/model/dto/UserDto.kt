package data.model.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UserDto(
    val id: String? = null,
    val userId: String? = null,
    val name: String? = null,
    val emailAddress: String? = null,
    val profilePhoto: String? = null,
    val lastActive: LocalDateTime? = null,
    val status: String? = null,
)
