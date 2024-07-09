@file:OptIn(ExperimentalFoundationApi::class)

package presenter.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import navigation.settings.SettingsComponent
import presenter.settings.component.SettingsBar

@Composable
fun SettingsScreen(
    component: SettingsComponent
) {
    val state = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state
    ) {
        stickyHeader {
            SettingsBar {
                component.popBackStack()
            }
        }
    }
}