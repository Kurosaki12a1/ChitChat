package navigation.more

import com.arkivanov.decompose.ComponentContext
import navigation.NavigationItem

class MoreComponent(
    componentContext: ComponentContext,
    private val onNavigateTo: (NavigationItem) -> Unit
) : ComponentContext by componentContext {
    fun navigateTo(item: NavigationItem) {
        onNavigateTo(item)
    }
}