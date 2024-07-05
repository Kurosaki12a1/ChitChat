package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.MessageBarState
import domain.repository.AuthRepository
import domain.repository.DataStoreOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import utils.GoogleAccountNotFoundException
import utils.RequestState

open class AuthViewModel(
    private val repository: AuthRepository,
    private val dataStoreOperations: DataStoreOperations
) : ViewModel(), KoinComponent {

    private val _confirmedState: MutableState<Boolean?> = mutableStateOf(null)
    val confirmedState: State<Boolean?> = _confirmedState

    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _apiResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.readSignedInState().collect { completed ->
                _signedInState.value = completed
            }
        }
    }

    fun readConfirmedState(userId: String) {
        viewModelScope.launch {
            dataStoreOperations.readConfirmedState(userId).collect { confirmed ->
                _confirmedState.value = confirmed
            }
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.saveSignedInState(signedIn = signedIn)
        }
    }

    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(
            error = GoogleAccountNotFoundException()
        )
    }

    fun verifyTokenOnBackend(request: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.verifyTokenOnBackend(request = request)
                if (response.success) {
                    _apiResponse.value = RequestState.Success(response)
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                    )
                } else {
                    _apiResponse.value = RequestState.Error(Exception(response.message ?: ""))
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                        error = response.error ?: Exception(response.message?: "")
                    )
                }

            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(e)
            _messageBarState.value = MessageBarState(error = e)
        }
    }
}