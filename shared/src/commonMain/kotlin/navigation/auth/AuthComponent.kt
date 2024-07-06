package navigation.auth

import com.arkivanov.decompose.ComponentContext
import domain.model.User

class AuthComponent(
    componentContext: ComponentContext,
    private val onNavigateToHomeScreen : () -> Unit,
) : ComponentContext by componentContext {
    fun navigateToNextScreen() {
        onNavigateToHomeScreen.invoke()
    }
}