package com.kuro.chitchat.auth_google

/**
 * GoogleUser class holds most necessary fields
 */
data class GoogleUser(
    val idToken: String,
    val accessToken: String? = null,
    val displayName: String = "",
    val profilePicUrl: String? = null,
)