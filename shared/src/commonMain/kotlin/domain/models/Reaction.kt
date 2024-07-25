package domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Reaction(
    val userId: String,
    val emoCode: String
)