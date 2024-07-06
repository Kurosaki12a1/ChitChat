package navigation

import chitchatmultiplatform.composeapp.generated.resources.Res
import chitchatmultiplatform.composeapp.generated.resources.chat_focused
import chitchatmultiplatform.composeapp.generated.resources.chat_route
import chitchatmultiplatform.composeapp.generated.resources.chat_unfocus
import chitchatmultiplatform.composeapp.generated.resources.contacts_focused
import chitchatmultiplatform.composeapp.generated.resources.contacts_route
import chitchatmultiplatform.composeapp.generated.resources.contacts_unfocus
import chitchatmultiplatform.composeapp.generated.resources.more_focused
import chitchatmultiplatform.composeapp.generated.resources.more_route
import chitchatmultiplatform.composeapp.generated.resources.more_unfocus
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class BottomNavigationItem(
    val route: NavigationItem,
    val focusedIcon: DrawableResource,
    val unFocusedIcon: DrawableResource,
    val iconContentDescription: StringResource
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(
        route = NavigationItem.ContactsScreen,
        focusedIcon = Res.drawable.contacts_focused,
        unFocusedIcon = Res.drawable.contacts_unfocus,
        iconContentDescription = Res.string.contacts_route
    ),
    BottomNavigationItem(
        route = NavigationItem.ChatScreen,
        focusedIcon = Res.drawable.chat_focused,
        unFocusedIcon = Res.drawable.chat_unfocus,
        iconContentDescription = Res.string.chat_route
    ),
    BottomNavigationItem(
        route = NavigationItem.MoreScreen,
        focusedIcon = Res.drawable.more_focused,
        unFocusedIcon = Res.drawable.more_unfocus,
        iconContentDescription = Res.string.more_route
    )
)
