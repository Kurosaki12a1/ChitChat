package presenter.more

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import navigation.more.MoreComponent
import presenter.more.component.MoreBar

@Composable
fun MoreScreen(
    component: MoreComponent
) {
    val state = rememberLazyListState()

    LazyColumn(state = state) {
        item {
            MoreBar(
                onSettingsClick = {

                }
            )
        }
        item {

        }
    }
}