package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.model.dto.MessageDto
import data.model.dto.UserDto
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.GetChatHistoryUseCase
import domain.usecase.chat.GetListJoinedUserUseCase
import domain.usecase.chat.SocketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import utils.RequestState

open class ChatRoomViewModel(
    private val getChatHistoryUseCase: GetChatHistoryUseCase,
    private val getListJoinedUserUseCase: GetListJoinedUserUseCase,
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase) {

    private val _listJoinedUser: MutableState<RequestState<List<UserDto>>> =
        mutableStateOf(RequestState.Idle)
    val listJoinedUser: State<RequestState<List<UserDto>>> = _listJoinedUser

    private val _chatHistory: MutableState<List<MessageDto>> = mutableStateOf(emptyList())
    val chatHistory: State<List<MessageDto>> = _chatHistory

    fun updateRoomUser(participants: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            _listJoinedUser.value = RequestState.Loading
            try {
                val response = getListJoinedUserUseCase(participants)
                _listJoinedUser.value = RequestState.Success(response)
            } catch (e: Exception) {
                _listJoinedUser.value = RequestState.Error(e)
            }
        }
    }

    fun getChatHistory(roomId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = getChatHistoryUseCase(roomId)
                _chatHistory.value = response.messages
            } catch (e: Exception) {
                _chatHistory.value = emptyList()
            }
        }
    }
}