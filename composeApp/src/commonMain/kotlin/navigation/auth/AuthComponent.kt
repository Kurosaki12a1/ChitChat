package navigation.auth

import com.arkivanov.decompose.ComponentContext
import domain.model.User

class AuthComponent(
    componentContext: ComponentContext,
    private val onNavigateToSetUpScreen : (User) -> Unit,
    private val onNavigateToHomeScreen : () -> Unit,
) : ComponentContext by componentContext {

    fun navigateToNextScreen(isConfirmed: Boolean, user: User) {
        if (isConfirmed) {
            onNavigateToHomeScreen.invoke()
        } else {
            onNavigateToSetUpScreen(user)
        }
    }
}