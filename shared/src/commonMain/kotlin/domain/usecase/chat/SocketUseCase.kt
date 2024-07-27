package domain.usecase.chat

data class SocketUseCase(
    val connectToWebSocketUseCase: ConnectToWebSocketUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val receiveMessagesUseCase: ReceiveMessagesUseCase,
)