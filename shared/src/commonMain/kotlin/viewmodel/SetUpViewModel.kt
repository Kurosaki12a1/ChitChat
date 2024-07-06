package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.ApiRequest
import domain.model.ApiResponse
import domain.model.MessageBarState
import domain.model.User
import domain.model.UserUpdate
import domain.repository.AuthRepository
import domain.repository.DataStoreOperations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import utils.MAX_LENGTH
import utils.RequestState

open class SetUpViewModel(
    private val repository: AuthRepository,
    private val dataStoreOperations: DataStoreOperations
) : ViewModel(), KoinComponent {

    private val _user: MutableState<User?> = mutableStateOf(null)
    val user: State<User?> = _user

    private val _firstName: MutableState<String> = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lastName: MutableState<String> = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _apiResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val apiResponse: State<RequestState<ApiResponse>> = _apiResponse

    private val _clearSessionResponse: MutableState<RequestState<ApiResponse>> =
        mutableStateOf(RequestState.Idle)
    val clearSessionResponse: State<RequestState<ApiResponse>> = _clearSessionResponse

    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    repository.getUserInfo()
                }
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
                if (response.user != null) {
                    _user.value = response.user
                    _firstName.value = response.user.name.split(" ").first()
                    _lastName.value = response.user.name.split(" ").last()
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun setConfirmedState(userId: String?, isConfirmed: Boolean) {
        if (userId == null) return
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.saveConfirmedState(
                isConfirmed = isConfirmed,
                id = userId
            )
        }
    }

    fun updateUserInfo() {
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (user.value != null) {
                    val response = repository.getUserInfo()
                    verifyAndUpdate(currentUser = response)
                }
            } catch (e: Exception) {
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    private fun verifyAndUpdate(currentUser: ApiResponse) {
        val (verified, exception) = if (firstName.value.isEmpty() || lastName.value.isEmpty()) {
            Pair(false, EmptyFieldException())
        } else {
            if (currentUser.user?.name?.split(" ")?.first() == firstName.value &&
                currentUser.user.name.split(" ").last() == lastName.value
            ) {
                Pair(false, NothingToUpdateException())
            } else {
                Pair(true, null)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (verified) {
                try {
                    val response = repository.updateUser(
                        request = UserUpdate(
                            firstName = firstName.value,
                            lastName = lastName.value
                        )
                    )
                    _apiResponse.value = RequestState.Success(response)
                    _messageBarState.value = MessageBarState(
                        message = response.message,
                        error = response.error
                    )
                } catch (e: Exception) {
                    _apiResponse.value = RequestState.Error(e)
                    _messageBarState.value = MessageBarState(error = e)
                }
            } else {
                _apiResponse.value = RequestState.Success(
                    ApiResponse(success = false, error = exception)
                )
                _messageBarState.value = MessageBarState(error = exception)
            }
        }
    }

    fun clearSession() {
        _clearSessionResponse.value = RequestState.Loading
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.clearSession()
                _clearSessionResponse.value = RequestState.Success(response)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
            } catch (e: Exception) {
                _clearSessionResponse.value = RequestState.Error(e)
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun deleteUser() {
        _clearSessionResponse.value = RequestState.Loading
        _apiResponse.value = RequestState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.deleteUser()
                _clearSessionResponse.value = RequestState.Success(response)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
            } catch (e: Exception) {
                _clearSessionResponse.value = RequestState.Error(e)
                _apiResponse.value = RequestState.Error(e)
                _messageBarState.value = MessageBarState(error = e)
            }
        }
    }

    fun verifyTokenOnBackend(request: ApiRequest) {
        _apiResponse.value = RequestState.Loading
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val response = repository.verifyTokenOnBackend(request = request)
                _apiResponse.value = RequestState.Success(response)
                _messageBarState.value = MessageBarState(
                    message = response.message,
                    error = response.error
                )
            }
        } catch (e: Exception) {
            _apiResponse.value = RequestState.Error(e)
            _messageBarState.value = MessageBarState(error = e)
        }
    }

    fun saveSignedInState(signedIn: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.saveSignedInState(signedIn = signedIn)
        }
    }

    fun updateFirstName(newName: String) {
        if (newName.length < MAX_LENGTH) {
            _firstName.value = newName
        }
    }

    fun updateLastName(newName: String) {
        if (newName.length < MAX_LENGTH) {
            _lastName.value = newName
        }
    }

}

class EmptyFieldException(
    override val message: String = "Empty Input Field."
) : Exception()

class NothingToUpdateException(
    override val message: String = "Nothing to Update."
) : Exception()