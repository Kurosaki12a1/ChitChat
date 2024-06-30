package com.kuro.chitchat.domain.model

import io.ktor.server.auth.Principal

data class UserSession(
    val id: String,
    val name: String
): Principal
