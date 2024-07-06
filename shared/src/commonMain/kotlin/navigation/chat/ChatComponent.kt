package navigation.chat

import com.arkivanov.decompose.ComponentContext

class ChatComponent(
    componentContext: ComponentContext,
    private val onNavigateTo: (String) -> Unit
) : ComponentContext by componentContext {

}