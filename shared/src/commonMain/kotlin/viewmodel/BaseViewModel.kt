package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.dto.MessageDto
import domain.models.MessageModel
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

/**
 * Base ViewModel class for managing WebSocket connections and user information.
 *
 * @property socketUseCase Use case for handling WebSocket operations.
 * @property getUserInfoUseCase Use case for retrieving user information.
 */
open class BaseViewModel(
    private val socketUseCase: SocketUseCase,
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

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
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = getUserInfoUseCase()
            if (_user.value != null) {
                connectToWebSocket(_user.value!!.userId)
            }
        }
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