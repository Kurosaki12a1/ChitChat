package presenter.chat.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import navigation.chat.tabs.TabUnreadComponent

@Composable
fun LazyItemScope.TabUnreadScreen(
    component : TabUnreadComponent
) {
    Column {
        Text("This is tab unread")
    }
}