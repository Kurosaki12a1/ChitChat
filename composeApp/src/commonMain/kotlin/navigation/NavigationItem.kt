package navigation

import domain.model.User
import kotlinx.serialization.Serializable
import navigation.auth.AuthComponent
import navigation.auth.SetUpComponent
import navigation.home.HomeComponent

@Serializable
sealed class NavigationItem {
    @Serializable
    data object AuthScreen : NavigationItem()

    @Serializable
    data class SetUpScreen(val user: User) : NavigationItem()

    @Serializable
    data object HomeScreen : NavigationItem()
}

sealed class NavigationChild {
    data class AuthScreen(val component: AuthComponent) : NavigationChild()
    data class SetUpScreen(val component: SetUpComponent) : NavigationChild()
    data class HomeScreen(val component: HomeComponent) : NavigationChild()
}