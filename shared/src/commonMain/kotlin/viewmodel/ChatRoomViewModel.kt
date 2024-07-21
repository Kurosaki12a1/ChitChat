package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.model.dto.ChatRoomDto
import domain.models.ChatRoomModel
import domain.models.UserModel
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.CreatePublicChatRoomUseCase
import domain.usecase.chat.SocketUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import utils.RequestState
import utils.now

open class ChatRoomViewModel(
    private val createPublicChatRoomUseCase: CreatePublicChatRoomUseCase,
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase) {
    private val _createRoomChatResponse: MutableState<RequestState<ChatRoomDto?>> =
        mutableStateOf(RequestState.Idle)
    val createChatRoomResponse: State<RequestState<ChatRoomDto?>> = _createRoomChatResponse

    private val _listUser: MutableState<List<UserModel>> = mutableStateOf(emptyList())
    val listUser: State<List<UserModel>> = _listUser

    fun updateRoomInfo(listUser: List<UserModel>, roomType: String) {
        _listUser.value = listUser
    }

    fun createRoomAndInvite(listUser: List<UserModel>, type: String, name: String? = null) {
        val creatorId = user.value!!.userId
        val listIds = listUser.map { it.userId } + creatorId
        val room = ChatRoomModel(
            participants = listIds,
            roomType = type,
            createdBy = creatorId,
            createdTime = now(),
            updatedTime = now(),
            roomName = name ?: (listUser.map { it.name } + user.value!!.name).joinToString(", ")
        )
        viewModelScope.launch(Dispatchers.IO) {
            _createRoomChatResponse.value = RequestState.Loading
            try {
                val response = createPublicChatRoomUseCase(
                    room = room,
                    creatorId = creatorId
                )
                if (response != null) {
                    _createRoomChatResponse.value = RequestState.Success(response)
                } else {
                    _createRoomChatResponse.value =
                        RequestState.Error(Exception("An error occurred"))
                }
            } catch (e: Exception) {
                _createRoomChatResponse.value = RequestState.Error(e)
            }
        }
    }
}