package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.dto.ApiResponse
import data.model.dto.UserDto
import data.model.toModel
import domain.model.ApiRequest
import domain.model.MessageBarState
import domain.repository.DataStoreOperations
import domain.repository.local.UserRepository
import domain.repository.remote.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import utils.GoogleAccountNotFoundException
import utils.RequestState

/**
 * AuthViewModel is a ViewModel class that manages the authentication state of the user.
 * It interacts with the AuthRepository for API requests and DataStoreOperations for local data storage.
 *
 * @property repository The AuthRepository instance used for API requests.
 * @property dataStoreOperations The DataStoreOperations instance used for reading and writing to DataStore.
 */
open class AuthViewModel(
    private val repository: AuthRepository,
    private val dataStoreOperations: DataStoreOperations,
    private val userRepository: UserRepository
) : ViewModel(), KoinComponent {

    // State to keep track of whether the user is signed in or not.
    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    // State to manage the message bar status.
    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    // State to manage the API response.
    private val _apiResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    init {
        // Read the signed-in state from DataStore when the ViewModel is initialized.
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.readSignedInState().collect { completed ->
                if (completed) {
                    getUserInfo { _signedInState.value = completed }
                } else {
                    _signedInState.value = completed
                }
            }
        }
    }

    /**
     * Retrieves user information from the repository.
     * If the session is expired, the provided callback is invoked.
     *
     * @param onSessionExpired The callback to be invoked when the session is expired.
     */
    private fun getUserInfo(onSessionExpired: () -> Unit) {
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getUserInfo()
                }
                if (response.success) {
                    saveUserToLocalStorage(response.user)
                    _apiResponse.value = RequestState.Success(response)
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                    )
                } else {
                    _apiResponse.value = RequestState.Idle
                    onSessionExpired.invoke()
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    /**
     * Saves user to local storage
     *
     * @param userDto API response userDTO
     */
    fun saveUserToLocalStorage(userDto: UserDto?) {
        viewModelScope.launch(Dispatchers.IO) {
            userDto?.let { userRepository.insertUser(it.toModel()) }
        }
    }

    /**
     * Saves the signed-in state to DataStore.
     *
     * @param signedIn The signed-in state to be saved.
     */
    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.saveSignedInState(signedIn = signedIn)
        }
    }


    /**
     * Updates the message bar state with a specific error.
     */
    fun updateMessageBarState() {
        _messageBarState.value = MessageBarState(
            error = GoogleAccountNotFoundException()
        )
    }

    /**
     * Verifies the token on the backend by making a request to the repository.
     * Updates the API response and message bar state based on the result.
     *
     * @param request The API request containing the token to be verified.
     */
    fun verifyTokenOnBackend(request: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.verifyTokenOnBackend(request = request)
                if (response.success) {
                    saveUserToLocalStorage(response.user)
                    _apiResponse.value = RequestState.Success(response)
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                    )
                } else {
                    _apiResponse.value = RequestState.Error(Exception(response.message ?: ""))
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                        error = response.error ?: Exception(response.message ?: "")
                    )
                }

            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(e)
            _messageBarState.value = MessageBarState(error = e)
        }
    }
}