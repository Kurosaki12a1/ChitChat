package viewmodel

import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.SocketUseCase

open class MoreViewModel(
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase) {

}