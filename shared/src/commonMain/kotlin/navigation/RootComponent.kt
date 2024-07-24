@file:OptIn(ExperimentalDecomposeApi::class)

package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.popWhile
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.replaceCurrent
import domain.models.ChatRoomModel
import navigation.add_chat.AddChatComponent
import navigation.chat.ChatComponent
import navigation.contacts.ContactsComponent
import navigation.more.MoreComponent
import navigation.settings.SettingsComponent
import utils.now
import utils.viewModelStoreOwner

/**
 * RootComponent class for handling navigation in a Kotlin Multiplatform project
 *
 */
class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    // StackNavigation for managing navigation items
    private val navigation = StackNavigation<NavigationItem>()

    // Child stack for managing the current stack of child components
    val childStack = childStack(
        source = navigation,
        serializer = NavigationItemSerializer,
        initialConfiguration = NavigationItem.ChatScreen,
        //    initialConfiguration = NavigationItem.AuthScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    // Function to navigate to a specific navigation item
    fun navigateTo(navigationItem: NavigationItem) {
        // If the current active configuration is the same as the navigation item, do nothing
        if (childStack.value.active.configuration == navigationItem) return
        // If the item should pop back stack, bring it to front
        if (shouldPopBackStack(navigationItem)) {
            navigation.bringToFront(navigationItem)
        } else {
            // Otherwise, push the new navigation item to the stack
            navigation.pushNew(navigationItem)
        }
    }

    fun replace(navigationItem: NavigationItem) {
        navigation.replaceCurrent(navigationItem)
    }

    fun pop(isSuccess: ((Boolean) -> Unit)? = null) {
        isSuccess?.let { navigation.pop(it) } ?: run { navigation.pop() }
    }

    // Factory method to create child components based on the navigation item
    private fun createChild(
        navigationItem: NavigationItem,
        context: ComponentContext
    ): NavigationChild {
        return when (navigationItem) {
            is NavigationItem.AuthScreen -> {
                NavigationChild.AuthScreen(
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.ChatScreen -> {
                NavigationChild.ChatScreen(
                    component = ChatComponent(
                        componentContext = context,
                        onNavigateTo = { item ->
                            navigateTo(item)
                        }
                    ),
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.MoreScreen -> {
                NavigationChild.MoreScreen(
                    component = MoreComponent(
                        componentContext = context,
                        onNavigateTo = { item ->
                            navigateTo(item)
                        }
                    ),
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.ContactsScreen -> {
                NavigationChild.ContactsScreen(
                    component = ContactsComponent(
                        componentContext = context
                    ),
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.SettingsScreen -> {
                NavigationChild.SettingsScreen(
                    component = SettingsComponent(
                        componentContext = context,
                        onPop = { navigation.pop() }
                    ),
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.AddChatScreen -> {
                NavigationChild.AddChatScreen(
                    component = AddChatComponent(
                        componentContext = context,
                        title = navigationItem.type,
                        onPop = { navigation.pop() },
                        navigateToRoom = { listUser, type ->
                            navigation.popWhile { it is NavigationItem.AddChatScreen }
                            val newRoomModel = ChatRoomModel(
                                roomName = "New Room",
                                participants = listUser.map { it.userId },
                                createdBy = "",
                                createdTime = now(),
                                roomType = type,
                                updatedTime = now(),
                                listUser = listUser
                            )
                            navigateTo(NavigationItem.AddChatRoomScreen(newRoomModel))
                        }
                    ),
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.ChatRoomScreen -> {
                NavigationChild.ChatRoomScreen(
                    chatRoom = navigationItem.chatRoom,
                    viewModelStore = context.viewModelStoreOwner()
                )
            }

            is NavigationItem.AddChatRoomScreen -> {
                NavigationChild.AddChatRoomScreen(
                    chatRoom = navigationItem.temporaryRoom,
                    viewModelStore = context.viewModelStoreOwner()
                )
            }
        }
    }

    // Check if the navigation item should pop back stack
    private fun shouldPopBackStack(navigationItem: NavigationItem): Boolean {
        return isPresentedInBackStack(navigationItem) && childStack.active.configuration != navigationItem
    }

    // Check if the navigation item is already presented in the back stack
    private fun isPresentedInBackStack(item: NavigationItem): Boolean {
        childStack.backStack.forEach {
            if (it.configuration == item) return true
        }
        return false
    }
}