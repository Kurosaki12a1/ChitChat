package navigation.chat_room

import com.arkivanov.decompose.ComponentContext
import domain.models.UserModel

class ChatRoomComponent(
    componentContext: ComponentContext,
    val listUser : List<UserModel>,
    val roomType: String
) : ComponentContext by componentContext {

}