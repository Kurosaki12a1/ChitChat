package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import data.model.dto.ChatRoomDto
import domain.models.ChatRoomModel
import domain.models.MessageModel
import domain.models.PrivateChatRequest
import domain.models.UserModel
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.CreatePublicChatRoomUseCase
import domain.usecase.chat.SocketUseCase
import domain.usecase.chat.StartPrivateChatUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import utils.RequestState
import utils.now

open class AddChatRoomViewModel(
    private val createPublicChatRoomUseCase: CreatePublicChatRoomUseCase,
    private val startPrivateChatUseCase: StartPrivateChatUseCase,
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase) {

    private val _createRoomChatResponse: MutableState<RequestState<ChatRoomDto?>> =
        mutableStateOf(RequestState.Idle)
    val createChatRoomResponse: State<RequestState<ChatRoomDto?>> = _createRoomChatResponse

    fun startPrivateChat(receiver: UserModel?, type: String, message: MessageModel) {
        if (receiver == null || user.value == null) return
        val updatedMessage = message.copy(
            senderId = user.value?.userId ?: ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            _createRoomChatResponse.value = RequestState.Loading
            try {
                val response = startPrivateChatUseCase(
                    PrivateChatRequest(
                        sender = user.value!!,
                        receiver = receiver,
                        roomType = type,
                        firstMessage = updatedMessage
                    )
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

    fun startPublicChat(
        listUser: List<UserModel>,
        roomType: String,
        message: MessageModel,
        name: String? = null
    ) {
        val creatorId = user.value!!.userId
        val room = ChatRoomModel(
            participants = listUser.map { it.userId },
            roomType = roomType,
            createdBy = creatorId,
            createdTime = now(),
            updatedTime = now(),
            lastMessage = message,
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