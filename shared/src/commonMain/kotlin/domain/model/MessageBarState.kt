package domain.model

data class MessageBarState(
    val message: String? = null,
    val error: Exception? = null
)