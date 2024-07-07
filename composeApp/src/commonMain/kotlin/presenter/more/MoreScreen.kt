package presenter.more

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.contacts.TabContactsChild
import navigation.more.MoreComponent
import presenter.contacts.component.ContactsBar
import presenter.contacts.component.StickyHeaderContacts
import presenter.contacts.tabs.TabContactsAllScreen
import presenter.contacts.tabs.TabContactsBotsScreen
import presenter.contacts.tabs.TabContactsFavoritesScreen
import presenter.more.component.MoreBar

@Composable
fun MoreScreen(
    component: MoreComponent
) {
    val state = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(state = state) {
            item {
                MoreBar(
                    onSettingsClick = {

                    }
                )
            }
        }
    }
}