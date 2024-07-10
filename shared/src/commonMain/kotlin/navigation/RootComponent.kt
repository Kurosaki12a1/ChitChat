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
import com.arkivanov.decompose.router.stack.pushNew
import navigation.auth.AuthComponent
import navigation.chat.ChatComponent
import navigation.contacts.ContactsComponent
import navigation.more.MoreComponent
import navigation.settings.SettingsComponent

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
        serializer = NavigationItem.serializer(),
        initialConfiguration = NavigationItem.ChatScreen,
        //   initialConfiguration = NavigationItem.AuthScreen,
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

    // Factory method to create child components based on the navigation item
    private fun createChild(
        navigationItem: NavigationItem,
        context: ComponentContext
    ): NavigationChild {
        return when (navigationItem) {
            is NavigationItem.AuthScreen -> {
                NavigationChild.AuthScreen(
                    AuthComponent(
                        componentContext = context,
                        onNavigateToHomeScreen = {
                            // Navigate to ChatScreen when authenticated
                            navigation.pushNew(NavigationItem.ChatScreen)
                        }
                    )
                )
            }

            is NavigationItem.ChatScreen -> {
                NavigationChild.ChatScreen(
                    ChatComponent(
                        componentContext = context,
                        onNavigateTo = {

                        }
                    )
                )
            }

            is NavigationItem.MoreScreen -> {
                NavigationChild.MoreScreen(
                    MoreComponent(
                        componentContext = context,
                        onNavigateTo = { item ->
                            if (shouldPopBackStack(item)) {
                                navigation.bringToFront(item)
                            } else {
                                navigation.pushNew(item)
                            }
                        }
                    )
                )
            }

            is NavigationItem.ContactsScreen -> {
                NavigationChild.ContactsScreen(
                    ContactsComponent(
                        componentContext = context
                    )
                )
            }

            is NavigationItem.SettingsScreen -> {
                NavigationChild.SettingsScreen(
                    SettingsComponent(
                        componentContext = context,
                        onPop = { navigation.pop () }
                    )
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