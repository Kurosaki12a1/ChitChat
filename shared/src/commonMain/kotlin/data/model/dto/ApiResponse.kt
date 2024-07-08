package data.model.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ApiResponse(
    val success: Boolean,
    val user: UserDto? = null,
    val message: String? = null,
    @Transient
    val error: Exception? = null
)