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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.BaseScreen
import domain.models.StatusUser
import navigation.chat_room.ChatRoomComponent
import org.koin.compose.viewmodel.koinViewModel
import presenter.chat_room.component.BottomBarChatRoom
import presenter.chat_room.component.MessageChatRoom
import presenter.chat_room.component.TopBarChatRoom
import ui.theme.BackgroundColorEmphasis
import viewmodel.ChatRoomViewModel

@Composable
fun ChatRoomScreen(
    component: ChatRoomComponent,
    viewModel: ChatRoomViewModel = koinViewModel()
) {
    BaseScreen(viewModel) {
        LaunchedEffect(Unit) {
            viewModel.updateRoomInfo(component.listUser, component.roomType)
        }

        val listUser by viewModel.listUser
        val isPublic by derivedStateOf { listUser.size > 2 }
        if (isPublic) {
            PublicChatScreen()
        } else {
            PrivateChatScreen()
        }
    }
}

@Composable
private fun PrivateChatScreen() {
    Scaffold(topBar = {
        Column {
            TopBarChatRoom(
                title = "Test aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                status = StatusUser.ONLINE.status,
                onBack = {

                },
                onMore = {

                },
                onSearchMessage = {

                }
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(3.dp)
                    .background(BackgroundColorEmphasis)
            )
        }
    }, bottomBar = {
        BottomBarChatRoom()
    }) { padding ->
        MessageChatRoom(padding)
    }
}

@Composable
private fun PublicChatScreen() {

}