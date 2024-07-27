package navigation

import androidx.lifecycle.ViewModelStoreOwner
import domain.models.ChatRoomModel
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import navigation.add_chat.AddChatComponent
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

    @Serializable
    data class AddChatRoomScreen(val temporaryRoom: ChatRoomModel) : NavigationItem()

    @Serializable
    data class ChatRoomScreen(val chatRoom: ChatRoomModel) :
        NavigationItem()
}

sealed class NavigationChild {
    data class AuthScreen(val viewModelStore: ViewModelStoreOwner) : NavigationChild()
    data class ChatScreen(
        val component: ChatComponent,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class ContactsScreen(
        val component: ContactsComponent,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class MoreScreen(
        val component: MoreComponent,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class SettingsScreen(
        val component: SettingsComponent,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class AddChatScreen(
        val component: AddChatComponent,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class AddChatRoomScreen(
        val chatRoom: ChatRoomModel,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()

    data class ChatRoomScreen(
        val chatRoom: ChatRoomModel,
        val viewModelStore: ViewModelStoreOwner
    ) : NavigationChild()
}

object NavigationItemSerializer :
    JsonContentPolymorphicSerializer<NavigationItem>(NavigationItem::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<NavigationItem> {
        return when {
            "temporaryRoom" in element.jsonObject ->NavigationItem.AddChatRoomScreen.serializer()
            "chatRoom" in element.jsonObject -> NavigationItem.ChatRoomScreen.serializer()
            "type" in element.jsonObject -> NavigationItem.AddChatScreen.serializer()
            else -> NavigationItem.serializer()
        }
    }
}