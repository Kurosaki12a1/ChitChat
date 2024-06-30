package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DataModel(
    val id: Int,
    val name: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String,
    val isActive: Boolean
)