package presenter.chat.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import navigation.chat.tabs.ChatUnreadComponent

@Composable
fun LazyItemScope.TabChatUnreadScreen(
    component : ChatUnreadComponent
) {
    Column {
        Text("This is tab unread")
    }
}