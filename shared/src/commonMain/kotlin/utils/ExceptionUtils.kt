package utils

class GoogleAccountNotFoundException(
    override val message: String? = "Google Account Not Found."
) : Exception()