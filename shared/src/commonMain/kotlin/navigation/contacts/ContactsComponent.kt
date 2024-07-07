package navigation.contacts

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import kotlinx.serialization.Serializable
import navigation.contacts.tab.ContactsAllComponent
import navigation.contacts.tab.ContactsBotsComponent
import navigation.contacts.tab.ContactsFavoritesComponent

class ContactsComponent(
    componentContext: ComponentContext,
) : ComponentContext by componentContext {
    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<TabContactsItem>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = TabContactsItem.serializer(),
        initialConfiguration = TabContactsItem.All,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun onTabSelect(item: TabContactsItem) {
        if (childStack.value.active.configuration == item) return
        if (shouldPopBackStack(item)) {
            navigation.bringToFront(item)
        } else {
            navigation.pushNew(item)
        }
    }

    private fun createChild(
        TabContactsItem: TabContactsItem,
        context: ComponentContext
    ): TabContactsChild {
        return when (TabContactsItem) {
            is TabContactsItem.All -> {
                TabContactsChild.AllScreen(
                    component = ContactsAllComponent(context)
                )
            }

            is TabContactsItem.Favorites -> {
                TabContactsChild.FavoritesScreen(
                    component = ContactsFavoritesComponent(context)
                )
            }

            is TabContactsItem.Bots -> {
                TabContactsChild.BotsScreen(
                    component = ContactsBotsComponent(context)
                )
            }
        }
    }

    // Check if the tab item should pop back stack
    private fun shouldPopBackStack(item: TabContactsItem): Boolean {
        return isPresentedInBackStack(item) && childStack.active.configuration != item
    }

    // Check if the tab item is already presented in the back stack
    private fun isPresentedInBackStack(item: TabContactsItem): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == item) return true
        }
        return false
    }
}

@Serializable
sealed class TabContactsItem {
    @Serializable
    data object All : TabContactsItem()

    @Serializable
    data object Favorites : TabContactsItem()

    @Serializable
    data object Bots : TabContactsItem()
}

@Serializable
sealed class TabContactsChild {
    data class AllScreen(val component: ContactsAllComponent) : TabContactsChild()
    data class FavoritesScreen(val component: ContactsFavoritesComponent) : TabContactsChild()
    data class BotsScreen(val component: ContactsBotsComponent) : TabContactsChild()
}
