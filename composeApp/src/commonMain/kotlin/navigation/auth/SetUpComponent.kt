package navigation.auth

import com.arkivanov.decompose.ComponentContext
import domain.model.User

class SetUpComponent(
    componentContext: ComponentContext,
    val user: User
) : ComponentContext by componentContext {

}