package presenter.chat.tabs

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import navigation.chat.tabs.ChatFavoritesComponent

@Composable
fun LazyItemScope.TabChatFavoritesScreen(
    component: ChatFavoritesComponent
) {
    Text("This is favorites Screen")
}