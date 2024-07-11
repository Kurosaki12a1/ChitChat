package domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class StatusUser {
    ONLINE, OFFLINE, BUSY, AWAY
}