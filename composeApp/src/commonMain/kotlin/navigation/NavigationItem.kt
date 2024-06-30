package navigation

import kotlinx.serialization.Serializable
import navigation.auth.AuthComponent
import navigation.home.HomeComponent

@Serializable
sealed class NavigationItem {
    @Serializable
    data object AuthScreen : NavigationItem()

    @Serializable
    data object HomeScreen : NavigationItem()
}

sealed class NavigationChild {
    data class AuthScreen(val component: AuthComponent) : NavigationChild()
    data class HomeScreen(val component: HomeComponent) : NavigationChild()
}