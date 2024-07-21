package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.viewModelScope
import data.model.dto.ChatRoomDto
import data.model.dto.UserDto
import domain.models.ChatRoomModel
import domain.models.RoomType
import domain.models.UserModel
import domain.usecase.auth.GetUserInfoUseCase
import domain.usecase.chat.CreatePublicChatRoomUseCase
import domain.usecase.chat.SocketUseCase
import domain.usecase.search.SearchUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import utils.RequestState
import utils.now

open class AddChatViewModel(
    private val searchUserUseCase: SearchUserUseCase,
    socketUseCase: SocketUseCase,
    getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel(socketUseCase, getUserInfoUseCase) {
    private val _searchValue = mutableStateOf("")
    val searchValue: State<String> = _searchValue

    private val _searchResponse: MutableState<RequestState<List<UserDto>>> =
        mutableStateOf(RequestState.Idle)
    val searchResponse: State<RequestState<List<UserDto>>> = _searchResponse

    private val _userSelected = mutableStateListOf<UserModel>()
    val userSelected: SnapshotStateList<UserModel> = _userSelected

    fun onSearchTextChange(text: String) {
        _searchValue.value = text
    }

    fun updateListSelected(userModel: UserModel) {
        if (_userSelected.contains(userModel)) {
            _userSelected.remove(userModel)
        } else {
            _userSelected.add(userModel)
        }
    }

    fun search() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchResponse.value = RequestState.Loading
            try {
                val result = searchUserUseCase(_searchValue.value)
                result.second?.let {
                    _searchResponse.value = RequestState.Error(it)
                    return@launch
                }
                result.first?.let {
                    _searchResponse.value = if (it.isEmpty()) {
                        RequestState.Error(Exception("Search not found"))
                    } else {
                        RequestState.Success(it)
                    }
                    return@launch
                }
                _searchResponse.value = RequestState.Error(Exception("An error occurred"))
            } catch (e: Exception) {
                _searchResponse.value = RequestState.Error(e)
            }
        }
    }
}