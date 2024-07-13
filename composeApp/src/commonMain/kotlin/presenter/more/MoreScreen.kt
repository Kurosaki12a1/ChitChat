package presenter.more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import navigation.NavigationItem
import navigation.more.MoreComponent
import org.koin.compose.koinInject
import presenter.more.component.BottomActionBar
import presenter.more.component.MoreBar
import presenter.more.component.MoreServices
import presenter.more.component.ProfileToolbar
import presenter.more.component.ServicesBar
import viewmodel.MoreViewModel

@Composable
fun MoreScreen(
    component: MoreComponent,
    moreViewModel: MoreViewModel = koinInject()
) {
    val user by moreViewModel.user
    LaunchedEffect(Unit) {
        moreViewModel.init()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            MoreBar(
                onSettingsClick = {
                    component.navigateTo(NavigationItem.SettingsScreen)
                }
            )
            user?.let {
                ProfileToolbar(
                    userModel = it,
                    onProfileClick = {

                    },
                    onSyncClick = {

                    }
                )
            }
            ServicesBar { item ->
                when (item) {
                    MoreServices.MEMO.ordinal -> {

                    }

                    MoreServices.TASK.ordinal -> {

                    }

                    MoreServices.APPS.ordinal -> {

                    }

                    MoreServices.NOTICE.ordinal -> {

                    }

                    MoreServices.SUPPORT.ordinal -> {

                    }
                }
            }
        }
        BottomActionBar(
            onPrivacyClick = {

            },
            onSignOutClick = {

            }
        )
    }
}