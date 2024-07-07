package presenter.chat.tabs

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import navigation.chat.tabs.TabFavoritesComponent

@Composable
fun LazyItemScope.TabFavoritesScreen(
    component: TabFavoritesComponent
) {
    Text("This is favorites Screen")
}