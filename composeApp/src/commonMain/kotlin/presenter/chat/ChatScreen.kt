@file:OptIn(ExperimentalFoundationApi::class)

package presenter.chat

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import component.AnimatedBox
import navigation.ChatBar
import navigation.chat.ChatComponent
import presenter.chat.component.StickyTabRow
import presenter.chat.tabs.TabAllScreen
import presenter.chat.tabs.TabFavoritesScreen
import presenter.chat.tabs.TabUnreadScreen

@Composable
fun ChatScreen(
    component: ChatComponent
) {
    val state = rememberLazyListState()
    var selectedTabIndex by remember { mutableStateOf(0) }
    var oldSelectedTabIndex by remember { mutableStateOf(0) }
    val offsetX = remember { Animatable(0f) }

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
                StickyTabRow(
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        if (oldSelectedTabIndex != selectedTabIndex) {
                            oldSelectedTabIndex = selectedTabIndex
                        }
                        selectedTabIndex = index
                    }
                )
            }
            item {
                AnimatedBox(
                    currentState = oldSelectedTabIndex,
                    targetState = selectedTabIndex,
                    orderedContent = listOf("0", "1", "2")
                ) {
                    when (selectedTabIndex) {
                        0 -> {
                            TabAllScreen { }
                        }

                        1 -> {
                            TabUnreadScreen()
                        }

                        2 -> {
                            TabFavoritesScreen()
                        }
                    }
                }
            }
            /* items(50) {
                 DummyItem()
             }*/
        }

    }
}

@Composable
private fun DummyItem() {
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp)
    ) {
        Text("TESTTTTTTTTTTTTTTT")
    }
}