package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.dto.MessageDto
import domain.models.MessageModel
import domain.models.StatusUser
import domain.models.UserModel
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.SocketUseCase
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import utils.now

/**
 * Base ViewModel class for managing WebSocket connections and user information.
 *
 * @property socketUseCase Use case for handling WebSocket operations.
 * @property getUserInfoUseCase Use case for retrieving user information.
 */
open class BaseViewModel(
    private val socketUseCase: SocketUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel(), KoinComponent {

    private val _incomingMessages = MutableStateFlow<MessageDto?>(null)
    val incomingMessages: StateFlow<MessageDto?> = _incomingMessages.asStateFlow()

    private val _webSocketSession = MutableStateFlow<WebSocketSession?>(null)
    val webSocketSession: StateFlow<WebSocketSession?> = _webSocketSession.asStateFlow()

    private val _user: MutableState<UserModel?> = mutableStateOf(null)
    val user: State<UserModel?> = _user

    /**
     * Initializes the ViewModel by loading user information and connecting to WebSocket.
     *
     * Note: Cannot use the default init block of ViewModel to avoid the bug:
     * "Reading a state that was created after the snapshot was taken or in a snapshot
     * that has not yet been applied. See: https://issuetracker.google.com/issues/166486000"
     */
    fun init() {
      /*  viewModelScope.launch(Dispatchers.IO) {
            _user.value = getUserInfoUseCase()
            if (_user.value != null) {
                connectToWebSocket(_user.value!!.userId)
            }
        }*/
        _user.value = UserModel(
            userId = "108250150007484359736",
            name = "Minh Thịnh Huỳnh",
            emailAddress = "Tes111t@gmail.com",
            lastActive = now(),
            status = StatusUser.ONLINE.status,
            profilePhoto = "https://protect2.fireeye.com/v1/url?k=d66cb7b4-b7e7a294-d66d3cfb-74fe485fb347-2161ced3ca43f186&q=1&e=58c251b8-02c2-40aa-a233-f41b025567ae&u=https%3A%2F%2Flh3.googleusercontent.com%2Fa%2FACg8ocIQvX8qnF3NqgpQuvdejzjTmkMXuEA5FifaZpnPguK6rPg9jyn9%3Ds96-c"
        )
    }

    /**
     * Connects to the WebSocket server for the given user ID.
     *
     * @param userId The ID of the user.
     */
    private fun connectToWebSocket(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            socketUseCase.connectToWebSocketUseCase(userId).collect { session ->
                _webSocketSession.value = session
                receiveMessages()
            }
        }
    }

    /**
     * Receives messages from the WebSocket server.
     */
    private fun receiveMessages() {
        viewModelScope.launch(Dispatchers.IO) {
            socketUseCase.receiveMessagesUseCase().collect { message ->
                _incomingMessages.value = message
            }
        }
    }

    /**
     * Sends a message through the WebSocket connection.
     *
     * @param message The message to be sent.
     */
    fun sendMessage(message: MessageModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _webSocketSession.value?.let {
                socketUseCase.sendMessageUseCase(message)
            }
        }
    }
}