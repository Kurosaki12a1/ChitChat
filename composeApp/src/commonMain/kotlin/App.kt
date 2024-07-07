import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.annotation.ExperimentalCoilApi
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
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
import presenter.chat.ChatScreen
import presenter.contacts.ContactsScreen
import presenter.login.AuthScreen
import presenter.more.MoreScreen
import ui.theme.BackgroundColorEmphasis

@ExperimentalCoilApi
@Composable
@Preview
fun App(root: RootComponent) {
    MaterialTheme {
        val childStack by root.childStack.subscribeAsState()
        Scaffold(
            bottomBar = {
                if (shouldTopBarAndBottomBarVisible(childStack.active.configuration)) {
                    AppBottomNavigation(navigation = childStack) { navigationItem ->
                        root.navigateTo(navigationItem)
                    }
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0)
        ) { innerPadding ->
            Children(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                stack = childStack,
                animation = stackAnimation(slide(animationSpec = tween(easing = LinearEasing)))
            ) { child ->
                when (val instance = child.instance) {
                    is NavigationChild.AuthScreen -> {
                        AuthScreen(instance.component)
                    }

                    is NavigationChild.ChatScreen -> {
                        ChatScreen(instance.component)
                    }

                    is NavigationChild.ContactsScreen -> {
                        ContactsScreen(instance.component)
                    }

                    is NavigationChild.MoreScreen -> {
                        MoreScreen(instance.component)
                    }
                }
            }
        }

    }
}

private fun shouldTopBarAndBottomBarVisible(child: NavigationItem): Boolean =
    child == NavigationItem.MoreScreen || child == NavigationItem.ChatScreen || child == NavigationItem.ContactsScreen

@Composable
private fun AppBottomNavigation(
    navigation: ChildStack<NavigationItem, NavigationChild>,
    onClick: (NavigationItem) -> Unit
) {
    Divider(modifier = Modifier.fillMaxWidth().height(1.dp).background(BackgroundColorEmphasis))
    BottomNavigation(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = BackgroundColorEmphasis,
        elevation = 0.dp
    ) {
        bottomNavigationItems.forEach { item ->
            val isSelected = item.route == navigation.active.configuration
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