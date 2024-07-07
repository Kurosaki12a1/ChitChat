@file:OptIn(ExperimentalFoundationApi::class)

package presenter.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.chat.ChatComponent
import navigation.chat.TabChatChild
import presenter.chat.component.ChatBar
import presenter.chat.component.StickyHeaderChat
import presenter.chat.tabs.TabChatAllScreen
import presenter.chat.tabs.TabChatFavoritesScreen
import presenter.chat.tabs.TabChatUnreadScreen

@Composable
fun ChatScreen(
    component: ChatComponent
) {
    val state = rememberLazyListState()
    val childStack by component.childStack.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            item {
                ChatBar(
                    onSearchClick = {

                    },
                    onStartNewChat = {

                    },
                    onBookmarkClick = {

                    }
                )
            }
            stickyHeader {
                StickyHeaderChat(
                    navigation = childStack,
                    onTabSelected = { item -> component.onTabSelect(item) }
                )
            }
            item {
                Children(
                    modifier = Modifier.fillMaxWidth(),
                    stack = childStack,
                    animation = stackAnimation(slide() + fade())
                ) { child ->
                    when (val instance = child.instance) {
                        is TabChatChild.AllScreen -> {
                            TabChatAllScreen(component = instance.component) {

                            }
                        }

                        is TabChatChild.UnreadScreen -> {
                            TabChatUnreadScreen(component = instance.component)
                        }

                        is TabChatChild.FavoritesScreen -> {
                            TabChatFavoritesScreen(component = instance.component)
                        }
                    }
                }
            }
        }
    }
}
