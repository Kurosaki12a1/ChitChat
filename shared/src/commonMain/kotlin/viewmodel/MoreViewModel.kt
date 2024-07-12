package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.model.UserModel
import domain.repository.DataStoreOperations
import domain.repository.local.LocalUserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

open class MoreViewModel(
    private val dataStoreOperations: DataStoreOperations,
    private val userRepository: LocalUserDataSource
) : ViewModel() {

    private val _user: MutableState<UserModel?> = mutableStateOf(null)
    val user: State<UserModel?> = _user

    /**
     * Can not use init default of viewmodel, will get bug
     * "Reading a state that was created after the snapshot was taken or in a snapshot that has not yet been applied
     * See: https://issuetracker.google.com/issues/166486000
     */
    fun init() {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreOperations.getCurrentSignedIn().collectLatest { id ->
                if (id.isNotEmpty()) {
                    userRepository.getUserById(id).collectLatest { user ->
                        _user.value = user
                    }
                }
            }
        }
    }
}