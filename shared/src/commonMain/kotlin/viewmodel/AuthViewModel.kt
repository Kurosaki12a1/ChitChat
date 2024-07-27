package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuro.chitchat.database.client.domain.repository.LocalUserDataSource
import data.model.dto.ApiResponse
import data.model.dto.UserDto
import data.model.toUser
import domain.models.ApiRequest
import domain.models.LoginState
import domain.repository.DataStoreOperations
import domain.repository.remote.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
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
    private val userRepository: LocalUserDataSource
) : ViewModel(), KoinComponent {

    // State to keep track of whether the user is signed in or not.
    private val _signedInState: MutableState<Boolean> = mutableStateOf(false)
    val signedInState: State<Boolean> = _signedInState

    // State to manage the message bar status.
    private val _loginState: MutableState<LoginState> = mutableStateOf(LoginState())
    val loginState: State<LoginState> = _loginState

    // State to manage the API response.
    private val _apiResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    fun init() {
        // Read the signed-in state from DataStore when the ViewModel is initialized.
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.readSignedInState().collect { completed ->
                if (completed) {
                    signIn { _signedInState.value = completed }
                } else {
                    _signedInState.value = completed
                }
            }
        }
    }


    /**
     * SignIn Account.
     * If the session is expired, the provided callback is invoked.
     *
     * @param onSessionExpired The callback to be invoked when the session is expired.
     */
    private fun signIn(onSessionExpired: () -> Unit) {
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.signIn()
                }
                if (response.success) {
                    val job = async {
                        saveUserToLocalStorage(response.user)
                    }
                    job.await()
                    _apiResponse.value = RequestState.Success(response)
                    _loginState.value = LoginState(
                        message = response.message,
                    )
                } else {
                    _apiResponse.value = RequestState.Idle
                    onSessionExpired.invoke()
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _loginState.value = LoginState(error = e)
            }
        }
    }

    /**
     * Saves user to local storage and dataStore
     *
     * @param userDto API response userDTO
     */
    private fun saveUserToLocalStorage(userDto: UserDto?) {
        viewModelScope.launch(Dispatchers.IO) {
            userDto?.let {
                userRepository.insertUser(it.toUser())
                dataStoreOperations.saveSignedInId(it.userId!!)
            }
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
        _loginState.value = LoginState(
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
                    val job = async { saveUserToLocalStorage(response.user) }
                    job.await()
                    _apiResponse.value = RequestState.Success(response)
                    _loginState.value = LoginState(
                        message = response.message,
                    )
                } else {
                    _apiResponse.value = RequestState.Error(Exception(response.message ?: ""))
                    _loginState.value = LoginState(
                        message = response.message,
                        error = response.error ?: Exception(response.message ?: "")
                    )
                }

            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(e)
            _loginState.value = LoginState(error = e)
        }
    }
}