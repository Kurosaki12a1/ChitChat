package presenter.chat_room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import domain.models.UserModel
import kotlinx.coroutines.flow.collectLatest
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
    onBack: () -> Unit,
    viewModel: ChatRoomViewModel = koinViewModel()
) {
    val listJoinedUser = remember { mutableStateListOf<UserDto>() }
    val isPublic by derivedStateOf { chatRoom.participants.size > 2 }
    val listMessageChat = remember { mutableStateListOf<MessageModel>() }

    LaunchedEffect(viewModel.listJoinedUser) {
        when (val result = viewModel.listJoinedUser.value) {
            is RequestState.Error -> {
                /* handle error */
            }

            is RequestState.Success -> {
                listJoinedUser.clear()
                listJoinedUser.addAll(result.data)
            }

            else -> {
                /* handle other states */
            }
        }
    }

    LaunchedEffect(viewModel.incomingMessages.collectAsState()) {
        viewModel.incomingMessages.collectLatest { message ->
            if (message != null && message.chatRoomId == chatRoom.id) {
                listMessageChat.add(message.toModel())
            }
        }
    }

    LaunchedEffect(chatRoom.participants.size) {
        viewModel.updateRoomUser(chatRoom.participants)
    }

    LaunchedEffect(Unit) {
        viewModel.getChatHistory(chatRoom.id)
    }

    BaseScreen(viewModel) {
        val myUser by viewModel.user
        val historyChat by viewModel.chatHistory
        if (isPublic) {
            PublicChatScreen(
                chatRoom = chatRoom,
                myUser = myUser,
                otherUsers =
                listJoinedUser
                    .filter { it.userId != myUser?.userId }
                    .map { it.toModel() },
                onBack = onBack,
                oldChats = historyChat.map { it.toModel() },
                newChat = listMessageChat,
                onSendMessage = { message ->
                    viewModel.sendMessage(message)
                }
            )
        } else {
            PrivateChatScreen(
                chatRoom = chatRoom,
                myUser = myUser,
                otherUser = listJoinedUser.find { it.userId != myUser?.userId }?.toModel(),
                onBack = onBack,
                oldChats = historyChat.map { it.toModel() },
                newChat = listMessageChat,
                onSendMessage = { message ->
                    viewModel.sendMessage(message)
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
    oldChats: List<MessageModel>,
    newChat: List<MessageModel>,
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
            oldChats = oldChats,
            newChat = newChat,
            paddingValues = padding
        )
    }
}

@Composable
private fun PublicChatScreen(
    chatRoom: ChatRoomModel,
    myUser: UserModel?,
    otherUsers: List<UserModel>?,
    onBack: () -> Unit,
    oldChats: List<MessageModel>,
    newChat: List<MessageModel>,
    onSendMessage: (MessageModel) -> Unit
) {
    if (otherUsers == null || myUser == null) return
    Scaffold(
        topBar = {
            Column {
                TopBarChatRoom(
                    title = chatRoom.roomName,
                    onBack = onBack,
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
                senderId = myUser.userId,
                chatRoomId = chatRoom.id,
                onSend = onSendMessage
            )
        }) { padding ->
        MessageChatRoom(
            myUser = myUser,
            oldChats = oldChats,
            newChat = newChat,
            paddingValues = padding
        )
    }
}