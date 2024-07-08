package navigation.auth

import com.arkivanov.decompose.ComponentContext

class AuthComponent(
    componentContext: ComponentContext,
    private val onNavigateToHomeScreen: () -> Unit,
) : ComponentContext by componentContext {
    fun navigateToNextScreen() {
        onNavigateToHomeScreen.invoke()
    }
}