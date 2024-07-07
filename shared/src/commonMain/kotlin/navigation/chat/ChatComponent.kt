package navigation.chat

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import navigation.chat.tabs.ChatAllComponent
import navigation.chat.tabs.ChatFavoritesComponent
import navigation.chat.tabs.ChatUnreadComponent

class ChatComponent(
    componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
) : ComponentContext by componentContext {

    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<TabChatItem>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = TabChatItem.serializer(),
        initialConfiguration = TabChatItem.All,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onTabSelect(item: TabChatItem) {
        if (childStack.value.active.configuration == item) return
        if (shouldPopBackStack(item)) {
            navigation.bringToFront(item)
        } else {
            navigation.pushNew(item)
        }
    }

    private fun createChild(
        tabChatItem: TabChatItem,
        context: ComponentContext
    ): TabChatChild {
        return when (tabChatItem) {
            is TabChatItem.All -> {
                TabChatChild.AllScreen(
                    component = ChatAllComponent(context)
                )
            }

            is TabChatItem.Unread -> {
                TabChatChild.UnreadScreen(
                    component = ChatUnreadComponent(context)
                )
            }

            is TabChatItem.Favorites -> {
                TabChatChild.FavoritesScreen(
                    component = ChatFavoritesComponent(context)
                )
            }
        }
    }

    // Check if the tab item should pop back stack
    private fun shouldPopBackStack(tabChatItem: TabChatItem): Boolean {
        return isPresentedInBackStack(tabChatItem) && childStack.active.configuration != tabChatItem
    }

    // Check if the tab item is already presented in the back stack
    private fun isPresentedInBackStack(item: TabChatItem): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == item) return true
        }
        return false
    }
}

@Serializable
sealed class TabChatItem {
    @Serializable
    data object All : TabChatItem()

    @Serializable
    data object Unread : TabChatItem()

    @Serializable
    data object Favorites : TabChatItem()
}

@Serializable
sealed class TabChatChild {
    data class AllScreen(val component: ChatAllComponent) : TabChatChild()
    data class UnreadScreen(val component: ChatUnreadComponent) : TabChatChild()
    data class FavoritesScreen(val component: ChatFavoritesComponent) : TabChatChild()
}
