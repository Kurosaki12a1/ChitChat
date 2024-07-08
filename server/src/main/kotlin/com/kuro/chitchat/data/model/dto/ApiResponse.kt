package com.kuro.chitchat.data.model.dto

import data.model.dto.UserDto
import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
    val success: Boolean,
    val userDto: UserDto? = null,
    val message: String? = null
)
