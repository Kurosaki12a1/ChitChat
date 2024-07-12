package domain.usecase.chat

data class SessionChatUseCase(
    val getUserChatRoomsUseCase: GetUserChatRoomsUseCase,
    val createPublicChatRoomUseCase: CreatePublicChatRoomUseCase,
    val startPrivateChatUseCase: StartPrivateChatUseCase,
    val joinPublicChatRoomUseCase: JoinPublicChatRoomUseCase,
    val getChatHistoryUseCase: GetChatHistoryUseCase,
    val connectToWebSocketUseCase: ConnectToWebSocketUseCase,
    val sendMessageUseCase: SendMessageUseCase,
    val receiveMessagesUseCase: ReceiveMessagesUseCase,
)