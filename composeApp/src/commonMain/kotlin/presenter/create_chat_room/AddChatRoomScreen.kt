package presenter.create_chat_room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.BaseScreen
import data.model.toModel
import domain.models.ChatRoomModel
import domain.models.MessageModel
import domain.models.UserModel
import org.koin.compose.viewmodel.koinViewModel
import presenter.chat_room.component.BottomBarChatRoom
import presenter.chat_room.component.MessageChatRoom
import presenter.chat_room.component.TopBarChatRoom
import presenter.create_chat_room.component.OnBoardChatScreen
import ui.theme.BackgroundColorEmphasis
import utils.RequestState
import viewmodel.AddChatRoomViewModel

@Composable
fun AddChatRoomScreen(
    chatRoom: ChatRoomModel,
    onCreateChatRoom: (ChatRoomModel) -> Unit,
    onBack: () -> Unit,
    viewModel: AddChatRoomViewModel = koinViewModel()
) {
    val isPublic by derivedStateOf { chatRoom.participants.size > 2 }

    LaunchedEffect(viewModel.createChatRoomResponse) {
        when (val result = viewModel.createChatRoomResponse.value) {
            is RequestState.Success -> {
                result.data?.let {
                    onCreateChatRoom(it.toModel())
                }
            }

            is RequestState.Error -> {

            }

            else -> {
                // Do nothing
            }
        }
    }

    BaseScreen(viewModel) {
        val myUser by viewModel.user
        if (isPublic) {
            PublicChatScreen(
                chatRoom = chatRoom,
                myUser = myUser,
                otherUsers = chatRoom.listUser.filter { it.userId != myUser?.userId },
                onBack = onBack,
                onSendMessage = { message ->
                    // TODO
                    /* viewModel.startPublicChat(

                     )*/
                }
            )
        } else {
            PrivateChatScreen(
                chatRoom = chatRoom,
                myUser = myUser,
                otherUser = chatRoom.listUser.find { it.userId != myUser?.userId },
                onBack = onBack,
                onSendMessage = { message ->
                    onCreateChatRoom(
                        ChatRoomModel(
                            id = "Test room nè",
                            participants = chatRoom.participants,
                            roomType = chatRoom.roomType,
                            createdBy = chatRoom.createdBy,
                            createdTime = chatRoom.createdTime,
                            updatedTime = chatRoom.updatedTime,
                        )
                    )
                    // viewModel.startPrivateChat()
                }
            )
        }
    }
}

@Composable
private fun PrivateChatScreen(
    chatRoom: ChatRoomModel,
    myUser: UserModel?,
    otherUser: UserModel?,
    onBack: () -> Unit,
    onSendMessage: (MessageModel) -> Unit
) {
    if (otherUser == null || myUser == null) return
    Scaffold(
        topBar = {
            Column {
                TopBarChatRoom(
                    title = otherUser.name,
                    status = otherUser.status,
                    onBack = onBack,
                    onMore = {},
                    onSearchMessage = {}
                )
                Divider(
                    modifier = Modifier.fillMaxWidth().height(3.dp)
                        .background(BackgroundColorEmphasis)
                )
            }
        },
        bottomBar = {
            BottomBarChatRoom(
                senderId = myUser.userId,
                chatRoomId = chatRoom.id,
                onSend = onSendMessage
            )
        }) { padding ->
        MessageChatRoom(
            myUser = myUser,
            oldChats = listOf(),
            newChat =  listOf(),
            paddingValues = padding
        )
     //   OnBoardChatScreen(chatRoom, padding)
    }
}

@Composable
private fun PublicChatScreen(
    chatRoom: ChatRoomModel,
    myUser: UserModel?,
    otherUsers: List<UserModel>?,
    onBack: () -> Unit,
    onSendMessage: (MessageModel) -> Unit
) {
    if (otherUsers == null || myUser == null) return
    Scaffold(
        topBar = {
            Column {
                TopBarChatRoom(
                    title = (otherUsers.map { it.name } + myUser.name).joinToString(", "),
                    onBack = onBack,
                    onMore = {},
                    onSearchMessage = {}
                )
                Divider(
                    modifier = Modifier.fillMaxWidth().height(3.dp)
                        .background(BackgroundColorEmphasis)
                )
            }
        },
        bottomBar = {
            BottomBarChatRoom(
                senderId = myUser.userId,
                chatRoomId = chatRoom.id,
                onSend = onSendMessage
            )
        }) { padding ->
        OnBoardChatScreen(chatRoom, padding)
    }
}