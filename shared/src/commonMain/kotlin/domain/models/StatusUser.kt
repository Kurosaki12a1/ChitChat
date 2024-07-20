package domain.models

import kotlinx.serialization.Serializable

@Serializable
enum class StatusUser(val status: String) {
    ONLINE("online"),
    OFFLINE("offline"),
    BUSY("busy"),
    AWAY("away")
}