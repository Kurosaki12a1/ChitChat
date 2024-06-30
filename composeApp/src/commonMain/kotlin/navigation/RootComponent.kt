@file:OptIn(ExperimentalDecomposeApi::class)

package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushNew
import navigation.auth.AuthComponent
import navigation.home.HomeComponent

class RootComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext {

    private val navigation = StackNavigation<NavigationItem>()

    val childStack = childStack(
        source = navigation,
        serializer = NavigationItem.serializer(),
        initialConfiguration = NavigationItem.AuthScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(
        navigationItem: NavigationItem,
        context: ComponentContext
    ): NavigationChild {
        return when (navigationItem) {
            NavigationItem.AuthScreen -> {
                NavigationChild.AuthScreen(
                    AuthComponent(
                        componentContext = context,
                        onNavigateToHomeScreen = {
                            navigation.pushNew(NavigationItem.HomeScreen)
                        }
                    )
                )
            }

            NavigationItem.HomeScreen -> {
                NavigationChild.HomeScreen(
                    HomeComponent(
                        componentContext = context,
                        onNavigateTo = {

                        }
                    )
                )
            }
        }
    }
}