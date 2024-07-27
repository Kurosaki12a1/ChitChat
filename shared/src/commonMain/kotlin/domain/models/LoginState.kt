package domain.models

data class LoginState(
    val message: String? = null,
    val error: Exception? = null
)