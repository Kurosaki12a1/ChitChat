@file:OptIn(ExperimentalFoundationApi::class)

package presenter.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.settings.SettingsChild
import navigation.settings.SettingsComponent
import navigation.settings.SettingsOptions
import presenter.settings.component.SettingsBar
import presenter.settings.component.SettingsChat
import presenter.settings.component.SettingsContacts
import presenter.settings.component.SettingsDisplay
import presenter.settings.component.SettingsMain
import presenter.settings.component.SettingsNotifications
import presenter.settings.component.SettingsOther
import presenter.settings.component.SettingsPasswordAndLock
import presenter.settings.component.SettingsReleaseNote
import presenter.settings.component.SettingsServiceInformation
import presenter.settings.component.SettingsSync

@Composable
fun SettingsScreen(
    component: SettingsComponent
) {
    val state = rememberLazyListState()
    val childStack by component.childStack.subscribeAsState()
    Scaffold(
        topBar = {
            SettingsBar(childStack.active.configuration) {
                if (childStack.active.configuration == SettingsOptions.Main) {
                    component.popBackStack()
                } else {
                    component.pop()
                }
            }
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            state = state
        ) {
            item {
                Children(
                    stack = childStack,
                    animation = stackAnimation(fade())
                ) { child ->
                    when (child.instance) {
                        SettingsChild.Main -> {
                            SettingsMain {
                                component.onNavigateOptions(it)
                            }
                        }

                        SettingsChild.Notifications -> SettingsNotifications()
                        SettingsChild.Chat -> SettingsChat()
                        SettingsChild.Contacts -> SettingsContacts()
                        SettingsChild.Display -> SettingsDisplay()
                        SettingsChild.Others -> SettingsOther()
                        SettingsChild.PasswordAndLock -> SettingsPasswordAndLock()
                        SettingsChild.ReleaseNote -> SettingsReleaseNote()
                        SettingsChild.ServiceInformation -> SettingsServiceInformation()
                        SettingsChild.Sync -> SettingsSync()
                    }
                }
            }
        }
    }
}
