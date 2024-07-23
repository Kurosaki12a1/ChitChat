package presenter.chat_room

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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.BaseScreen
import data.model.dto.UserDto
import data.model.toModel
import domain.models.ChatRoomModel
import domain.models.MessageModel
import domain.models.StatusUser
import domain.models.UserModel
import org.koin.compose.viewmodel.koinViewModel
import presenter.chat_room.component.BottomBarChatRoom
import presenter.chat_room.component.MessageChatRoom
import presenter.chat_room.component.TopBarChatRoom
import ui.theme.BackgroundColorEmphasis
import utils.RequestState
import viewmodel.ChatRoomViewModel

@Composable
fun ChatRoomScreen(
    chatRoom: ChatRoomModel,
    onReCreateChatRoom: (ChatRoomModel) -> Unit,
    onBack: () -> Unit,
    viewModel: ChatRoomViewModel = koinViewModel()
) {
    val listJoinedUser = remember { mutableStateListOf<UserDto>() }
    val isPublic by derivedStateOf { chatRoom.participants.size > 2 }

    LaunchedEffect(viewModel.listJoinedUser) {
        when (val result = viewModel.listJoinedUser.value) {
            is RequestState.Error -> { /* handle error */ }
            is RequestState.Success -> {
                listJoinedUser.addAll(result.data)
            }
            else -> { /* handle other states */ }
        }
    }

    BaseScreen(viewModel) {
        val myUser by viewModel.user

        LaunchedEffect(chatRoom.participants.size) {
            viewModel.updateRoomUser(chatRoom.participants)
        }

        if (isPublic) {
            PublicChatScreen()
        } else {
            // New room
            if (chatRoom.id.isBlank()) {
                val isListChanged = derivedStateOf { chatRoom.participants.map { it } }
                val otherUser = remember(isListChanged) { chatRoom.listUser.find { it.userId != myUser?.userId } }
                SharedPrivateChatScreen(
                    chatRoom = chatRoom,
                    otherUser = otherUser,
                    onBack = onBack,
                    onSendMessage = { message ->
                   /*     onReCreateChatRoom(
                            ChatRoomModel(
                                id = "ada;dasdasd",
                                participants = chatRoom.participants,
                                roomType = chatRoom.roomType,
                                createdBy = chatRoom.createdBy,
                                createdTime = chatRoom.createdTime,
                                updatedTime = chatRoom.updatedTime,
                            )
                        )*/
                   //     viewModel.startPrivateChat(receiver = otherUser, message = message)
                      //  onReCreateChatRoom(chatRoom.copy(id = generateRoomId()))
                    }
                )
            } else {
                val otherUser = listJoinedUser.find { it.userId != myUser?.userId }?.toModel()
                SharedPrivateChatScreen(
                    chatRoom = chatRoom,
                    otherUser = otherUser,
                    onBack = onBack,
                    onSendMessage = { message ->
                        println("Vào public room thành công!")
                    }
                )
            }
        }
    }
}

@Composable
private fun SharedPrivateChatScreen(
    chatRoom: ChatRoomModel,
    otherUser: UserModel?,
    onBack: () -> Unit,
    onSendMessage: (MessageModel) -> Unit
) {
    if (otherUser == null) return
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
                    modifier = Modifier.fillMaxWidth().height(3.dp).background(BackgroundColorEmphasis)
                )
            }
        },
        bottomBar = {
            BottomBarChatRoom(
                senderId = otherUser.userId,
                chatRoomId = chatRoom.id,
                onSend = { onSendMessage(it) })
        }) { padding ->
        MessageChatRoom(padding)
    }
}
@Composable
private fun PublicChatScreen() {
    Scaffold(
        topBar = {
            Column {
                TopBarChatRoom(
                    title = "Test Pubkic",
                    status = StatusUser.ONLINE.status,
                    onBack = {

                    },
                    onMore = {

                    },
                    onSearchMessage = {

                    }
                )
                Divider(
                    modifier = Modifier.fillMaxWidth().height(3.dp)
                        .background(BackgroundColorEmphasis)
                )
            }
        },
        bottomBar = {
            BottomBarChatRoom(
                senderId = "",
                onSend = {

                })
        }) { padding ->
        MessageChatRoom(padding)
    }
}