package navigation.add_chat

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.essenty.lifecycle.doOnDestroy
import domain.models.UserModel
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.compose.viewmodel.koinViewModel
import viewmodel.AddChatViewModel

class AddChatComponent(
    componentContext: ComponentContext,
    val title: String,
    private val onPop: () -> Unit,
    private val navigateToRoom: (List<UserModel>, String) -> Unit
) : ComponentContext by componentContext {

    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<TabAddChatItem>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = TabAddChatItem.serializer(),
        initialConfiguration = TabAddChatItem.Employee,
        handleBackButton = true,
        childFactory = ::createChild
    )

    fun navigateToChatRoom(currentUser: UserModel, listUser: List<UserModel>, type: String) {
        val finalListUser = mutableListOf<UserModel>()
        // Add myself
        finalListUser.add(currentUser)

        // Add selected
        finalListUser.addAll(listUser)
        navigateToRoom(finalListUser, type)
    }

    fun onTabSelect(item: TabAddChatItem) {
        if (childStack.value.active.configuration == item) return
        if (shouldPopBackStack(item)) {
            navigation.bringToFront(item)
        } else {
            navigation.pushNew(item)
        }
    }

    fun pop() {
        onPop.invoke()
    }


    private fun createChild(
        tab: TabAddChatItem,
        context: ComponentContext
    ): TabAddChatChild {
        return when (tab) {
            is TabAddChatItem.Employee -> {
                TabAddChatChild.EmployeeScreen
            }

            is TabAddChatItem.Chat -> {
                TabAddChatChild.ChatScreen
            }

            is TabAddChatItem.Contacts -> {
                TabAddChatChild.ContactsScreen
            }
        }
    }

    // Check if the tab item should pop back stack
    private fun shouldPopBackStack(tabChatItem: TabAddChatItem): Boolean {
        return isPresentedInBackStack(tabChatItem) && childStack.active.configuration != tabChatItem
    }

    // Check if the tab item is already presented in the back stack
    private fun isPresentedInBackStack(item: TabAddChatItem): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == item) return true
        }
        return false
    }
}

@Serializable
sealed class TabAddChatItem {
    @Serializable
    data object Employee : TabAddChatItem()

    @Serializable
    data object Chat : TabAddChatItem()

    @Serializable
    data object Contacts : TabAddChatItem()
}

@Serializable
sealed class TabAddChatChild {
    data object EmployeeScreen : TabAddChatChild()
    data object ChatScreen : TabAddChatChild()
    data object ContactsScreen : TabAddChatChild()
}
