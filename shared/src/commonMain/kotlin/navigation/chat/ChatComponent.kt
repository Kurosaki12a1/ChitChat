package navigation.chat

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import navigation.chat.tabs.TabAllComponent
import navigation.chat.tabs.TabFavoritesComponent
import navigation.chat.tabs.TabUnreadComponent

class ChatComponent(
    componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
) : ComponentContext by componentContext {

    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<TabItem>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = TabItem.serializer(),
        initialConfiguration = TabItem.All,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onTabSelect(item: TabItem) {
        if (childStack.value.active.configuration == item) return
        if (shouldPopBackStack(item)) {
            navigation.bringToFront(item)
        } else {
            navigation.pushNew(item)
        }
    }

    private fun createChild(
        tabItem: TabItem,
        context: ComponentContext
    ): TabChild {
        return when (tabItem) {
            is TabItem.All -> {
                TabChild.AllScreen(
                    component = TabAllComponent(context)
                )
            }

            is TabItem.Unread -> {
                TabChild.UnreadScreen(
                    component = TabUnreadComponent(context)
                )
            }

            is TabItem.Favorites -> {
                TabChild.FavoritesScreen(
                    component = TabFavoritesComponent(context)
                )
            }
        }
    }

    // Check if the tab item should pop back stack
    private fun shouldPopBackStack(tabItem: TabItem): Boolean {
        return isPresentedInBackStack(tabItem) && childStack.active.configuration != tabItem
    }

    // Check if the tab item is already presented in the back stack
    private fun isPresentedInBackStack(item: TabItem): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == item) return true
        }
        return false
    }
}

@Serializable
sealed class TabItem {
    @Serializable
    data object All : TabItem()

    @Serializable
    data object Unread : TabItem()

    @Serializable
    data object Favorites : TabItem()
}

@Serializable
sealed class TabChild {
    data class AllScreen(val component: TabAllComponent) : TabChild()
    data class UnreadScreen(val component: TabUnreadComponent) : TabChild()
    data class FavoritesScreen(val component: TabFavoritesComponent) : TabChild()
}
