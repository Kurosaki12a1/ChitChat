package viewmodel

import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.SocketUseCase

open class ChatViewModel(
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase)