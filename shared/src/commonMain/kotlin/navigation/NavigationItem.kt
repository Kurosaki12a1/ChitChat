package navigation

import kotlinx.serialization.Serializable
import navigation.auth.AuthComponent
import navigation.chat.ChatComponent
import navigation.contacts.ContactsComponent
import navigation.more.MoreComponent

@Serializable
sealed class NavigationItem {
    @Serializable
    data object AuthScreen : NavigationItem()

    @Serializable
    data object ChatScreen : NavigationItem()

    @Serializable
    data object ContactsScreen : NavigationItem()

    @Serializable
    data object MoreScreen : NavigationItem()
}

sealed class NavigationChild {
    data class AuthScreen(val component: AuthComponent) : NavigationChild()
    data class ChatScreen(val component: ChatComponent) : NavigationChild()
    data class ContactsScreen(val component: ContactsComponent) : NavigationChild()
    data class MoreScreen(val component : MoreComponent) : NavigationChild()
}
