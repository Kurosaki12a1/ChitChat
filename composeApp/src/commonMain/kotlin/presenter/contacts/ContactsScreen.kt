@file:OptIn(ExperimentalFoundationApi::class)

package presenter.contacts

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
import navigation.contacts.ContactsComponent
import navigation.contacts.TabContactsChild
import presenter.contacts.component.ContactsBar
import presenter.contacts.component.StickyHeaderContacts
import presenter.contacts.tabs.TabContactsAllScreen
import presenter.contacts.tabs.TabContactsBotsScreen
import presenter.contacts.tabs.TabContactsFavoritesScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsScreen(
    component: ContactsComponent
) {
    val state = rememberLazyListState()
    val childStack by component.childStack.subscribeAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            item {
                ContactsBar(
                    onSearchClick = {

                    },
                    onAddContacts = {

                    },
                    onStartNewChat = {

                    },
                    onMoreClick = {

                    }
                )
            }
            stickyHeader {
                StickyHeaderContacts(
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
                        is TabContactsChild.AllScreen -> {
                            TabContactsAllScreen(component = instance.component)
                        }

                        is TabContactsChild.FavoritesScreen -> {
                            TabContactsFavoritesScreen(component = instance.component)
                        }

                        is TabContactsChild.BotsScreen -> {
                            TabContactsBotsScreen(component = instance.component)
                        }
                    }
                }
            }
        }
    }
}