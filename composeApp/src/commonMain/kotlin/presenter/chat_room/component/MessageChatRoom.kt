package presenter.chat_room.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import domain.models.MessageModel
import ui.theme.HighlightDefaultDefault

@Composable
fun MessageChatRoom(
    oldChats: List<MessageModel>,
    newChat: List<MessageModel>,
    paddingValues: PaddingValues
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().background(HighlightDefaultDefault).padding(paddingValues)
    ) {
        // TODO
    }
}