package navigation

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import navigation.add_chat.AddChatComponent
import navigation.auth.AuthComponent
import navigation.chat.ChatComponent
import navigation.contacts.ContactsComponent
import navigation.more.MoreComponent
import navigation.settings.SettingsComponent

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

    @Serializable
    data object SettingsScreen : NavigationItem()

    @Serializable
    data class AddChatScreen(val type: String) : NavigationItem()
}

sealed class NavigationChild {
    data class AuthScreen(val component: AuthComponent) : NavigationChild()
    data class ChatScreen(val component: ChatComponent) : NavigationChild()
    data class ContactsScreen(val component: ContactsComponent) : NavigationChild()
    data class MoreScreen(val component: MoreComponent) : NavigationChild()
    data class SettingsScreen(val component: SettingsComponent) : NavigationChild()
    data class AddChatScreen(val component: AddChatComponent) : NavigationChild()
}

object NavigationItemSerializer :
    JsonContentPolymorphicSerializer<NavigationItem>(NavigationItem::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<NavigationItem> {
        return when {
            "type" in element.jsonObject -> NavigationItem.AddChatScreen.serializer()
            else -> NavigationItem.serializer()
        }
    }
}