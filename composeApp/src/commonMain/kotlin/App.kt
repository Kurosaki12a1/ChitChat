import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import navigation.NavigationChild
import navigation.NavigationItem
import navigation.RootComponent
import navigation.bottomNavigationItems
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import presenter.add_chat.AddChatScreen
import presenter.chat.ChatScreen
import presenter.contacts.ContactsScreen
import presenter.login.AuthScreen
import presenter.more.MoreScreen
import presenter.settings.SettingsScreen
import ui.theme.BackgroundColorEmphasis

/**
 * The main entry point for the application.
 *
 * @param root The RootComponent used for navigation and managing child stacks.
 */
@ExperimentalCoilApi
@Composable
@Preview
fun App(root: RootComponent) {
    // Applying Material theme to the entire application
    MaterialTheme {
        // Subscribing to the current child stack state from the root component
        val childStack by root.childStack.subscribeAsState()

        val isBottomSheetEnable = remember { mutableStateOf(false) }
        // Scaffold provides a structure with top and bottom bars and a body
        Scaffold(
            bottomBar = {
                // Conditionally showing the bottom bar based on the active screen
                if (shouldShowBottomBar(
                        childStack.active.configuration,
                        isBottomSheetEnable.value
                    )
                ) {
                    AppBottomNavigation(navigation = childStack) { navigationItem ->
                        // Navigating to the selected item
                        root.navigateTo(navigationItem)
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            // Managing the display of child components with animations
            Children(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                stack = childStack,
                animation = stackAnimation { _, otherChild, _ ->
                    // Custom animation for SettingsScreen
                    if (otherChild.instance is NavigationChild.SettingsScreen || otherChild.instance is NavigationChild.AddChatScreen) {
                        slide(
                            animationSpec = tween(easing = LinearEasing),
                            orientation = Orientation.Vertical
                        ) + fade()
                    } else {
                        // Default slide and fade animation
                        slide(animationSpec = tween(easing = LinearEasing)) + fade()
                    }
                }
            ) { child ->
                // Displaying the appropriate screen based on the current child instance
                when (val instance = child.instance) {
                    is NavigationChild.AuthScreen -> {
                        AuthScreen(instance.component)
                    }

                    is NavigationChild.ChatScreen -> {
                        ChatScreen(
                            isBottomSheetVisible = isBottomSheetEnable.value,
                            onStartNewChatClick = { shouldEnable ->
                                isBottomSheetEnable.value = shouldEnable
                            },
                            instance.component
                        )
                    }

                    is NavigationChild.ContactsScreen -> {
                        ContactsScreen(instance.component)
                    }

                    is NavigationChild.MoreScreen -> {
                        MoreScreen(instance.component)
                    }

                    is NavigationChild.SettingsScreen -> {
                        SettingsScreen(instance.component)
                    }

                    is NavigationChild.AddChatScreen -> {
                        AddChatScreen(instance.component)
                    }
                }
            }
        }

    }
}

/**
 * Determines if bottom bars should be visible based on the current screen.
 *
 * @param child The current navigation item.
 * @return True bottom bars should be visible, false otherwise.
 */
private fun shouldShowBottomBar(child: NavigationItem, isBottomSheetEnable: Boolean): Boolean =
    (child == NavigationItem.MoreScreen || child == NavigationItem.ChatScreen || child == NavigationItem.ContactsScreen) && !isBottomSheetEnable

/**
 * Composable function for the bottom navigation bar of the application.
 *
 * @param navigation The current child stack of navigation items.
 * @param onClick The callback to be invoked when a navigation item is clicked.
 */
@Composable
private fun AppBottomNavigation(
    navigation: ChildStack<NavigationItem, NavigationChild>,
    onClick: (NavigationItem) -> Unit
) {
    // Divider at the top of the bottom navigation bar
    Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(BackgroundColorEmphasis))
    // BottomNavigation component to hold the navigation items
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = BackgroundColorEmphasis,
        elevation = 0.dp
    ) {
        // Iterating over each bottom navigation item
        bottomNavigationItems.forEach { item ->
            val isSelected = item.route == navigation.active.configuration

            // BottomNavigationItem represents each navigation option in the bar
            BottomNavigationItem(
                modifier = Modifier.weight(1f),
                selected = isSelected,
                alwaysShowLabel = true,
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black,
                icon = {
                    Icon(
                        modifier = Modifier.padding(vertical = 4.dp),
                        painter = if (isSelected) painterResource(item.focusedIcon) else painterResource(
                            item.unFocusedIcon
                        ),
                        contentDescription = stringResource(item.iconContentDescription),
                        tint = Color.Unspecified
                    )
                },
                onClick = {
                    onClick(item.route)
                },
                label = {
                    Text(
                        text = stringResource(item.iconContentDescription),
                        color = if (isSelected) Color.Black else Color.Black.copy(0.3f)
                    )
                }
            )
        }
    }
}